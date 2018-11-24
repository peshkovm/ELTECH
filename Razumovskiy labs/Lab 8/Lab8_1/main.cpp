#include <iostream>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <signal.h>
#include <unistd.h>
#include <csignal>
#include <thread>

using namespace std;

#define STACK 1024*64
int id_server;

int threadJob(void *timeWait) {
    int sec = *reinterpret_cast<int *>(timeWait);
    sleep(sec);
    raise(SIGUSR1);
}

void exitFunc(int sig) {
    switch (sig) {
        case SIGUSR1: {
            msgctl(id_server, IPC_RMID, nullptr);
            cout << "Timed out! The program will be close" << endl;
            raise(SIGKILL);
        }
    }
}

int main(int argc, char *argv[]) {
    if (argc == 2) {
        int timeWait = atoi(argv[1]);
        void *stack = malloc(STACK);

        struct msg_client {
            long id_client;
            int message;
        } msg_cl;

        id_server = msgget(100, 0622 | IPC_CREAT);

        cout << "id_server = " << id_server << endl;

        signal(SIGUSR1, exitFunc);

        //thread t1(threadJob, timeWait);

        clone(&threadJob, (char *) stack + STACK,
              SIGCHLD | CLONE_FS | CLONE_FILES | CLONE_SIGHAND | CLONE_VM, &timeWait);

        for (;;) {
            if (msgrcv(id_server, &msg_cl, sizeof(int), 0, 0) != -1) {
                signal(SIGUSR1, SIG_IGN);
                cout << "Request from client: " << msg_cl.message << endl;
            } else {
                cout << "msgrcv error: " << endl;
                break;
            }
        }
    } else {
        cout << "Вы неправильно ввели данные" << endl;
        return 1;
    }

    return 0;
}