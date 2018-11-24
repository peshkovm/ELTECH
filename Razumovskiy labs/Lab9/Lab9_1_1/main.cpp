#include <iostream>
#include <fstream>
#include <unistd.h>
#include <sys/shm.h>
#include <unistd.h>

using namespace std;

int main(int argc, char *argv[]) {
    if (argc == 3) {
        int interval = atoi(argv[1]);
        int numOfLineToWrite = atoi(argv[2]);
        string inFileName = "/home/max/Desktop/Razumovskiy_labi/Lab9/Lab9_1_1/inFile.txt";
        string outFileName = "/home/max/Desktop/Razumovskiy_labi/Lab9/Lab9_1/outFile.txt";
        ifstream inFile(inFileName);
        ofstream outFile(outFileName, ios_base::app);
        string line;
        int shmId;
        bool *shmAddr;
        bool lock = false;
        bool lockIsReleased = false;
        bool lockIsAcquired = true;
        bool res = false;

        if ((shmId = shmget(55, sizeof(bool), 0666 | IPC_CREAT)) == -1) {
            cout << "shmget error. errno = " << errno << endl;
            shmctl(shmId, IPC_RMID, nullptr);
            exit(1);
        }

        if ((shmAddr = (bool *) shmat(shmId, 0, 0)) == (bool *) -1) {
            cout << "shmat error. errno = " << errno << endl;
            shmctl(shmId, IPC_RMID, nullptr);
            exit(1);
        }

        for (int i = 0; getline(inFile, line) && i < numOfLineToWrite; i++) {
            sleep(interval);
            while (!__sync_bool_compare_and_swap(&shmAddr[0], lockIsReleased, lockIsAcquired)); //cas
            cout << line << endl;
            outFile << line << endl;
            shmAddr[0] = false;
        }

        if ((shmdt(shmAddr)) == -1) {
            cout << "shmdt error. errno = " << errno << endl;
            shmctl(shmId, IPC_RMID, nullptr);
            exit(1);
        }

        shmctl(shmId, IPC_RMID, nullptr);

        return 0;
    } else {
        cout << "error in main args" << endl;
    }
}