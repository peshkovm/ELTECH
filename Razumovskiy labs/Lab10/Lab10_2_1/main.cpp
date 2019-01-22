#include <iostream>
#include <fstream>
#include <sys/shm.h>
#include <sys/ipc.h>
#include <sys/types.h>
#include <sys/sem.h>
#include <unistd.h>

using namespace std;

int writersId = 0;
int semaphoreId=0;

void writersInit() {
    writersId = semget(100, 1, IPC_CREAT | 0666);
}

void writersAcquire() {
    struct sembuf op = {0, -1, 0}; //операция захвата
    semop(writersId, &op, 1);
}

void writersRelease() {
    struct sembuf op = {0, 1, 0}; //операция освобождения
    semop(writersId, &op, 1);
}

void semaphoreInit() {
    semaphoreId = semget(102, 1, IPC_CREAT | 0666);
}

void semaphoreAcquire() {
    struct sembuf op = {0, -1, 0}; //операция захвата
    semop(semaphoreId, &op, 1);
}

void semaphoreRelease() {
    struct sembuf op = {0, 1, 0}; //операция освобождения
    semop(semaphoreId, &op, 1);
}

int main(int argc, char **argv) {
    string outFileName = "/home/denis/Desktop/ELTECH/ELTECH/Razumovskiy labs/Lab10/Lab10_2_1/file.txt";
    ofstream outFile(outFileName, std::ofstream::app);

    writersInit();
    semaphoreInit();

    for (int i = 0; i < 10; i++) {
        writersAcquire();
        semaphoreAcquire();
        outFile << "string #" << i << endl;
        cout << "string #" << i << " " << argv[1] << " " << "writer" << endl;
        semaphoreRelease();
        writersRelease();
        sleep(1);
    }

    return 0;
}