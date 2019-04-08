#include <mpi.h>
#include <vector>

int main(int argc, char** argv) {
  MPI_Init(&argc, &argv);

  int proc_num;
  int self_rank;

  MPI_Comm_rank(MPI_COMM_WORLD, &self_rank);
  MPI_Comm_size(MPI_COMM_WORLD, &proc_num);

  int matrix[3][3] = {
    1,2,3,
    2,3,5,
    5,5,7
  };

  MPI_Group world_group;
  MPI_Comm_group(MPI_COMM_WORLD, &world_group);

  int n = proc_num / 2;
  std::vector<int> ranks_first;

  // Fill first group (Process with rank = 0 must be there!)
  for (int i = 0; i < n; i++)
  {
    ranks_first.push_back(i);
  }

  MPI_Group first_group;
  MPI_Group_incl(world_group, n, ranks_first.data(), &first_group);

  MPI_Comm first_comm;
  MPI_Comm_create_group(MPI_COMM_WORLD, first_group, 0, &first_comm);

  // std::vector<int> data_for_first;
  // data_for_first.push_back(matrix[0][0]);
  // data_for_first.push_back(matrix[1][1]);
  // data_for_first.push_back(matrix[2][2]);
  //
  // data_for_first.push_back(matrix[0][1]);
  // data_for_first.push_back(matrix[1][2]);
  // data_for_first.push_back(matrix[2][0]);
  //
  // data_for_first.push_back(matrix[0][2]);
  // data_for_first.push_back(matrix[1][0]);
  // data_for_first.push_back(matrix[2][1]);

  if (first_comm != MPI_COMM_NULL)
  {
    auto res = matrix[0][0]*matrix[1][1]*matrix[2][2] +
               matrix[0][1]*matrix[1][2]*matrix[2][0] +
               matrix[0][2]*matrix[1][0]*matrix[2][1];

    MPI_Send(&res, 1, MPI_INT, proc_num - 1, 0, MPI_COMM_WORLD); // send to boundary process
  }

  // Fill second group
  std::vector<int> second_ranks;
  for (int i = n; i < proc_num; i++)
  {
    second_ranks.push_back(i);
  }

  MPI_Group second_group;
  MPI_Group_incl(world_group, n, second_ranks.data(), &second_group);

  MPI_Comm second_comm;
  MPI_Comm_create_group(MPI_COMM_WORLD, second_group, 0, &second_comm);

  if (second_comm != MPI_COMM_NULL)
  {
    auto res = matrix[0][2]*matrix[1][1]*matrix[2][0] +
               matrix[0][0]*matrix[1][2]*matrix[2][1] +
               matrix[0][1]*matrix[1][0]*matrix[2][2];

    int res_from_first;

    if (self_rank == proc_num - 1)
    {
      MPI_Recv(&res_from_first, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

      // end of calculation

      auto final_det = res_from_first - res;

      printf("Determinant is %d\n", final_det);
    }
  }


  MPI_Finalize();
  return 0;
}
