# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.12

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /home/denis/.local/share/JetBrains/Toolbox/apps/CLion/ch-0/182.4505.18/bin/cmake/linux/bin/cmake

# The command to remove a file.
RM = /home/denis/.local/share/JetBrains/Toolbox/apps/CLion/ch-0/182.4505.18/bin/cmake/linux/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = "/home/denis/Desktop/ELTECH/ELTECH/Razumovskiy labs/Lab4_2"

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = "/home/denis/Desktop/ELTECH/ELTECH/Razumovskiy labs/Lab4_2/cmake-build-debug"

# Include any dependencies generated for this target.
include CMakeFiles/Lab4_2.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/Lab4_2.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/Lab4_2.dir/flags.make

CMakeFiles/Lab4_2.dir/main.cpp.o: CMakeFiles/Lab4_2.dir/flags.make
CMakeFiles/Lab4_2.dir/main.cpp.o: ../main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="/home/denis/Desktop/ELTECH/ELTECH/Razumovskiy labs/Lab4_2/cmake-build-debug/CMakeFiles" --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/Lab4_2.dir/main.cpp.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Lab4_2.dir/main.cpp.o -c "/home/denis/Desktop/ELTECH/ELTECH/Razumovskiy labs/Lab4_2/main.cpp"

CMakeFiles/Lab4_2.dir/main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Lab4_2.dir/main.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E "/home/denis/Desktop/ELTECH/ELTECH/Razumovskiy labs/Lab4_2/main.cpp" > CMakeFiles/Lab4_2.dir/main.cpp.i

CMakeFiles/Lab4_2.dir/main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Lab4_2.dir/main.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S "/home/denis/Desktop/ELTECH/ELTECH/Razumovskiy labs/Lab4_2/main.cpp" -o CMakeFiles/Lab4_2.dir/main.cpp.s

# Object files for target Lab4_2
Lab4_2_OBJECTS = \
"CMakeFiles/Lab4_2.dir/main.cpp.o"

# External object files for target Lab4_2
Lab4_2_EXTERNAL_OBJECTS =

Lab4_2: CMakeFiles/Lab4_2.dir/main.cpp.o
Lab4_2: CMakeFiles/Lab4_2.dir/build.make
Lab4_2: CMakeFiles/Lab4_2.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir="/home/denis/Desktop/ELTECH/ELTECH/Razumovskiy labs/Lab4_2/cmake-build-debug/CMakeFiles" --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable Lab4_2"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/Lab4_2.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/Lab4_2.dir/build: Lab4_2

.PHONY : CMakeFiles/Lab4_2.dir/build

CMakeFiles/Lab4_2.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/Lab4_2.dir/cmake_clean.cmake
.PHONY : CMakeFiles/Lab4_2.dir/clean

CMakeFiles/Lab4_2.dir/depend:
	cd "/home/denis/Desktop/ELTECH/ELTECH/Razumovskiy labs/Lab4_2/cmake-build-debug" && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" "/home/denis/Desktop/ELTECH/ELTECH/Razumovskiy labs/Lab4_2" "/home/denis/Desktop/ELTECH/ELTECH/Razumovskiy labs/Lab4_2" "/home/denis/Desktop/ELTECH/ELTECH/Razumovskiy labs/Lab4_2/cmake-build-debug" "/home/denis/Desktop/ELTECH/ELTECH/Razumovskiy labs/Lab4_2/cmake-build-debug" "/home/denis/Desktop/ELTECH/ELTECH/Razumovskiy labs/Lab4_2/cmake-build-debug/CMakeFiles/Lab4_2.dir/DependInfo.cmake" --color=$(COLOR)
.PHONY : CMakeFiles/Lab4_2.dir/depend

