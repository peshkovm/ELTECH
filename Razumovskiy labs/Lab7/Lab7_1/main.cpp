#include <iostream>
#include<fstream>
#include <unistd.h>
#include <fcntl.h>
#include <wait.h>

using namespace std;

int createChild(int fileDesc, string inFileName) {
    int pid = fork();

    switch (pid) {
        case -1: {
            cerr << "Child wasn't created" << endl;
            exit(1);
        }
        case 0: {
            //Дочерний процесс
            cout << "Child process" << endl;
            ifstream inFile;
            string line;

            inFile.open(inFileName);
            while (getline(inFile, line)) {
                cout << line << endl;
                line += '\n';
                write(fileDesc, line.c_str(), line.size());
            }

            close(fileDesc);
            inFile.close();
            cout << "Child exit" << endl;
            exit(0);
        }
        default: {
            //Родительский процесс
            return pid;
        }
    }
}

int main(int argc, char *argv[]) {

    if (argc == 4) {
        string inFile1Name = argv[1];
        string inFile2Name = argv[2];
        string outFileName = argv[3];
        ofstream outFile;
        int fileDesc[2];
        char buffer[1024];
        int pipeIsCreate;
        int status;

        pipeIsCreate = pipe(fileDesc);
        if (pipeIsCreate == -1) {
            cerr << "pipe wasn't created" << endl;
            return 0;
        }
        outFile.open(outFileName);


        int childPid1 = createChild(fileDesc[1], inFile1Name);
        int childPid2 = createChild(fileDesc[1], inFile2Name);
        ssize_t numRead;

        //fcntl(fileDesc[0], F_SETFL, O_NONBLOCK);

        close(fileDesc[1]);

        while ((numRead = read(fileDesc[0], buffer, 1024)) > 0 && (waitpid(childPid1, &status, WNOHANG) == 0
                                                                   && waitpid(childPid2, &status, WNOHANG) == 0)) {

            cout << numRead << endl;
            outFile << buffer << endl;

        }

        cout << "After while" << endl;

        close(fileDesc[0]);

        outFile.close();

        cout << "Программа завершилась" << endl;

        return 0;
    } else {
        cerr << "вы неправильно ввели данные" << endl;
        return 0;
    }
}