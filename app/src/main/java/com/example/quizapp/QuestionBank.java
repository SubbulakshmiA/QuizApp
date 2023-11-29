package com.example.quizapp;

import android.content.Context;

import java.util.ArrayList;

public class QuestionBank {

    ArrayList<Integer> colorCodeList ;
    ArrayList<Question> questionList;

    public ArrayList<Question> getQuestionList(Context context) {

        Question q1 = new Question(context.getResources().getString(R.string.question1),true);
        Question q2 = new Question(context.getResources().getString(R.string.question2),true);
        Question q3 = new Question(context.getResources().getString(R.string.question3),true);
        Question q4 = new Question(context.getResources().getString(R.string.question4),false);
        Question q5 = new Question(context.getResources().getString(R.string.question5),false);
        Question q6 = new Question(context.getResources().getString(R.string.question6),true);
        Question q7 = new Question(context.getResources().getString(R.string.question7),true);
        Question q8 = new Question(context.getResources().getString(R.string.question7),false);
        if(questionList == null){
            questionList = new ArrayList<>();
            questionList.add(q1);
            questionList.add(q2);
            questionList.add(q3);
            questionList.add(q4);
            questionList.add(q5);
            questionList.add(q6);
            questionList.add(q7);
            questionList.add(q8);
        }
        return questionList;
    }

    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }

    public ArrayList<Integer> getColorCodeList(Context context) {
        if(colorCodeList == null){
            colorCodeList = new ArrayList<>();
            colorCodeList.add(context.getResources().getColor(R.color.Pink, context.getTheme()));
            colorCodeList.add(context.getResources().getColor(R.color.LightSkyBlue, context.getTheme()));
            colorCodeList.add(context.getResources().getColor(R.color.NavajoWhite, context.getTheme()));
            colorCodeList.add(context.getResources().getColor(R.color.LightPink, context.getTheme()));
            colorCodeList.add(context.getResources().getColor(R.color.LightGoldenrodYellow, context.getTheme()));
            colorCodeList.add(context.getResources().getColor(R.color.LightCyan, context.getTheme()));
            colorCodeList.add(context.getResources().getColor(R.color.PowderBlue, context.getTheme()));
            colorCodeList.add(context.getResources().getColor(R.color.LightGreen, context.getTheme()));

        }

        return colorCodeList;
    }


}
