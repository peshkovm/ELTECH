cmake_minimum_required(VERSION 3.13)
project(Lab1)

set(CMAKE_CXX_STANDARD 14)

find_package(MPI REQUIRED)

include_directories(SYSTEM ${MPI_INCLUDE_PATH})
link_libraries(${MPI_LIBRARIES})

add_executable(Lab1 main.cpp)