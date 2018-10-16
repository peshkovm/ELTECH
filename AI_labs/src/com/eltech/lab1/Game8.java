package com.eltech.lab1;

//

import com.eltech.common.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Game8 {

    public static void main(String[] args) throws IOException {
        Solution<Matrix, String> solution;
        long startTime;

        while (true) {
            Matrix mas = new Matrix(new int[]{3, 4, 6, 0, 2, 1, 7, 5, 8});
            ProblemGame8 problem = new ProblemGame8(mas);
            IDSSearch<Matrix, String> obj = new IDSSearch<>();
            startTime = System.currentTimeMillis();
            solution = obj.search(problem);

            //System.out.println(solution);
           /* System.out.print("max_depth=" + obj.maxPathCost + " ");
            System.out.println("memory=" + solution.memory() + " time in ms=" + (System.currentTimeMillis() - startTime) + " time complexity=" + solution.time() + " depth=" + solution.depth());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Do you want to continue? y/n");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            reader.readLine();*/

            if (solution.isExists()) {
                int a = solution.node.getPath_Cost();
                System.out.println(solution.node.getPath_Cost() + " " + (System.currentTimeMillis() - startTime) + " " + solution.time() + " " + solution.depth());
            }
            //if (reader.readLine().equals("n")) break;
        }

        //obj.searhStep(problem);
    }
}