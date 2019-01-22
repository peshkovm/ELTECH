#include <iostream>
#include <fstream>
#include <sys/shm.h>
#include <sys/ipc.h>
#include <sys/types.h>
#include <sys/sem.h>

using namespace std;

int readersId=0;

void readersInit() {
    readersId = semget(101, 1, IPC_CREAT | 0666);
}

void readersAcquire() {
    struct sembuf op = {0, -1, 0}; //операция захвата
    semop(readersId, &op, 1);
}

void readersRelease() {
    struct sembuf op = {0, 1, 0}; //операция освобождения
    semop(readersId, &op, 1);
}

int main() {
    string inFileName = "/home/denis/Desktop/ELTECH/ELTECH/Razumovskiy labs/Lab10/Lab10_2_1/file.txt";
    ifstream inFile(inFileName);
    string line;

    no_writersInit();
    readersInit();
    counter_mutexInit();

    no_writersAcquire();
    counter_mutexAcquire();
    prev_=nreaders;
    nreaders++;
    if(prev)
    readmutRelease();

    while (getline(inFile, line)) {
        cout << line << endl;
    }
    cout << endl;

    readmutAcquire();
    b--;
    if(b==0) {
        mutRelease();
    }
    readmutRelease();

    return 0;
}