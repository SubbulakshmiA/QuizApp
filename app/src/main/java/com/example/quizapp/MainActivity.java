package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,AlertEndOFQuizFragment.AlertBtnClicked{

    FrameLayout frameLayout;
    Button trueBtn, falseBtn;
    ProgressBar progressBar;
    int progress = 0;
    int index =0;
    int numOfQuestions;
    int correctAnswer ;
    int numOfAttempts;
    int totalNumOfCorrectAnswer = 0;
    int totalNumOfQuestionsAnswered ;

    List<Question> listOfShuffledQuestion;
    List<Integer> listOfShuffledColors;
    FileManager fileManager;
    String questionToFrag;
    int colorCodeToFrag;
    int selectedNumOfQuestions ;
    int configChange = 0;
    AlertEndOFQuizFragment alertFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        trueBtn = findViewById(R.id.true_btn);
        trueBtn.setOnClickListener(this);
        falseBtn = findViewById(R.id.false_btn);
        falseBtn.setOnClickListener(this);
        progressBar  = findViewById(R.id.progress_bar);
        progressBar.setProgress(progress);

        selectedNumOfQuestions = ((MyApp)getApplication()).selectedNumOfQuestions;

        if(selectedNumOfQuestions == 0 || selectedNumOfQuestions == 2){
            listOfShuffledQuestion = ((MyApp)getApplication()).getListOfShuffledQuestion(this);
            listOfShuffledColors = ((MyApp)getApplication()).getListOfShuffledColors(this);
        } else if (selectedNumOfQuestions == 1) {
            listOfShuffledQuestion =  (((MyApp)getApplication())
                    .getListOfShuffledQuestion(this)).subList(0,4);
            listOfShuffledColors =  (((MyApp)getApplication())
                    .getListOfShuffledColors(this)).subList(0,4);
        }

        index = ((MyApp)getApplication()).index;
        correctAnswer = ((MyApp)getApplication()).correctAnswer;
        fileManager = ((MyApp)getApplication()).fileManager;
        numOfAttempts = ((MyApp)getApplication()).numOfAttempts;
        totalNumOfCorrectAnswer = ((MyApp)getApplication()).totalNumOfCorrectAnswer;
        progress =  ((MyApp)getApplication()).progress;
        totalNumOfQuestionsAnswered = ((MyApp)getApplication()).totalNumOfQuestionsAnswered;


        numOfQuestions = listOfShuffledQuestion.size();
        progressBar.setMax(numOfQuestions * 10);
        progressBar.setProgress(progress);

        questionToFrag = listOfShuffledQuestion.get(index).question;
        colorCodeToFrag = listOfShuffledColors.get(index);
        fragment(questionToFrag,colorCodeToFrag);

//        fileManager.deleteAllToDos(this);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("orientation","orientation "+newConfig.orientation);

        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            configChange = 2;
            ((MyApp)getApplication()).listOfShuffledQuestion = (ArrayList<Question>) listOfShuffledQuestion;
        }else{
            configChange = 1;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();
        if(i == R.id.average ){
            String msg ="";
            String fromFileToDisplay ="";
            ArrayList<String> getResultFromFile = fileManager.getAllToDos(this);

            ArrayList<Integer> correctScoreFromFile = fileManager.extractScores(getResultFromFile);
                for(int s:correctScoreFromFile){
                    Log.d("correctScoreFromFile","correctScoreFromFile "+correctScoreFromFile);
                }
                fromFileToDisplay = "Your score is "+correctScoreFromFile.get(0)+"/"+correctScoreFromFile.get(1)+
                        " in "+correctScoreFromFile.get(2)+" attempts";
            if(!getResultFromFile.isEmpty()){
                msg = fromFileToDisplay;
            }else{
                msg = "No Result to show";
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Average");
            builder.setMessage(msg);
            builder.setPositiveButton("Ok",null);
            builder.create().show();
            Log.d("on menu clicked","average clicked ");
            return true;
        }else if(i == R.id.num_of_quest){
            CustomDialogFragment cf = new CustomDialogFragment(MainActivity.this);
            cf.showDialog(MainActivity.this);
            Log.d("on menu clicked","numof quest clicked ");
            return  true;
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete");
            builder.setMessage("Are you sure to delete results from file?");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    fileManager.deleteAllToDos(MainActivity.this);
                }
            });
            builder.setNegativeButton("cancel",null);
            builder.create().show();

            Log.d("on menu clicked","reset clicked ");
            return true;
        }

    }


    @Override
    public void onClick(View view) {

        Button btn = (Button) view;
            progress += 10;
        ((MyApp)getApplication()).progress = progress;
            progressBar.setProgress(progress);

        if(index < numOfQuestions ) {
        if(btn == trueBtn && listOfShuffledQuestion.get(index).answer){
            Toast.makeText(this,getResources().getString(R.string.correct),Toast.LENGTH_SHORT).show();
            correctAnswer += 1;
            ((MyApp)getApplication()).correctAnswer = correctAnswer;

        } else if (btn == falseBtn && !listOfShuffledQuestion.get(index).answer) {
            Toast.makeText(this, getResources().getString(R.string.correct),Toast.LENGTH_SHORT).show();
            correctAnswer += 1;
            ((MyApp)getApplication()).correctAnswer = correctAnswer;

        } else{
            Toast.makeText(this, getResources().getString(R.string.wrong_answer),Toast.LENGTH_SHORT).show();
        }

            index += 1;
            if(index < numOfQuestions){
                questionToFrag = listOfShuffledQuestion.get(index).question;
                colorCodeToFrag = listOfShuffledColors.get(index);
                fragment(questionToFrag,colorCodeToFrag);
                ((MyApp)getApplication()).index = index;
            }else{
                callEndOfQuizAlertFrag();
            }
        }
    }

    void callEndOfQuizAlertFrag(){

        AlertEndOFQuizFragment.newInstance(getString(R.string.your_score_is)+correctAnswer +getString(R.string.out_of)+numOfQuestions)
                .show(getSupportFragmentManager(),"Alert");
        AlertEndOFQuizFragment.listener = MainActivity.this;

        numOfAttempts += 1;
        totalNumOfQuestionsAnswered += numOfQuestions;
        ((MyApp)getApplication()).totalNumOfQuestionsAnswered = totalNumOfQuestionsAnswered;
        ((MyApp)getApplication()).totalNumOfCorrectAnswer += correctAnswer;
        ((MyApp)getApplication()).numOfAttempts = numOfAttempts;

        index = 0;
        ((MyApp)getApplication()).index = index;
        correctAnswer = 0;
        listOfShuffledQuestion = ((MyApp)getApplication()).getListOfShuffledQuestion(MainActivity.this);
        questionToFrag = listOfShuffledQuestion.get(index).question;
        colorCodeToFrag = listOfShuffledColors.get(index);
        progress = 0;
        progressBar.setProgress(progress);
    }

    void fragment(String questionToFrag,int colorCodeToFrag) {

        QuestionFragment questFrag1 = (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout);

        QuestionFragment questFrag =QuestionFragment.newInstance( questionToFrag,colorCodeToFrag);

        if(questFrag1 == null){
            getSupportFragmentManager().beginTransaction().add(R.id.frame_layout, questFrag).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, questFrag).commit();
        }

    }


    @Override
    public void alertBtnClicked() {
        Log.d("restart","restart new quiz");
        QuestionFragment qfrag =  (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        getSupportFragmentManager().beginTransaction().detach(qfrag).attach(qfrag).commit();
        fragment(questionToFrag,colorCodeToFrag);

    }
}