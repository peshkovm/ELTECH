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
#define STACK 1024*64

struct Queue {
    int buf[ARRAYSIZE];
    pthread_mutex_t mutex;
    pthread_cond_t notFull, notEmpty;
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
    if ((shmQueueId = shmget(111, sizeof(Queue), 0666 | IPC_CREAT)) == -1) {
        deleteIds();
    }
    cout << "shmQueueId" << shmQueueId << endl;

    if ((queue = (Queue *) shmat(shmQueueId, 0, 0)) == (Queue *) -1) {
        deleteIds();
    }
}

void dequeue() {
    int num = queue->buf[takeIndex];
    queue->buf[takeIndex] = 0;
    if (++takeIndex == ARRAYSIZE) takeIndex = 0;
    cout << num << endl;
    (queue->count)--;
    //int b = pthread_cond_signal(&queue->notFull);
    int b = pthread_cond_broadcast(&queue->notFull);
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
    //void *stack = malloc(STACK);
    pthread_t p;
    string line;

    exitInit();     //
    shareQueue();   // ИНИЦИАЛИЗАЦИЯ

    /*clone(&threadJob, (char *) stack + STACK,
          SIGCHLD | CLONE_FS | CLONE_FILES | CLONE_SIGHAND | CLONE_VM, nullptr);*/

    pthread_create(&p, NULL, threadJob, NULL);

    //TODO заменить clone на pthread_create

    for (;;) {
        int a1 = pthread_mutex_lock(&queue->mutex);
        while (queue->count == 0) {
            cout << "consumer: queue EMPTY" << endl;
            pthread_cond_wait(&queue->notEmpty, &queue->mutex);
        }
        dequeue();
        int a = pthread_mutex_unlock(&queue->mutex);
    }
}