#ifndef LAB16_PR_COMMON_H
#define LAB16_PR_COMMON_H

#include <stdio.h>
#include "unistd.h"
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <stdlib.h>
#include "sys/types.h"
#include "errno.h"
#include<string>
#include<iostream>
#include<fstream>

using namespace std;

string fname = "/home/denis/Desktop/ELTECH/ELTECH/Razumovskiy labs/Lab10/Lab10_2_1_1/file.txt";
int key = 101;
int sem_id;
int master_sem = 0;
int sem_size = 3;
struct sembuf dec0, inc0, dec1, inc1, check2, check1, inc2, dec2;

void get_sem() {
    if ((sem_id = semget(key, sem_size, IPC_CREAT | 0666)) < 0) {
        cerr << "Error semaphore" << endl;
        exit(-1);
    }
    semctl(sem_id, 0, SETVAL, 1);
    semctl(sem_id, 1, SETVAL, 0);
    semctl(sem_id, 2, SETVAL, 0);
    master_sem = 1;
}

void del_sem() {
    printf("del sem\n");
    int ncnt = 0;
    int zcnt = 0;
    do {
        if (semctl(sem_id, 0, GETNCNT, &ncnt) == -1) {
            printf("Err get ncnt\n");
            printf("errno = %d\n", errno);
        }
        if (semctl(sem_id, 0, GETZCNT, &zcnt) == -1) {
            printf("Err get zcnt\n");
            printf("errno = %d\n", errno);
        }
        printf("ncnt = %d, zcnt = %d\n", ncnt, zcnt);
    } while (ncnt + zcnt != 0);
    if (semctl(sem_id, 0, IPC_RMID) == -1) {
        printf("Err deleting sem\n");
        printf("errno = %d\n", errno);
    }
}

void init_operations() {
    dec0.sem_num = 0;
    dec0.sem_op = -1;
    dec0.sem_flg = 0;

    inc0.sem_num = 0;
    inc0.sem_op = 1;
    inc0.sem_flg = 0;

    dec1.sem_num = 1;
    dec1.sem_op = -1;
    dec1.sem_flg = 0;

    inc1.sem_num = 1;
    inc1.sem_op = 1;
    inc1.sem_flg = 0;

    check2.sem_num = 2;
    check2.sem_op = 0;
    check2.sem_flg = 0;

    check1.sem_num = 1;
    check1.sem_op = 0;
    check1.sem_flg = 0;

    inc2.sem_num = 2;
    inc2.sem_op = 1;
    inc2.sem_flg = 0;

    dec2.sem_num = 2;
    dec2.sem_op = -1;
    dec2.sem_flg = 0;
}

void print_sem() {
    printf("sem = %d, %d, %d\n", semctl(sem_id, 0, GETVAL, 0), semctl(sem_id, 1, GETVAL, 0),
           semctl(sem_id, 2, GETVAL, 0));
}

#endif //LAB16_PR_COMMON_H