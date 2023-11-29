package com.example.quizapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomDialogFragment extends Dialog {

    public CustomDialogFragment(@NonNull Context context) {
        super(context);
    }

    void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       dialog.setContentView(R.layout.custom_dialog);
        Log.d("custom", "custom frag");

        RadioButton select1 = (RadioButton) dialog.findViewById(R.id.select1);
        RadioButton select2 = (RadioButton) dialog.findViewById(R.id.select2);

        select1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApp)getContext().getApplicationContext()).selectedNumOfQuestions = 1;
                activity.finish();
                activity.startActivity(activity.getIntent());
                dialog.dismiss();
            }
        });
        select2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApp)getContext().getApplicationContext()).selectedNumOfQuestions = 2;
                activity.finish();
                activity.startActivity(activity.getIntent());
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
