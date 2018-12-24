#include <iostream>
#include <fstream>
#include <sys/shm.h>
#include <sys/ipc.h>
#include <sys/types.h>
#include <sys/sem.h>
#include <pthread.h>

using namespace std;

#define ARRAYSIZE 5
#define PRODSEM 0
#define CONSSEM 1

typedef struct {
    int buf[ARRAYSIZE];
    int semId;
    int count;
} Queue;

Queue *queue;
int shmQueueId;
int putIndex = 0;

void deleteIds() {
    cout << "delete all ids" << endl;
    semctl(queue->semId, IPC_RMID, 0);
    shmdt(queue);
    shmctl(shmQueueId, IPC_RMID, nullptr);
    exit(1);
}

Queue queueInit() {
    Queue q;

    for (int i = 0; i < ARRAYSIZE; i++) q.buf[i] = 0;
    q.semId = semget(100, 2, IPC_CREAT | 0666);
    q.count = 0;

    return q;
}

void queueInit(Queue *q) {
    for (int i = 0; i < ARRAYSIZE; i++) q->buf[i] = 0;
    q->semId = semget(100, 2, IPC_CREAT | 0666);
    semctl(q->semId, CONSSEM, SETVAL, 0);
    semctl(q->semId, PRODSEM, SETVAL, 1);
    q->count = 0;
}

void shareQueue() {
    //Queue q = queueInit();

    if ((shmQueueId = shmget(111, sizeof(Queue), 0666 | IPC_CREAT)) == -1) {
        deleteIds();
    }
    cout << "shmQueueId = " << shmQueueId << endl;

    if ((queue = (Queue *) shmat(shmQueueId, 0, 0)) == (Queue *) -1) {
        deleteIds();
    }

    // *queue = q;
    queueInit(queue);
}

void prodAcquire() {
    struct sembuf op = {PRODSEM, -1, 0};
    semop(queue->semId, &op, 1);
}

void consRelease() {
    struct sembuf op = {CONSSEM, 1, 0};
    semop(queue->semId, &op, 1);
}

void enqueue(string line) {
    prodAcquire();

    int num = stoi(line);
    queue->buf[putIndex] = num;
    if (++putIndex == ARRAYSIZE) putIndex = 0;
    cout << num << endl;
    (queue->count)++;

    consRelease();
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
    string inFileName = "/home/denis/Desktop/ELTECH/ELTECH/Razumovskiy labs/Lab10/Lab10_1/inFile.txt";
    ifstream inFile(inFileName);
    string line;

    shareQueue();   // ИНИЦИАЛИЗАЦИЯ

    for (; getline(inFile, line);) {
        enqueue(line);
    }

    while (queue->count != 0);

    exit();

    return 0;
}