#include "common.h"
int id;
int str_count;

void write_line() {
    FILE* fout = fopen(fname, "a+");
    char str[20];
    sprintf(str, "Writer %d\n", id);
    fputs(str, fout);
    printf("%s", str);
    fclose(fout);
}

void writer_fun() {
    print_sem();
    printf("inc1\n");
    semop(sem_id, &inc1, 1);
    print_sem();
    printf("check2\n");
    semop(sem_id, &check2, 1);
    print_sem();
    printf("dec0\n");
    semop(sem_id, &dec0, 1);
    print_sem();
    printf("write\n");
    sleep(3);
    write_line();
    sleep(3);
    print_sem();
    printf("inc0\n");
    semop(sem_id, &inc0, 1);
    print_sem();
    printf("dec1\n");
    semop(sem_id, &dec1, 1);
    print_sem();
}

void work() {
    for (int i = 0; i < str_count; ++i) {
        writer_fun();
    }
}

int main(int argc, char* argv[]) {
    id = atoi(argv[1]);
    str_count = atoi(argv[2]);
    get_sem();
    printf("master sem = %d\n", master_sem);
    init_operations();

    work();
    /*if(master_sem == 1)
        del_sem();*/
    return 0;
}