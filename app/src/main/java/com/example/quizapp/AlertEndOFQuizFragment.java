package com.example.quizapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class AlertEndOFQuizFragment extends DialogFragment {

    interface AlertBtnClicked{
        void alertBtnClicked();
    }
    static String msg = "";
    int numOfQuestions;
    int correctAnswer ;
    int numOfAttempts;
    int numOfAttemptsFromFile = 0;
    FileManager fileManager;
    String resultToFile;
    static AlertBtnClicked listener;
    int totalNumOfQuestionsAnswered ;
    int totalNumOfQuestionsAnsweredFromFile = 0;
    int totalNumOfCorrectAnswerFromFile = 0;
    public static AlertEndOFQuizFragment newInstance(String m){
        msg = m;
        return new AlertEndOFQuizFragment();
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        correctAnswer = ((MyApp)requireActivity().getApplication()).totalNumOfCorrectAnswer;
        fileManager = ((MyApp)requireActivity().getApplication()).fileManager;
        numOfQuestions = ((MyApp)requireActivity().getApplication()).getListOfShuffledQuestion(requireActivity()).size();
        numOfAttempts = ((MyApp)requireActivity().getApplication()).numOfAttempts;
        totalNumOfQuestionsAnswered  = ((MyApp)requireActivity().getApplication()).totalNumOfQuestionsAnswered;
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(R.string.result);
        builder.setMessage(msg);
        builder.setPositiveButton(getResources().getString(R.string.save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                ArrayList<String> getResultFromFile = fileManager.getAllToDos(requireContext());

                ArrayList<Integer> correctScoreFromFile = fileManager.extractScores(getResultFromFile);
                for(int s:correctScoreFromFile){
                    Log.d("correctScoreFromFile","correctScoreFromFile "+correctScoreFromFile);
                }
                correctAnswer = correctAnswer + correctScoreFromFile.get(0);
                totalNumOfQuestionsAnswered = totalNumOfQuestionsAnswered + correctScoreFromFile.get(1);
                numOfAttempts = numOfAttempts + correctScoreFromFile.get(2);

                resultToFile = getString(R.string.your_correct_answers_are)+"-" + correctAnswer +"-"+"/"+"-"+totalNumOfQuestionsAnswered+
                        "-"+ getString(R.string.in)+"-" + numOfAttempts +"-"+ getString(R.string.attempts);
                fileManager.writeResultToFile(requireActivity(), resultToFile );
                listener.alertBtnClicked();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.ignore), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.alertBtnClicked();
            }
        });
        return builder
                .create();
    }
}
