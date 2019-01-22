#include "common.h"

void read_file() {
    FILE* fin = fopen(fname, "r");
    char* str = NULL;
    size_t size = 0;
    while(getline(&str, &size, fin) != -1){
        printf("%s", str);
        str = NULL;
        size = 0;
    }
    fclose(fin);
}

void reader_fun() {
    printf("reader fun\n");
    print_sem();
    printf("check1\n");
    semop(sem_id, &check1, 1);
    print_sem();
    printf("inc2\n");
    semop(sem_id, &inc2, 1);
    print_sem();
    printf("read\n");
    sleep(3);
    read_file();
    sleep(3);
    print_sem();
    printf("dec2\n");
    semop(sem_id, &dec2, 1);
    print_sem();
}

void work() {
    reader_fun();
}

int main() {
    get_sem();
    printf("master sem = %d\n", master_sem);
    init_operations();
    work();
    /*if(master_sem == 1)
        del_sem();*/
    return 0;
}