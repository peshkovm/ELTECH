#include <iostream>
#include <fstream>
#include <sys/shm.h>
#include <sys/ipc.h>
#include <sys/types.h>
#include <sys/sem.h>
#include <unistd.h>
#include<stdlib.h>

using namespace std;

int main() {
    string str = "\"/home/denis/Desktop/ELTECH/ELTECH/Razumovskiy labs/Lab10/Lab10_2_1_1/cmake-build-debug/Lab10_2_1_1\"";
    string str1 = "\"/home/denis/Desktop/ELTECH/ELTECH/Razumovskiy labs/Lab10/Lab10_2_2_1/cmake-build-debug/Lab10_2_2_1\"";

    for (int i = 0; i < 5; i++) {
        system((str + " " + to_string(i) + " " + "&").c_str());
        system((str1+" " + to_string(i)).c_str());
    }
    while (true);
}