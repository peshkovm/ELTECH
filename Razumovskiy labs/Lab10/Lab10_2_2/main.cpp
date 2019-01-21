#include <iostream>
#include <fstream>
#include <sys/shm.h>
#include <sys/ipc.h>
#include <sys/types.h>
#include <sys/sem.h>

using namespace std;

int mutId = 0;
int readmutId = 0;
int b=0;

#define SETVALUE 1

void mutInit() {
    mutId = semget(100, 1, IPC_CREAT | 0666); //инициализация семафора
}

void readmutInit() {
    if ((readmutId = semget(101, 1, IPC_CREAT | 0666)) < 0) {
        cerr << "Семафор не создан" << endl;
        exit(-1);
    }
    semctl(readmutId, 0, SETVAL, SETVALUE);// установка семафора в изаначальное значение 1
}

void mutAcquire() {
    struct sembuf op = {0, -1, 0}; //захват семафора
    semop(mutId, &op, 1);
}

void mutRelease() {
    struct sembuf op = {0, 1, 0}; //освобождение семафора
    semop(mutId, &op, 1);
}

void readmutAcquire() {
    struct sembuf op = {0, -1, 0};
    semop(readmutId, &op, 1);
}

void readmutRelease() {
    struct sembuf op = {0, 1, 0};
    semop(readmutId, &op, 1);
}

int main() {
    string inFileName = "/home/denis/Desktop/ELTECH/ELTECH/Razumovskiy labs/Lab10/Lab10_2_1/file.txt";
    ifstream inFile(inFileName);
    string line;

    mutInit();
    readmutInit();

    readmutAcquire();
    b++;
    if(b==1) {
        mutAcquire();
    }
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