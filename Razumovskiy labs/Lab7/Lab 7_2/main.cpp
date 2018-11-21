#include <iostream>
#include <fstream>
#include <unistd.h>
#include <signal.h>
#include <csignal>
#include <wait.h>
#include <fcntl.h>

using namespace std;

int fileDesc[2];

ofstream outFile1;
ofstream outFile2;
int pid1 = -5, pid2 = -5;

void exitFunc(int sigNum) {
    switch (sigNum) {
        case SIGINT: {
            cout << "SIGQUIT occurred" << endl;
            //close(fileDesc[1]);
            char zero[1];
            zero[0] = '0';
            write(fileDesc[1], zero, 1);
            break;
            //exit(0);
        }
        default:
            cout << "default case in exitFunc()" << endl;
    }
}

void writeFunc(int sigNum) {
    switch (sigNum) {
        case SIGUSR1: {
            cout << "SIGUSR1 occurred" << endl;
            ssize_t numRead = 0;
            char numBuffer[1];
            int bufferSize;

            read(fileDesc[0], numBuffer, 1);
            bufferSize = numBuffer[0] - '0';

            if (bufferSize != 0) {
                char buffer[bufferSize];

                numRead = read(fileDesc[0], buffer, bufferSize);
                cout << "numRead = " << numRead << endl;
                outFile1 << buffer;
                outFile1.flush();

                kill(0, SIGUSR2);
            } else {
                close(fileDesc[0]);
                //close(fileDesc[1]);
                outFile1.close();
                cout << "Child process1 exit" << endl;
                kill(0, SIGINT);
                kill(0, SIGUSR2);
                exit(0);
            }
            break;
        }
        case SIGUSR2: {
            cout << "SIGUSR2 occurred" << endl;
            ssize_t numRead = 0;
            char numBuffer[1];
            int bufferSize;

            read(fileDesc[0], numBuffer, 1);
            bufferSize = numBuffer[0] - '0';

            if (bufferSize != 0) {
                char buffer[bufferSize];

                numRead = read(fileDesc[0], buffer, bufferSize);
                cout << "numRead = " << numRead << endl;
                outFile2 << buffer;
                outFile2.flush();

                kill(0, SIGUSR1);
            } else {
                close(fileDesc[0]);
                //close(fileDesc[1]);
                outFile2.close();
                cout << "Child process2 exit" << endl;
                kill(0, SIGINT);
                kill(0, SIGUSR1);
                exit(0);
            }
        }
    }
}

int createChild(bool isKill, int sigIgnore, int sigWriteFunc) {
    int pid = fork();

    switch (pid) {
        case -1: {
            cerr << "Child wasn't created" << endl;
            exit(1);
        }
        case 0: {
            //Дочерний процесс
            cout << "Child was created" << endl;

            signal(sigIgnore, SIG_IGN);
            signal(sigWriteFunc, writeFunc);
            signal(SIGINT, exitFunc);

            sleep(1);

            //close(fileDesc[1]);

            if (isKill)
                kill(0, sigIgnore);

            while (true);
        }
        default: {
            //Родительский процесс
            cout << "pid = " << pid << endl;
            return pid;
        }
    }
}

int main() {
    ifstream inFile;
    int pipeIsCreate;
    string line;
    int status1, status2;

    inFile.open("/home/max/Desktop/Razumovskiy_labi/Lab7/Lab7_2/inFile.txt");
    outFile1.open("/home/max/Desktop/Razumovskiy_labi/Lab7/Lab7_2/outFile1.txt");
    outFile2.open("/home/max/Desktop/Razumovskiy_labi/Lab7/Lab7_2/outFile2.txt");

    pipeIsCreate = pipe(fileDesc);
    if (pipeIsCreate == -1) {
        cerr << "pipe wasn't created" << endl;
        return 0;
    }

    pid1 = createChild(false, SIGUSR2, SIGUSR1);  //Первый потомок
    sleep(1);
    pid2 = createChild(true, SIGUSR1, SIGUSR2);  //Второй потомок

    signal(SIGUSR1, SIG_IGN);
    signal(SIGUSR2, SIG_IGN);
    signal(SIGINT, SIG_IGN);

    sleep(5);

    close(fileDesc[0]);

    while (getline(inFile, line)) {
        //cout << line << endl;
        int lineLength = line.size();
        line.insert(0, to_string(lineLength + 1));
        line += '\n';
        cout << line;
        cout.flush();
        write(fileDesc[1], line.c_str(), line.size());
    }
    kill(pid1, SIGINT);

    cout << "pid1 in main = " << pid1 << endl;
    cout << "pid2 in main = " << pid2 << endl;

    waitpid(pid1, &status1, 0);
    waitpid(pid2, &status2, 0);

    close(fileDesc[1]);

    cout << "Программа завершилась" << endl;

    return 0;
}