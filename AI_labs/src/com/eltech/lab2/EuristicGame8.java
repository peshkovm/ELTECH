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
        AStar<Matrix, String> obj = new AStar<>(Euristics.ManhattanDistance());
        ProblemGame8 problem = new ProblemGame8(new Matrix(new int[]{0, 4, 3, 6, 2, 1, 7, 5, 8}, 0));
        Solution<Matrix, String> solution;
        long startTime;

        /*while (true) {
            startTime = System.currentTimeMillis();
            solution = obj.search(problem);
            System.out.println(solution);
            System.out.println("memory=" + solution.memory() + " time in ms=" + (System.currentTimeMillis() - startTime) + " time complexity=" + solution.time() + " depth=" + solution.depth());

            System.out.println("Do you want to continue? y/n");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            if (reader.readLine().equals("n")) break;
        }*/

        obj.searhStep(problem);
    }
}
