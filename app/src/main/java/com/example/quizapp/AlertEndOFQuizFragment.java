package com.example.quizapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class AlertEndOFQuizFragment extends DialogFragment {

    interface AlertBtnClicked{
        void alertBtnClicked();
    }
    static String msg = "";
    int numOfQuestions;
    int correctAnswer ;
    int numOfAttempts;
    FileManager fileManager;
    String resultToFile;
    static AlertBtnClicked listener;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Result");
        builder.setMessage(msg);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                resultToFile = "Your correct answers are " + correctAnswer +
                        " in " + numOfAttempts + " attempts \n";
                fileManager.writeResultToFile(requireActivity(), resultToFile );
                listener.alertBtnClicked();
            }
        });
        builder.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.alertBtnClicked();
            }
        });
        return builder
                .create();
    }
}
