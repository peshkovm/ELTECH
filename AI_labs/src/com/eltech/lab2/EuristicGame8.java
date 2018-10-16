package com.eltech.lab2;

import com.eltech.common.*;
import com.eltech.lab1.Matrix;
import com.eltech.lab1.ProblemGame8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class EuristicGame8 {

    public static void main(String[] args) throws IOException {
        Solution<Matrix, String> solution, solution1;
        long startTime1, endTime1, startTime2, endTime2;

        while (true) {
            AStar<Matrix, String> obj = new AStar<>(Euristics.ManhattanDistance());
            IDSSearch<Matrix, String> obj1 = new IDSSearch<>();
            Matrix matrix = new Matrix(new int[]{2, 7, 8, 4, 6, 5, 1, 3, 0});
            ProblemGame8 problem = new ProblemGame8(matrix);
            startTime1 = System.currentTimeMillis();
            solution = obj.search(problem);
            endTime1 = System.currentTimeMillis() - startTime1;
            if (solution.isExists()) {
                startTime2 = System.currentTimeMillis();
                solution1 = obj1.search(problem);
                endTime2 = System.currentTimeMillis() - startTime2;
                System.out.println(solution);
                System.out.println(solution1);
                System.out.println("time in ms=" + endTime1 + " time complexity=" + solution.time() + " time in ms A*=" + endTime2 + " time complexity A*=" + solution1.time() + " depth1=" + solution.depth() + " depth2=" + solution1.depth());
            }
        }
    }
}
