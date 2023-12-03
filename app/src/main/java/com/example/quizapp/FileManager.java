package com.example.quizapp;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileManager {

    String fileName = "Result.txt";

    void writeResultToFile(Context context,String msg){
        try {
            deleteAllToDos(context);

            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_APPEND);
            fos.write(msg.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        ArrayList<String> getAllToDos(Context context)  {
            ArrayList<String> avg = new ArrayList<>();

        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {

                String line = reader.readLine();
                System.out.println("line "+line);
                while (line != null) {
                    avg.add(line);
                    line = reader.readLine();
                }
            }  catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return avg;
    }

    void deleteAllToDos(Context context){
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write("".getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Integer> extractScores(ArrayList<String> inputList) {
        ArrayList<Integer> scores = new ArrayList<>();
        Pattern pattern = Pattern.compile("-\\d+");

        for (String input : inputList) {
            Matcher matcher = pattern.matcher(input);

            while (matcher.find()) {
                String match = matcher.group();
                int score = Integer.parseInt(match.substring(1)); // Remove the hyphen
                scores.add(score);
            }
        }

        return scores;
    }

}
