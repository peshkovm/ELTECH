//
// Created by max on 23.09.18.
//

#include "/home/max/Desktop/Razumovskiy_labi/Lab3/writeAttrs.h"

void writeAttrs(ofstream &file) {
    file << "Идентификатор процесса = " << getpid() << endl;
    file << "Иденитификатор предка = " << getppid() << endl;
    file << "Идентификатор сесси процесса = " << getsid(getpid()) << endl;
    file << "Идентификатор группы процессов = " << getpgid(getpid()) << endl;
    file << "Реальный идентификатор пользователя = " << getuid() << endl;
    file << "Эффективный идентификатор пользователя = " << geteuid() << endl;
    file << "Реальный групповой идентификатор = " << getgid() << endl;
    file << "Эффективный групповой идентификатор = " << getegid() << endl;
}