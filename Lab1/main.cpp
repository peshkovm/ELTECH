#include <iostream>
#include "mpi.h"

using namespace std;

long fibonachi(int n) {
    int prev1 = 0, prev2 = 1;
    int a = prev1;
    if (n == 0) return 0;
    if (n == 1) return 1;
    for (int i = 1; i < n; i++) {
        a = prev1 + prev2;
        prev1 = prev2;
        prev2 = a;
    }
    return a;
}

int main(int argc, char **argv) {
    MPI_Init(&argc, &argv);
    int rank, proc_count, res_;
    MPI_Comm_size(MPI_COMM_WORLD, &proc_count);

    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    if (rank == 0) {
        for (int i = 1; i < proc_count; i++) {
            MPI_Recv(&res_, 1, MPI_INT, i, MPI_ANY_TAG, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            cout << "res " << res_ << endl;
        }
    } else {
        long res = fibonachi(rank);
        MPI_Send(&res, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
    }
    MPI_Finalize();
    return 0;
}