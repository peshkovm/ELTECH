#include <iostream>
#include <fstream>
#include <sys/shm.h>
#include <sys/ipc.h>
#include <sys/types.h>
#include <sys/sem.h>
#include <signal.h>
#include <pthread.h>
#include <unistd.h>

using namespace std;

#define ARRAYSIZE 5
#define PRODSEM 0
#define CONSSEM 1
#define SYNCSEM 2
#define CONS_READ_WHOLE_BUFFER 0
#define CONS_FINISH_WORK 0
#define SYNC_INITIAL_STATE 1
#define PROD_FINISH_WORK 2

struct Queue {
    int buf[ARRAYSIZE];
    int semId;
    int count;
};

Queue *queue;
int shmQueueId;
int takeIndex = 0;

void deleteIds() {
    cout << "delete all ids" << endl;
    semctl(queue->semId, IPC_RMID, 0);
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
        queue->semId = semget(100, 3, IPC_CREAT | 0666);
        semctl(queue->semId, CONSSEM, SETVAL, 0);
        semctl(queue->semId, PRODSEM, SETVAL, ARRAYSIZE);  //1
        semctl(queue->semId, SYNCSEM, SETVAL, SYNC_INITIAL_STATE);
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

bool isProducerFinished() {
    return semctl(queue->semId, SYNCSEM, GETVAL, nullptr) == PROD_FINISH_WORK;
}

void dequeue() {
    //cout << "before consAcquire(): " << semctl(queue->semId, CONSSEM, GETVAL, nullptr) << endl;
    consAcquire();
    //cout << "after consAcquire(): " << semctl(queue->semId, CONSSEM, GETVAL, nullptr) << endl;

    if (isProducerFinished()) {
        cout << "return from dequeue" << endl;
        return;
    }

    int num = queue->buf[takeIndex];
    queue->buf[takeIndex] = 0;
    if (++takeIndex == ARRAYSIZE) takeIndex = 0;
    cout << num << endl;
    (queue->count)--;
    prodRelease();
}

int main() {
    pthread_t p;
    string line;

    shareQueue();   // ИНИЦИАЛИЗАЦИЯ

    for (;;) {
        dequeue();

        if (queue->count == 0) {
            if (isProducerFinished()) {
                cout << "break occured" << endl;
                break;
            }
            semctl(queue->semId, SYNCSEM, SETVAL, CONS_READ_WHOLE_BUFFER);
        }
    }

    cout << "PROD finished work" << endl;
    semctl(queue->semId, SYNCSEM, SETVAL, CONS_FINISH_WORK);


    deleteIds();
}