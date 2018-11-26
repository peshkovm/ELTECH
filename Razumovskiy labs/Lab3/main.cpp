#include <string>
#include <iostream>
#include <fstream>
#include <wait.h>
#include "/home/max/Desktop/Razumovskiy_labi/Lab3/writeAttrs.h"

using namespace std;

ofstream file;
string fileName;

int createNewProcesses(int sec1, int sec2, int sec3) {
    int pid1 = fork();

    switch (pid1) {
        case -1: {
            cout << "Error no child process was created" << endl;
            file.close();
            exit(0);
        }
        case 0: {
            sleep(sec2);
            file << "Потомок 1:" << endl;
            writeAttrs(file);
            file.close();
            break;
        }
        default: {
            int pid2 = vfork();

            if (pid2 == -1) {
                cout << "Error no child process was created" << endl;
                file.close();
                exit(0);
            } else if (pid2 == 0) {
                execl("/home/max/Desktop/Razumovskiy_labi/TestProject/cmake-build-debug/TestProject", "name",
                      fileName.c_str(),
                      to_string(sec3).c_str());
            } else {
                sleep(sec1);
                file << "Предок:" << endl;
                writeAttrs(file);
                waitpid(pid1, nullptr, 0);
                waitpid(pid2, nullptr, 0);
                file.close();
            }
        }
    }
}

int main(int argc, char *argv[]) {
    unsigned sec1, sec2, sec3;

    if (argc == 5) {
        fileName = argv[1];
        sec1 = static_cast<unsigned int>(atoi(argv[2]));
        sec2 = static_cast<unsigned int>(atoi(argv[3]));
        sec3 = static_cast<unsigned int>(atoi(argv[4]));
        file.open(fileName, ofstream::trunc);
        file.close();
        file.open(fileName, ofstream::app);
    } else {
        cout << "Вы неправильно ввели параметры" << endl;
        exit(0);
    }

    file << "Задержки: " << sec1 << " " << sec2 << " " << sec3 << endl;
    createNewProcesses(sec1, sec2, sec3);

    return 0;
}