#include <iostream>
#include <fstream>
#include <sys/shm.h>
#include <sys/ipc.h>
#include <sys/types.h>
#include <sys/sem.h>
#include <unistd.h>

using namespace std;

int mutId = 0; //идентификатор мьютекса

#define SETVALUE 1

void mutInit() {
    if ((mutId = semget(100, 1, IPC_CREAT | 0666)) < 0) {
        cerr << "Семафор не создан" << endl;
        exit(-1);
    }
    semctl(mutId, 0, SETVAL, SETVALUE);// установка семафора в изаначальное значение 5
}

void mutAcquire() {
    struct sembuf op = {0, -1, 0}; //операция захвата
    semop(mutId, &op, 1);
}

void mutRelease() {
    struct sembuf op = {0, 1, 0}; //операция освобождения
    semop(mutId, &op, 1);
}

int main() {
    string outFileName = "/home/denis/Desktop/ELTECH/ELTECH/Razumovskiy labs/Lab10/Lab10_2_1/file.txt";
    ofstream outFile(outFileName,std::ofstream::app);

    sleep(3);

    mutInit();


    for (int i = 0; i < 10; i++) {
        mutAcquire();
        outFile << "string #" << i << endl;
        cout << "string #" << i << " Writer" << endl;
        mutRelease();
    }

    return 0;
}