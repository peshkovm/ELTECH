#include <iostream>
#include <fstream>
#include <sys/shm.h>
#include <sys/ipc.h>
#include <sys/types.h>
#include <sys/sem.h>
#include <pthread.h>

using namespace std;

#define ARRAYSIZE 5

typedef struct {
    int buf[ARRAYSIZE];
    pthread_mutex_t mutex;
    pthread_cond_t notFull, notEmpty;
    int count;
} Queue;

Queue *queue;
int shmQueueId;
int putIndex = 0;

void deleteIds() {
    cout << "delete all ids" << endl;
    pthread_mutex_destroy(&queue->mutex);
    pthread_cond_destroy(&queue->notFull);
    pthread_cond_destroy(&queue->notEmpty);
    shmdt(queue);
    shmctl(shmQueueId, IPC_RMID, nullptr);
    exit(1);
}

Queue queueInit() {
    Queue q;

    for (int i = 0; i < ARRAYSIZE; i++) q.buf[i] = 0;
    q.mutex = PTHREAD_MUTEX_INITIALIZER;
    q.notFull = PTHREAD_COND_INITIALIZER;
    q.notEmpty = PTHREAD_COND_INITIALIZER;
    q.count = 0;

    return q;
}

void shareQueue() {
    Queue q = queueInit();

    if ((shmQueueId = shmget(111, sizeof(q), 0666 | IPC_CREAT)) == -1) {
        deleteIds();
    }
    cout << "shmQueueId" << shmQueueId << endl;

    if ((queue = (Queue *) shmat(shmQueueId, 0, 0)) == (Queue *) -1) {
        deleteIds();
    }

    *queue = q;
}

void enqueue(string line) {
    int num = stoi(line);
    queue->buf[putIndex] = num;
    if (++putIndex == ARRAYSIZE) putIndex = 0;
    cout << num << endl;
    (queue->count)++;
    pthread_cond_signal(&queue->notEmpty);
}

void exit() {
    int exitId;
    bool *exitAddr;
    if ((exitId = shmget(20, sizeof(bool), 0666 | IPC_CREAT)) == -1) {
        deleteIds();
    }

    if ((exitAddr = (bool *) shmat(exitId, 0, 0)) == (bool *) -1) {
        deleteIds();
    }

    exitAddr[0] = true;

    if ((shmdt(exitAddr)) == -1) {
        shmctl(exitId, IPC_RMID, nullptr);
        deleteIds();
    }
}

int main() {
    string inFileName = "/home/max/Desktop/Razumovskiy_labi/Lab10/Lab10_1/inFile.txt";
    ifstream inFile(inFileName);
    string line;

    shareQueue();   // ИНИЦИАЛИЗАЦИЯ

    for (; getline(inFile, line);) {
        pthread_mutex_lock(&queue->mutex);
        while (queue->count == ARRAYSIZE) {
            cout << "producer: queue FULL" << endl;
            pthread_cond_wait(&queue->notFull, &queue->mutex);
        }
        enqueue(line);
        pthread_mutex_unlock(&queue->mutex);
    }

    while (queue->count != 0);

    exit();

    return 0;
}