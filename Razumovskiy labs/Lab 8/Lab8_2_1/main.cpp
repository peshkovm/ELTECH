#include <iostream>
#include <sys/msg.h>
#include <fstream>
#include <signal.h>
#include <wait.h>
#include <vector>
#include <unistd.h>

using namespace std;

int id_server;
int fromProgram = 2;
size_t msgRequestSize = sizeof(long) + sizeof(int) + sizeof(time_t);
size_t msgResponseSize = sizeof(long) + sizeof(time_t);
time_t readTime = time(nullptr);
bool fileIsRead = false;
#define STACK 1024*64

struct msg_request {
    long toProgram;
    long fromProgram;
    int id_msg_queue;
    time_t sendTime;
};

struct msg_response {
    long toProgram;
    long fromProgram;
    time_t sendTime;
};

vector<msg_request> vec;

void sendMsgRequest(long toProgram) {
    msg_request msg_req;

    msg_req.toProgram = toProgram;
    msg_req.fromProgram = fromProgram;
    msg_req.id_msg_queue = id_server;
    msg_req.sendTime = time(nullptr);

    if (msgsnd(id_server, &msg_req, msgRequestSize, 0) == -1) {
        cout << "msgsnd request error" << endl;
        msgctl(id_server, IPC_RMID, nullptr);
        exit(1);
    }

    cout << "Send request message to program " << toProgram << endl;
}

void sendMsgResponse(long toProgram) {
    msg_response msg_res;

    msg_res.toProgram = toProgram + 3;
    msg_res.fromProgram = fromProgram;
    msg_res.sendTime = time(nullptr);

    if (msgsnd(id_server, &msg_res, msgResponseSize, 0) == -1) {
        cout << "msgsnd response error" << endl;
        msgctl(id_server, IPC_RMID, nullptr);
        exit(1);
    }

    cout << "Send response message to program " << toProgram << endl;
}

msg_request receiveMsgRequest() {
    msg_request msg_req;

    if (msgrcv(id_server, &msg_req, msgRequestSize, fromProgram, 0) == -1) {
        cout << "msgrcv request error" << endl;
        msgctl(id_server, IPC_RMID, nullptr);
        exit(1);
    }

    cout << "Receive request message from program " << msg_req.fromProgram << endl;

    return msg_req;
}

msg_response receiveMsgResponse() {
    msg_response msg_res;

    if (msgrcv(id_server, &msg_res, msgResponseSize, fromProgram + 3, 0) == -1) {
        cout << "msgrcv response error" << endl;
        cout << "errno = " << errno << endl;
        msgctl(id_server, IPC_RMID, nullptr);
        exit(1);
    }

    cout << "Receive response message from program " << msg_res.fromProgram << endl;

    return msg_res;
}

void writeInfoAboutProgram(msg_response msg_cl1, msg_response msg_cl2) {
    cout << "Program #1" << endl;
    cout << ctime(&msg_cl1.sendTime);
    cout.flush();
    cout << ctime(&msg_cl2.sendTime);
    cout.flush();
}

int threadJob(void *arg) {
    for (int i = 0; i < 2; i++) {
        msg_request msg_req;

        msg_req = receiveMsgRequest();
        cout << "Receive message in thread" << endl;
        double timeDiff = difftime(readTime, msg_req.sendTime);

        if (timeDiff > 0 || fileIsRead) {
            sendMsgResponse(msg_req.fromProgram);
            cout << "Send message in thread" << endl;
        } else
            vec.push_back(msg_req);
    }

    for (;;) {
        if (fileIsRead) {
            for (int i = 0; i < vec.size(); i++) {
                msg_request msg_req = vec.at(i);
                sendMsgResponse(msg_req.fromProgram);
                cout << "Send message in thread" << endl;
            }
            break;
        }
    }

    exit(0);
}

int main() {
    errno = 0;
    id_server = msgget(555, 0622 | IPC_CREAT);
    cout << "id_server = " << id_server << endl;
    ifstream inFile;
    string inFileName = "/home/max/Desktop/Razumovskiy_labi/Lab8/Lab8_2/inFile.txt";
    string line;
    msg_response msg_cl1, msg_cl2;
    void *stack = malloc(STACK);
    int threadPid;
    int status;

    inFile.open(inFileName);

    threadPid = clone(&threadJob, (char *) stack + STACK,
                      SIGCHLD | CLONE_FS | CLONE_FILES | CLONE_SIGHAND | CLONE_VM, nullptr);

    if (fromProgram != 1)
        sendMsgRequest(1);
    if (fromProgram != 2)
        sendMsgRequest(2);
    if (fromProgram != 3)
        sendMsgRequest(3);

    msg_cl1 = receiveMsgResponse();
    msg_cl2 = receiveMsgResponse();

    writeInfoAboutProgram(msg_cl1, msg_cl2);

    readTime = time(nullptr);

    while (getline(inFile, line))
        cout << line << endl;

    fileIsRead = true;
    waitpid(threadPid, &status, 0);

    msqid_ds msqidDs;

    do {
        msgctl(id_server, IPC_STAT, &msqidDs);
        sleep(1);
    } while (msqidDs.msg_qnum > 0);

    msgctl(id_server, IPC_RMID, nullptr);

    cout << "Program ends" << endl;

    return 0;
}