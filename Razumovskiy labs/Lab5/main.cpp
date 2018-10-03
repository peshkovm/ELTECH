#include <iostream>
#include <csignal>

using namespace std;

void signalHandler(int sigNum) {
    switch (sigNum) {
        case SIGFPE:
            cout << "SIGFPE occurred" << endl;
            exit(1);
        case SIGSEGV:
            cout << "SIGSEGV occurred" << endl;
            exit(2);
    }
}

int main(int argc, char *argv[]) {
    int sig = atoi(argv[1]);
    __sighandler_t funcPtr;

    funcPtr = signal(sig, signalHandler);
    if (funcPtr == SIG_ERR)
        cout << "signal() returned error" << endl;

    if (argc == 2)
        raise(sig);

    return 0;
}