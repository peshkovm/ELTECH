#include <string>
#include <iostream>
#include <fstream>
#include <unistd.h>
#include </usr/include/linux/sched.h>
#include <wait.h>

using namespace std;

#define STACK 1024*64

int threadJob(void *argFile) {
    ifstream *file = static_cast<ifstream *>(argFile);
    int policy = sched_getscheduler(0);
    string policyName;
    sched_param param;

    switch (policy) {
        case SCHED_FIFO:
            policyName = "SCHED_FIFO";
            break;
        case SCHED_RR:
            policyName = "SCHED_RR";
            break;
        case SCHED_OTHER:
            policyName = "SCHED_OTHER";
            break;
        default:
            policyName = "Unknown policy";
    }

    sched_getparam(0, &param);

    cout << "Класс планирования: " << policyName << endl;
    cout << "Минимальный приоритет: " << sched_get_priority_min(policy) << endl;
    cout << "Текущий приоритет: " << param.sched_priority << endl;
    cout << "Максимальный приоритет: " << sched_get_priority_max(policy) << endl;


    if (!file->is_open()) {
        cout << "File was not open by main thread" << endl;
        (*file).close();
        return 1;
    } else {
        string line;
        cout << "Содержимое файла:" << endl;
        while (getline(*file, line))
            cout << line << endl;
    }

    // (*file).close();
    return 0;
}

int main() {
    string fileName = "/home/max/Desktop/Razumovskiy_labi/Lab4/testFile.txt";
    ifstream file;
    void *stack = malloc(STACK);
    pid_t pid;
    sched_param param;

    file.open(fileName);

    pid = clone(&threadJob, (char *) stack + STACK,
                SIGCHLD | CLONE_FS | CLONE_FILES | CLONE_SIGHAND | CLONE_VM, &file);

    waitpid(pid, nullptr, 0);

    sched_getparam(0, &param);
    cout << "Текущий приоритет: " << param.sched_priority << endl;

    if (!file.is_open()) {
        cout << "Файл был закрыт потоком" << endl;
    } else {
        cout << "Файл не был закрыт потоком" << endl;
        cout << "Закрываю" << endl;
        file.close();
        if (!file.is_open())
            cout << "Файл закрыт" << endl;
        else
            cout << "Не удалось закрыть файл" << endl;
    }

    free(stack);

    return 0;
}