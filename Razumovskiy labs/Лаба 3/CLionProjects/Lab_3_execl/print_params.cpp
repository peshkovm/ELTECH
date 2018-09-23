//
// Created by denis on 23.09.18.
//

//
// Created by denis on 23.09.18.
//

#include <fstream>
#include <unistd.h>

using namespace std;

void print_params(ofstream &file) {
    file << "Идентификатор процесса=" << getpid() << endl;
    file << "Идентификатор процесса=" << getppid() << endl;
    file << "Идентификатор сессии процесса=" << getsid(0) << endl;
    file << "Идентификатор группы процессов=" << getpgid(0) << endl;
    file << "Реальный идентификатор пользователя=" << getuid() << endl;
    file << "Эффективный идентификатор пользователя=" << geteuid() << endl;
    file << "Реальный групповой идентификатор=" << getgid() << endl;
    file << "Эффективнй групповой идентификатор=" << getegid() << endl;
}