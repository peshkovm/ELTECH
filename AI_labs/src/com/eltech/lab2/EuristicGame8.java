package com.eltech.lab2;

import com.eltech.common.*;
import com.eltech.lab1.Matrix;
import com.eltech.lab1.ProblemGame8;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class EuristicGame8 {

    public static void main(String[] args) throws IOException {
        Solution<Matrix, String> solution, solution1;
        long startTime1, endTime1, startTime2, endTime2;

        AStar<Matrix, String> obj = new AStar<>(Euristics.ManhattanDistance());
        Matrix matrix = new Matrix(new int[]{0, 4, 3, 6, 2, 1, 7, 5, 8});
        ProblemGame8 problem = new ProblemGame8(matrix);
        solution = obj.search(problem);
        new FileOutputStream("C:\\Users\\Пользователь\\Documents\\GitHub\\ELTECH\\AI_labs\\src\\com\\eltech\\common\\out.txt").write(solution.toString().getBytes());

    }
}
