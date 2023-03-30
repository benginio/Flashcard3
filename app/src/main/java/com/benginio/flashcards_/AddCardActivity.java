package com.benginio.flashcards_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        findViewById(R.id.flashcard_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.flashcard_save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(((EditText)  findViewById(R.id.flashcard_question_edittext)).getText().toString())){
                    Toast.makeText(AddCardActivity.this, "Question field is null!", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(((EditText)  findViewById(R.id.flashcard_answer_edittext)).getText().toString())){
                    Toast.makeText(AddCardActivity.this, "Answer field is null!", Toast.LENGTH_SHORT).show();
                }else {
                    Intent data = new Intent();
                    String inputQuestion = ((EditText) findViewById(R.id.flashcard_question_edittext)).getText().toString();
                    String inputAnswer = ((EditText) findViewById(R.id.flashcard_answer_edittext)).getText().toString();
                    data.putExtra("QUESTION_KEY", inputQuestion);
                    data.putExtra("ANSWER_KEY", inputAnswer);

                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });

    }
}