#include <string>
#include <iostream>
#include <fstream>
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
        file.open(filename);
        file << "Задержки:" << " " << sec1 << " " << sec2 << " " << sec3 << endl;
    } else {
        cout << "error wrong arguments" << endl;
        exit(0);
    }

    __pid_t pid = fork();

    switch (pid) {
        case -1: {
            cout << "Error no child process was created" << endl;
            file.close();
            exit(0);
        }
        case 0: {
            sleep(sec2);
            file << "Потомок 1:" << endl;
            print_params(file);
            break;
        }
        default: {
            pid = vfork();

            if (pid == -1) {
                cout << "Error no child process was created" << endl;
                file.close();
                exit(0);
            } else if (pid == 0) {
                int a = execl("/home/denis/CLionProjects/Lab_3_execl/cmake-build-debug/Lab_3_execl", "name",
                              to_string(sec3).c_str(),
                              filename.c_str());
                cout << "a " << a << endl;
            } else {
                cout << "errno " << errno << endl;
                sleep(sec1);
                file << "Предок:" << endl;
                print_params(file);
                file.close();
            }
        }
    }
}