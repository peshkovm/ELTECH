#include <mpi.h>
#include <vector>

int main(int argc, char** argv) {
  MPI_Init(&argc, &argv);

  int size;
  int rank;

  MPI_Comm_rank(MPI_COMM_WORLD, &rank);
  MPI_Comm_size(MPI_COMM_WORLD, &size);
  
  MPI_Comm graph_comm;
  int nnodes = size; /* number of nodes */
  int even=(size-1)/2;
  int odd=(size+1)/2-2;
  int index[size];
  int edges[(even+odd)*2];
  
  index[0]=even;
  index[1]=odd;
  
  for(int i=0;i<size-2;i++) {
	  index[i]=index[i-1]+1;
  }
  
  for(int i=0,j=2;i<even;i++,j+=2;) {
	  edges[i]=j;
  }
  
  for(int i=even,j=1;i<even+odd;i++,j+=2;) {
	  edges[i]=j;
  }
  
  for(int i=even+odd;i<2*(even+odd);i++) {
	  edges[i]=(i-(even+odd))%2;
  }
  
  int reorder = 1; /* allows processes reordered for efficiency */
  MPI_Graph_create(MPI_COMM_WORLD, nnodes, index, edges, reorder, graph_comm); 

  MPI_Finalize();
  return 0;
}