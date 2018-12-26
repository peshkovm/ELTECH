package com.eltech.lab1;

//

import com.eltech.common.*;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Game8 {

    public static void main(String[] args) throws IOException {
        Solution<Matrix, String> solution;
        long startTime;

        Matrix mas = new Matrix(new int[]{6, 2, 8, 4, 1, 7, 5, 3, 0});
        ProblemGame8 problem = new ProblemGame8(mas);
        IDSSearch<Matrix, String> obj = new IDSSearch<>();
        solution = obj.search(problem);
        new FileOutputStream("C:\\Users\\Пользователь\\Documents\\GitHub\\ELTECH\\AI_labs\\src\\com\\eltech\\common\\out.txt").write(solution.toString().getBytes());
        //System.out.println(solution);
    }
}