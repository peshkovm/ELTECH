#include <iostream>
#include <unistd.h>
#include <sys/time.h>
#include <signal.h>

using namespace std;

time_t startParent;
time_t endParent;
int numRepeat;
int i = 0;

void createNewProcess(int sigNum) {
    if (sigNum == SIGALRM) {
        i++;
        int pid1 = fork();

        switch (pid1) {
            case -1: {
                cout << "Error no child process was created" << endl;
                exit(1);
            }
            case 0: {
                time_t startChild;
                time_t endChild;

                startChild = time(nullptr);
                //i++;
                time_t sec;
                sec = time(nullptr);

                cout << "pid = " << getpid() << endl;
                cout << "Текущее время и дата: " << ctime(&sec);


                endChild = time(nullptr);

                double diffClidTime = difftime(endChild, startChild);

                endParent = time(nullptr);
                double diffParentTime = difftime(endParent, startParent);
                cout << "Parent time: " << diffParentTime << endl;

                cout << "Child time: " << diffClidTime << endl << endl;

                exit(1);
            }
            default: {
                if (i >= numRepeat) {
/*                    endParent = time(nullptr);
                    double diffParentTime = difftime(endParent, startParent);
                    cout << "\nTotal parent time: " << diffParentTime << endl;*/
                    exit(0);
                }
            }
        }
    }
}

int main(int argc, char *argv[]) {
    startParent = time(nullptr);

    if (argc != 3)
        return 1;

    long interval = 1;
    numRepeat = atoi(argv[1]);
    long value = atoi(argv[2]);
    itimerval A;
    __sighandler_t funcPtr;
    char ch;

    A.it_interval.tv_sec = interval;
    A.it_interval.tv_usec = 0;
    A.it_value.tv_sec = value;
    A.it_value.tv_usec = 0;

    time_t sec;
    sec = time(nullptr);
    cout << "Start time of the program" << ctime(&sec) << endl;

    setitimer(ITIMER_REAL, &A, nullptr);

    funcPtr = signal(SIGALRM, createNewProcess);
    if (funcPtr == SIG_ERR) {
        cout << "signal() returned error" << endl;
        return 1;
    }

    while (true);
}