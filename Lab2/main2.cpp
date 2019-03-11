#include <iostream>
#include <mpich/mpi.h>
#include "mpi.h"
#include <cstdlib>

using namespace std;

int main(int argc, char **argv) {
    MPI_Init(&argc, &argv);
    int rank, proc_count;
    int res;
    MPI_Request req;
    int flag = 0;

    MPI_Comm_size(MPI_COMM_WORLD, &proc_count);

    MPI_Comm_rank(MPI_COMM_WORLD, &rank);

    switch (rank) {
        case 1: {
            MPI_Irecv(&res, 1, MPI_INT, 64, MPI_ANY_TAG, MPI_COMM_WORLD, &req);
            while (!flag) {
                cout << "Im's still waiting..." << " " << rank << endl;
                MPI_Test(&req, &flag, MPI_STATUS_IGNORE);
            }
            cout << "Receieved message from process" << " " << res << endl;
            break;
        }
        case 2: {
            MPI_Irecv(&res, 1, MPI_INT, 78, MPI_ANY_TAG, MPI_COMM_WORLD, &req);
            while (!flag) {
                cout << "Im's still waiting..." << " " << rank << endl;
                MPI_Test(&req, &flag, MPI_STATUS_IGNORE);
            }
            cout << "Receieved message from process" << " " << res << endl;
            break;
        }
        case 3: {
            MPI_Irecv(&res, 1, MPI_INT, 81, MPI_ANY_TAG, MPI_COMM_WORLD, &req);
            while (!flag) {
                cout << "Im's still waiting..." << " " << rank << endl;
                MPI_Test(&req, &flag, MPI_STATUS_IGNORE);
            }
            cout << "Receieved message from process" << " " << res << endl;
            break;
        }
        case 4: {
            MPI_Irecv(&res, 1, MPI_INT, 99, MPI_ANY_TAG, MPI_COMM_WORLD, &req);
            while (!flag) {
                cout << "Im's still waiting..." << " " << rank << endl;
                MPI_Test(&req, &flag, MPI_STATUS_IGNORE);
            }
            cout << "Receieved message from process" << " " << res << endl;
            break;
        }
        default: {
            for (int i = 0; i < 100; i++) {
                if (i != rank)
                    MPI_Send(&rank, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
            }
        }
    }
    MPI_Finalize();
    return 0;
}