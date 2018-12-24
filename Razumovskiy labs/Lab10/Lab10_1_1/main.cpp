#include <iostream>
#include <fstream>
#include <sys/shm.h>
#include <sys/ipc.h>
#include <sys/types.h>
#include <sys/sem.h>
#include <signal.h>
#include <pthread.h>

using namespace std;

#define ARRAYSIZE 5
#define PRODSEM 0
#define CONSSEM 1
#define STACK 1024*64

struct Queue {
    int buf[ARRAYSIZE];
    int semId;
    int count;
};

Queue *queue;
int shmQueueId;
int exitId;
bool *exitAddr;
int takeIndex = 0;

void deleteIds() {
    cout << "delete all ids" << endl;
    shmdt(exitAddr);
    shmctl(exitId, IPC_RMID, nullptr);
    shmdt(queue);
    shmctl(shmQueueId, IPC_RMID, nullptr);
    exit(1);
}

void shareQueue() {
    if ((shmQueueId = shmget(111, sizeof(Queue), 0666 | IPC_CREAT | IPC_EXCL)) != -1) {
        cout << "shmQueueId = " << shmQueueId << endl;

        if ((queue = (Queue *) shmat(shmQueueId, 0, 0)) == (Queue *) -1) {
            deleteIds();
        }

        for (int i = 0; i < ARRAYSIZE; i++) queue->buf[i] = 0;
        queue->semId = semget(100, 2, IPC_CREAT | 0666);
        semctl(queue->semId, CONSSEM, SETVAL, 0);
        semctl(queue->semId, PRODSEM, SETVAL, 1);
        queue->count = 0;

    } else {
        if ((shmQueueId = shmget(111, sizeof(Queue), 0666 | IPC_CREAT)) == -1)
            deleteIds();

        cout << "shmQueueId = " << shmQueueId << endl;

        if ((queue = (Queue *) shmat(shmQueueId, 0, 0)) == (Queue *) -1) {
            deleteIds();
        }
    }
}

void consAcquire() {
    struct sembuf op = {CONSSEM, -1, 0};
    semop(queue->semId, &op, 1);
}

void prodRelease() {
    struct sembuf op = {PRODSEM, 1, 0};
    semop(queue->semId, &op, 1);
}

void dequeue() {
    consAcquire();
    int num = queue->buf[takeIndex];
    queue->buf[takeIndex] = 0;
    if (++takeIndex == ARRAYSIZE) takeIndex = 0;
    cout << num << endl;
    (queue->count)--;
    prodRelease();
}

void exitInit() {
    if ((exitId = shmget(20, sizeof(bool), 0666 | IPC_CREAT)) == -1) {
        deleteIds();
    }

    if ((exitAddr = (bool *) shmat(exitId, 0, 0)) == (bool *) -1) {
        deleteIds();
    }
}

bool isSupplierExit() {
    return *exitAddr;
}

void *threadJob(void *arg) {
    while (!(queue->count == 0 && isSupplierExit()));

    deleteIds();

    return 0;
}

int main() {
    pthread_t p;
    string line;

    exitInit();     //
    shareQueue();   // ИНИЦИАЛИЗАЦИЯ

    pthread_create(&p, NULL, threadJob, NULL);

    //TODO заменить clone на pthread_create

    for (;;) {
        dequeue();
    }
}