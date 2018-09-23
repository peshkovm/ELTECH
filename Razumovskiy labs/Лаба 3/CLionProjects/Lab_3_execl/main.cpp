#include <fstream>
#include <iostream>
#include <unistd.h>

using namespace std;

void print_params(ofstream &);

int main(int argc, char **argv) {
    ofstream file;
    unsigned sec;
    if (argc == 3) {
        sec = static_cast<unsigned int>(atoi(argv[1]));
        file.open(argv[2], ofstream::app);
        cout << "execl file.fail " << file.fail() << endl;
    } else {
        cout << "error wrong arguments from execl" << endl;
        exit(0);
    }

    sleep(sec);
    file << "Потомок 2:" << endl;
    print_params(file);
}