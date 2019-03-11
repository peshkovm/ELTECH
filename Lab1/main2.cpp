#include <iostream>
#include <mpich/mpi.h>
#include "mpi.h"

using namespace std;


int main(int argc, char **argv) {
    MPI_Init(&argc,&argv);
    int rank, proc_count,receive_rank;
    MPI_Comm_size(MPI_COMM_WORLD, &proc_count);

    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    if (rank == 0) {
        for (int i = proc_count-1; i > 0; i--) {
            MPI_Recv(&receive_rank, 1, MPI_INT, i, MPI_ANY_TAG, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

            cout << "received rank " << receive_rank << endl;
        }
    } else {
        MPI_Send(&rank, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
    }
    MPI_Finalize();
    return 0;
}