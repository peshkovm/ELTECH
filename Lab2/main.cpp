#include <iostream>
#include <mpich/mpi.h>
#include "mpi.h"
#include <cstdlib>
#include <cmath>

using namespace std;

int main(int argc, char **argv) {
    MPI_Init(&argc, &argv);
    int rank, proc_count;
    float res[1000];
    float buf[1000];
    float *buf1;
    int size1;
    MPI_Comm_size(MPI_COMM_WORLD, &proc_count);

    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Buffer_attach(buf, 2 * sizeof(float) + MPI_BSEND_OVERHEAD);

    if (rank == 2) {
        MPI_Recv(res, 2, MPI_FLOAT, MPI_ANY_SOURCE, MPI_ANY_TAG, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        cout << "res " << res[0] << " " << res[1] << endl;
    } else if (rank == 0) {
        srand(time(NULL));
        //int arr[2] = {rand() % 11, rand() % 11}; //буфер сделать
        buf[0]=(float)pow(rand()%11,2);
        buf[1]=(float)pow(rand()%11,2);
        //кварат рандома вместо числа
        MPI_Request req;
        MPI_Ibsend(buf,2, MPI_FLOAT, 2, 0, MPI_COMM_WORLD, &req);
    }

    MPI_Buffer_detach(&buf1, &size1);
    MPI_Finalize();
    return 0;
}