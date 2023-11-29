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
//            File f = new File(fileName);
//            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
//            oos.writeObject(msg);
//            System.out.println("write "+msg.numOfAttempts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        ArrayList<String> getAllToDos(Context context)  {
        StringBuilder sb = new StringBuilder();
            ArrayList<String> avg = new ArrayList<>();

////            FileInputStream fileIn = null;
//            ObjectInputStream objIn = null;
//            Average newDog = null;
//
//            try {
//                FileInputStream fileIn = context.openFileInput(fileName);
//                try {
//                    if(fileIn != null)
//                    objIn = new ObjectInputStream(fileIn);
//                } catch (IOException e) {
//                    Log.d("IOException","ee "+e);
//                }
//                // Reads the objects
//                newDog = (Average) objIn.readObject();
//                avg.add(new Average(newDog.totalNumOfCorrectAnswer,newDog.totalNumOfQuestionsAnswered, newDog.numOfAttempts,
//                        newDog.scoreIs, newDog.div, newDog.in, newDog.att));
//
//            } catch (ClassNotFoundException | IOException e) {
//                throw new RuntimeException(e);
//            }
//             return avg;







        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {

                String line = reader.readLine();
                System.out.println("line "+line);
                while (line != null) {
//                    sb.append("\n");
                    sb.append(line);
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
//        return sb.toString();
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
