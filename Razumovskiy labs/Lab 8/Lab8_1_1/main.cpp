#include <iostream>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <unistd.h>

using namespace std;

int main(int argc, char *argv[]) {
    if (argc == 2) {
        int id_port_server;
        int timeWait = atoi(argv[1]);

        struct msg_client {
            long id_client;
            int message;
        } msg_server;

        id_port_server = msgget(100,  IPC_CREAT | IPC_EXCL);
        cout << "id_server=" << id_port_server << endl;
        if (id_port_server != -1) {
            cout << "not EEXIST" << endl;
            msgctl(id_port_server, IPC_RMID, nullptr);
            exit(0);
        }
        id_port_server = msgget(100,  IPC_CREAT);
        msg_server.id_client = getpid();
        msg_server.message = 5;
        sleep(timeWait);
        msgsnd(id_port_server, &msg_server, sizeof(int), 0);
        msgctl(id_port_server, IPC_RMID, nullptr);
    } else {
        cout << "Вы неправильно ввели данные" << endl;
        return 1;
    }
    return 0;
}