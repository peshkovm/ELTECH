#include <iostream>
#include <sys/shm.h>
#include <unistd.h>
#include <signal.h>
#include <wait.h>

using namespace std;

int masSize = 10;
#define STACK 1024*64

int threadJob(void *arg) {
    int shmId;
    int *shmAddr;
    int hasData = 1;
    int notData = 2;

    if ((shmId = shmget(100, 2 * sizeof(int), 0666 | IPC_CREAT)) == -1) {
        cout << "shmget error. errno = " << errno << endl;
        shmctl(shmId, IPC_RMID, nullptr);
        exit(1);
    }
    cout << "shmId = " << shmId << endl;

    if ((shmAddr = (int *) shmat(shmId, 0, 0)) == (int *) -1) {
        cout << "shmat error. errno = " << errno << endl;
        shmctl(shmId, IPC_RMID, nullptr);
        exit(1);
    }

    for (int i = 0; i < masSize; i++) {
        while (shmAddr[0] != hasData);
        cout << shmAddr[1] << endl;
        shmAddr[0] = notData;
    }

    if ((shmdt(shmAddr)) == -1) {
        cout << "shmdt error. errno = " << errno << endl;
        shmctl(shmId, IPC_RMID, nullptr);
        exit(1);
    }

    shmctl(shmId, IPC_RMID, nullptr);

    return 0;
}

int main() {
    int mas[masSize];
    int shmId;
    int *shmAddr;
    int threadPid;
    int status;
    void *stack = malloc(STACK);
    int hasData = 1;
    int notData = 2;

    for (int i = 0; i < masSize; i++)
        mas[i] = i + 1;

    if ((shmId = shmget(55, 2 * sizeof(int), 0666 | IPC_CREAT)) == -1) {
        cout << "shmget error. errno = " << errno << endl;
        shmctl(shmId, IPC_RMID, nullptr);
        exit(1);
    }
    cout << "shmId = " << shmId << endl;

    if ((shmAddr = (int *) shmat(shmId, 0, 0)) == (int *) -1) {
        cout << "shmat error. errno = " << errno << endl;
        shmctl(shmId, IPC_RMID, nullptr);
        exit(1);
    }

    threadPid = clone(&threadJob, (char *) stack + STACK,
                      SIGCHLD | CLONE_FS | CLONE_FILES | CLONE_SIGHAND | CLONE_VM, nullptr);

    shmAddr[0] = notData;
    for (int i = 0; i < masSize; i++) {
        sleep(1);
        shmAddr[1] = mas[i];
        //cout << shmAddr[1] << endl;
        shmAddr[0] = hasData;
        while (shmAddr[0] != notData);
    }

    waitpid(threadPid, &status, 0);

    return 0;
}