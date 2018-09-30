#include <string>
#include <iostream>
#include <fstream>
#include <wait.h>
#include"printparams.h"

using namespace std;

ofstream file;

int main(int argc, char **argv) {
    unsigned sec1;
    unsigned sec2;
    unsigned sec3;
    string filename;

    if (argc == 5) {
        filename = argv[1];
        sec1 = static_cast<unsigned int>(atoi(argv[2]));
        sec2 = static_cast<unsigned int>(atoi(argv[3]));
        sec3 = static_cast<unsigned int>(atoi(argv[4]));
        file.open(filename, ofstream::trunc);
        file.close();
        file.open(filename, ofstream::app);
        file << "Задержки:" << " " << sec1 << " " << sec2 << " " << sec3 << endl;
    } else {
        cout << "error wrong arguments" << endl;
        exit(0);
    }

    __pid_t pid1 = fork();

    switch (pid1) {
        case -1: {
            cout << "Error no child process was created" << endl;
            file.close();
            exit(0);
        }
        case 0: {
            sleep(sec2);
            file << "Потомок 1:" << endl;
            cout << "Потомок 1" << endl;
            print_params(file);
            break;
        }
        default: {
            __pid_t pid2 = vfork();

            if (pid2 == -1) {
                cout << "Error no child process was created" << endl;
                file.close();
                exit(0);
            } else if (pid2 == 0) {
                execl(
                        "/home/denis/Desktop/ELTECH/Razumovskiy labs/Лаба 3/CLionProjects/Lab_3_execl/cmake-build-debug/Lab_3_execl",
                        "name",
                        to_string(sec3).c_str(),
                        filename.c_str());
                cout << "error with excl" << endl;
            } else {
                sleep(sec1);
                file << "Предок:" << endl;
                cout << "Предок" << endl;
                print_params(file);
                //waitpid(pid1, nullptr, 0);
                //waitpid(pid2, nullptr, 0);
                file.close();
            }
        }
    }
}