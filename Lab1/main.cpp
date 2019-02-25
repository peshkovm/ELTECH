#include <iostream>
#include "mpi.h"

using namespace std;

int main(int argc, char **argv) {
    MPI_Init(&argc, &argv);
    int rank, proc_count, received_rank;
    MPI_Comm_size(MPI_COMM_WORLD, &proc_count);

    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    if (rank == 0) {
        for (int i = 0; i < proc_count; i++) {
            MPI_Recv(&received_rank, 1, MPI_INT, MPI_ANY_SOURCE, MPI_ANY_TAG, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            cout << received_rank << endl;
        }
    } else {
        MPI_Send(&rank, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
    }
    return 0;
}