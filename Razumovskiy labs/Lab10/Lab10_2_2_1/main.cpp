#include "common.h"

int id;

void read_file() {
    ifstream file = ifstream(fname);
    string str;
    while (getline(file, str)) {
        cout << "Reader" << " " << id << " " << "\"" << str << "\"" << endl;
    }
}

void reader_fun() {
    //printf("reader fun\n");
    //print_sem();
    //printf("check1\n");
    semop(sem_id, &check1, 1);
    //print_sem();
    //printf("inc2\n");
    semop(sem_id, &inc2, 1);
    //print_sem();
    //printf("read\n");
    read_file();
    //print_sem();
    //printf("dec2\n");
    semop(sem_id, &dec2, 1);
    //print_sem();
}

void work() {
    reader_fun();
}

int main(int argc, char *argv[]) {
    id = atoi(argv[1]);
    get_sem();
    init_operations();
    work();
//    if (master_sem == 1)
//        del_sem();
    return 0;
}