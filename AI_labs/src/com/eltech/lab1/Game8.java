package com.eltech.lab1;

//

import com.eltech.common.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Game8 {

    public static void main(String[] args) throws IOException {
        BFSSearch<Matrix, String> obj = new BFSSearch<>();
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