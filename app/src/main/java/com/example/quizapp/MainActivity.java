package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    ArrayList<Question> listOfShuffledQuestion;
    ArrayList<Integer> listOfShuffledColors;
    FileManager fileManager;
    String questionToFrag;
    int colorCodeToFrag;
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

        listOfShuffledQuestion = ((MyApp)getApplication()).getListOfShuffledQuestion(this);
        listOfShuffledColors = ((MyApp)getApplication()).getListOfShuffledColors(this);
//        listOfShuffledQuestion = ((MyApp)getApplication()).getQb().getQuestionList(); // not working

        for(int i =0; i< listOfShuffledQuestion.size(); i++){
//            Log.d("listOfShuffledQuestion","listOfShuffledQuestion "+i +" --> "+listOfShuffledQuestion.get(i).question);
//            Log.d("listOfShuffledColors","listOfShuffledColors "+i +" --> "+listOfShuffledColors.get(i));
        }
        index = ((MyApp)getApplication()).index;
        correctAnswer = ((MyApp)getApplication()).correctAnswer;
        fileManager = ((MyApp)getApplication()).fileManager;
        numOfAttempts = ((MyApp)getApplication()).numOfAttempts;
        totalNumOfCorrectAnswer = ((MyApp)getApplication()).totalNumOfCorrectAnswer;
        progress =  ((MyApp)getApplication()).progress;


        numOfQuestions = listOfShuffledQuestion.size();
        progressBar.setMax(numOfQuestions * 10);
        progressBar.setProgress(progress);

        questionToFrag = listOfShuffledQuestion.get(index).question;
        colorCodeToFrag = listOfShuffledColors.get(index);
        fragment(questionToFrag,colorCodeToFrag);

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
            String getResultFromFile = fileManager.getAllToDos(this);
            if(getResultFromFile.isEmpty())
                getResultFromFile = "No results available";
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Average");
            builder.setMessage(getResultFromFile);
            builder.setPositiveButton("Ok",null);
            builder.create().show();
            Log.d("on menu clicked","average clicked ");
            return true;
        }else if(i == R.id.num_of_quest){
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
            Log.d("btn","btn clicked ");
            progress +=10;
        ((MyApp)getApplication()).progress = progress;
            progressBar.setProgress(progress);
//        Log.d("ques in main","ques "+listOfShuffledQuestion.get(index).question.toString());
        Log.d("index in frag","index inn main "+index);

        if(index < numOfQuestions ) {
        if(btn == trueBtn && listOfShuffledQuestion.get(index).answer){
            Toast.makeText(this,"Correct",Toast.LENGTH_SHORT).show();
            correctAnswer += 1;
            ((MyApp)getApplication()).correctAnswer = correctAnswer;
            Log.d("correctAnswer","correctAnswer "+correctAnswer);

        } else if (btn == falseBtn && !listOfShuffledQuestion.get(index).answer) {
            Toast.makeText(this,"Correct",Toast.LENGTH_SHORT).show();
            correctAnswer += 1;
            ((MyApp)getApplication()).correctAnswer = correctAnswer;
            Log.d("correctAnswer","correctAnswer "+correctAnswer);

        } else{
            Toast.makeText(this,"Wrong answer",Toast.LENGTH_SHORT).show();
        }

//            index += 1;
            if(index < numOfQuestions-1){
                index += 1;
                questionToFrag = listOfShuffledQuestion.get(index).question;
                colorCodeToFrag = listOfShuffledColors.get(index);
                fragment(questionToFrag,colorCodeToFrag);
                ((MyApp)getApplication()).index = index;
            }else{

                callEndOfQuizAlertFrag();
            }
        }
//        else{
//            callEndOfQuizAlertFrag();
//        }
//        Log.d("index in frag","index inn main after click  "+index);

    }
    void callEndOfQuizAlertFrag(){
        Log.d("end of quiz","end of quiz");

        AlertEndOFQuizFragment.newInstance("Your Score is : "+correctAnswer +" out of "+numOfQuestions)
                .show(getSupportFragmentManager(),"Alert");
        AlertEndOFQuizFragment.listener = MainActivity.this;

        numOfAttempts += 1;
        ((MyApp)getApplication()).totalNumOfCorrectAnswer += correctAnswer;
        ((MyApp)getApplication()).numOfAttempts = numOfAttempts;
        Log.d("end of quiz","totalNumOfCorrectAnswer "+((MyApp)getApplication()).totalNumOfCorrectAnswer);

        index = 0;
        ((MyApp)getApplication()).index = index;
        correctAnswer = 0;
        listOfShuffledQuestion = ((MyApp)getApplication()).getListOfShuffledQuestion(MainActivity.this);
        for(int i =0; i< listOfShuffledQuestion.size(); i++){
            Log.d("listOfShuffledQuestion","listOfShuffledQuestion "+i +" --> "+listOfShuffledQuestion.get(i).question);
        }
        questionToFrag = listOfShuffledQuestion.get(index).question;
        colorCodeToFrag = listOfShuffledColors.get(index);
        progress = 0;
        progressBar.setProgress(progress);
    }

    void fragment(String questionToFrag,int colorCodeToFrag) {

        QuestionFragment questFrag1 = (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout);

        QuestionFragment questFrag =QuestionFragment.newInstance( questionToFrag,colorCodeToFrag);
//        Log.d("index in frag","index inn main "+index);

        if(questFrag1 == null){
            getSupportFragmentManager().beginTransaction().add(R.id.frame_layout, questFrag).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, questFrag).commit();
        }

    }


    @Override
    public void alertBtnClicked() {
        Log.d("restart","restart new quiz");
        fragment(questionToFrag,colorCodeToFrag);

    }
}