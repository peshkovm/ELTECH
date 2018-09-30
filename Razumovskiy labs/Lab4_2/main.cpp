#include <string>
#include <iostream>
#include <fstream>
#include <unistd.h>
#include </usr/include/linux/sched.h>
#include <wait.h>

using namespace std;

#define STACK 1024*64

ofstream outFile1;
ofstream outFile2;

int thread1Job(void *argLine) {
    string line = *(static_cast<string *>(argLine));
    outFile1 << line << endl;
    return 0;
}

int thread2Job(void *argLine) {
    string line = *(static_cast<string *>(argLine));
    outFile2 << line << endl;
    return 0;
}

int main() {
    string inFileName = "/home/max/Desktop/Razumovskiy_labi/Lab4_2/inFile.txt";
    //string inFileName = "/lib64/ld-linux-x86-64.so.2";
    string outFile1Name = "/home/max/Desktop/Razumovskiy_labi/Lab4_2/outFile1.txt";
    string outFile2Name = "/home/max/Desktop/Razumovskiy_labi/Lab4_2/outFile2.txt";
    ifstream inFile;
    void *stack1 = malloc(STACK);
    void *stack2 = malloc(STACK);

    inFile.open(inFileName);
    outFile1.open(outFile1Name);
    outFile2.open(outFile2Name);

    string line;
    int lineCount = 0;
    while (getline(inFile, line)) {
        pid_t pid1, pid2;
        if (lineCount % 2 == 0) {
            pid1 = clone(&thread1Job, (char *) stack1 + STACK,
                         SIGCHLD | CLONE_FS | CLONE_FILES | CLONE_SIGHAND | CLONE_VM, &line);
            waitpid(pid1, nullptr, 0);
        } else {
            pid2 = clone(&thread2Job, (char *) stack2 + STACK,
                         SIGCHLD | CLONE_FS | CLONE_FILES | CLONE_SIGHAND | CLONE_VM, &line);
            waitpid(pid2, nullptr, 0);
        }
        //waitpid(pid1, nullptr, 0);
        //waitpid(pid2, nullptr, 0);
        lineCount++;
    }

    free(stack1);
    free(stack2);

    inFile.close();
    outFile1.close();
    outFile2.close();

    return 0;
}