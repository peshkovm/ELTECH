#include "common.h"

int id;
int str_count;

void write_line() {
    ofstream file = ofstream(fname, std::ofstream::app);
    string str = "Writer";
    file << str + " " + to_string(id) << endl;
    cout << str + " " + to_string(id) << endl;
}

void writer_fun() {
    //print_sem();
    //printf("inc1\n");
    semop(sem_id, &inc1, 1);
    //print_sem();
    //printf("check2\n");
    semop(sem_id, &check2, 1);
    //print_sem();
    //printf("dec0\n");
    semop(sem_id, &dec0, 1);
    //print_sem();
    //printf("write\n");
    write_line();
    //print_sem();
    //printf("inc0\n");
    semop(sem_id, &inc0, 1);
    //print_sem();
    //printf("dec1\n");
    semop(sem_id, &dec1, 1);
    //print_sem();
}

void work() {
    for (int i = 0; i < str_count; ++i) {
        writer_fun();
    }
}

int main(int argc, char *argv[]) {
    id = atoi(argv[1]);
    str_count = 1;
    get_sem();
    init_operations();

    work();
    /*if (master_sem == 1)
        del_sem();*/
    return 0;
}