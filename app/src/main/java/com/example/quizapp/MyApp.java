package com.example.quizapp;

import static java.util.Collections.shuffle;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;

public class MyApp extends Application {

    ArrayList<Question> listOfShuffledQuestion ;
    ArrayList<Integer> listOfShuffledColors;

    FileManager fileManager = new FileManager();
    int index = 0;
    int correctAnswer = 0;
    int totalNumOfCorrectAnswer = 0;
    int numOfAttempts = 0;
    int progress = 0;
    QuestionBank qb ;

    public ArrayList<Integer> getListOfShuffledColors(Context context) {
        if(qb == null){
            qb = new QuestionBank();
        }
        if(listOfShuffledColors == null){
            listOfShuffledColors = new ArrayList<>();
             listOfShuffledColors.addAll(qb.getColorCodeList(context));
        }
        Collections.shuffle(listOfShuffledColors);
        return listOfShuffledColors;
    }

    public ArrayList<Question> getListOfShuffledQuestion(Context context) {
        if(qb == null){
           qb = new QuestionBank();
        }
        if(listOfShuffledQuestion == null){
            listOfShuffledQuestion = new ArrayList<>();
            listOfShuffledQuestion.addAll((qb.getQuestionList(context)));
        }
        Collections.shuffle(listOfShuffledQuestion);
        return listOfShuffledQuestion;
    }
}
