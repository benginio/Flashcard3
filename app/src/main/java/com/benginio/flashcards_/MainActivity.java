package com.benginio.flashcards_;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tvFlashcardQuestion;
    private TextView tvFlashcardAnswer;
    private ImageView addQuestionImageView;
    private ImageView nextButton;
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;
    private final int REQUEST_CODE = 20;
    CountDownTimer countDownTimer;
    CountDownTimer defaulftTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvFlashcardAnswer = findViewById(R.id.flashcard_answer_textview);
        nextButton = findViewById(R.id.next_icon);
        TextView flashcardAnswer1 = findViewById(R.id.flashcardAnswer1);
         TextView flashcardAnswer2 = findViewById(R.id.flashcardAnswer2);
         TextView flashcardAnswer3 = findViewById(R.id.flashcardAnswer3);
        tvFlashcardQuestion = findViewById(R.id.flashcard_question_textview);
        if (allFlashcards != null && allFlashcards.size() > 0) {
            int lastQindex = allFlashcards.size() - 1;
            tvFlashcardQuestion.setText(allFlashcards.get(lastQindex).getQuestion());
            tvFlashcardAnswer.setText(allFlashcards.get(lastQindex).getAnswer());
            flashcardAnswer1.setText(allFlashcards.get(lastQindex).getWrongAnswer1());
            flashcardAnswer2.setText(allFlashcards.get(lastQindex).getAnswer());
            flashcardAnswer3.setText(allFlashcards.get(lastQindex).getWrongAnswer2());

        }

        tvFlashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvFlashcardQuestion.setCameraDistance(25000);
                tvFlashcardAnswer.setCameraDistance(25000);

                tvFlashcardQuestion.animate()
                        .rotationY(90)
                        .setDuration(200)
                        .withEndAction(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        tvFlashcardQuestion.setVisibility(view.INVISIBLE);
                                        tvFlashcardAnswer.setVisibility(view.VISIBLE);
                                        Log.i("Benginio", "entered onCLick method");

                                        Toast.makeText(MainActivity.this, "I CLICKED THE QUESTION!", Toast.LENGTH_SHORT).show();

                                        tvFlashcardAnswer.setRotationY(-90);
                                        tvFlashcardAnswer.animate()
                                                .rotationY(0)
                                                .setDuration(200)
                                                .start();
                                    }
                                }
                        ).start();


            }
        });

        tvFlashcardAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvFlashcardQuestion.setCameraDistance(25000);
                tvFlashcardAnswer.setCameraDistance(25000);

                tvFlashcardAnswer.animate()
                        .rotationY(-90)
                        .setDuration(200)
                        .withEndAction(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        tvFlashcardAnswer.setVisibility(view.INVISIBLE);
                                        tvFlashcardQuestion.setVisibility(view.VISIBLE);
                                        Log.i("Benginio", "entered onCLick method");

                                        Toast.makeText(MainActivity.this, "I CLICKED THE ANSWER!", Toast.LENGTH_SHORT).show();
                                        tvFlashcardQuestion.setRotationY(90);
                                        tvFlashcardQuestion.animate()
                                                .rotationY(0)
                                                .setDuration(200)
                                                .start();
                                    }
                                }
                        ).start();
            }
        });

        flashcardAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(getResources().getColor(R.color.incorrectAnswer));
                flashcardAnswer2.setBackgroundColor(getResources().getColor(R.color.correctAnswer));
                flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.incorrectAnswer));
            }
        });

        flashcardAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(getResources().getColor(R.color.correctAnswer));
                flashcardAnswer1.setBackgroundColor(getResources().getColor(R.color.incorrectAnswer));
                flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.incorrectAnswer));
                new ParticleSystem(MainActivity.this, 40, R.drawable.confetti3, 3000)
                        .setSpeedRange(0.2f, 0.5f)
                        .oneShot(findViewById(R.id.flashcardAnswer2), 100);
                new ParticleSystem(MainActivity.this, 40, R.drawable.confetti, 3000)
                        .setSpeedRange(0.2f, 0.5f)
                        .oneShot(findViewById(R.id.flashcardAnswer2), 100);
                new ParticleSystem(MainActivity.this, 40, R.drawable.confetti2, 3000)
                        .setSpeedRange(0.2f, 0.5f)
                        .oneShot(findViewById(R.id.flashcardAnswer2), 100);
            }
        });
        flashcardAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(getResources().getColor(R.color.incorrectAnswer));
                flashcardAnswer1.setBackgroundColor(getResources().getColor(R.color.incorrectAnswer));
                flashcardAnswer2.setBackgroundColor(getResources().getColor(R.color.correctAnswer));
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {//next card
            @Override
            public void onClick(View v) {
                currentCardDisplayedIndex++;

                final Animation leftOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_in);
                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
               if(allFlashcards==null || allFlashcards.size()==0){
                   return;
               }

                if (currentCardDisplayedIndex >= allFlashcards.size()) {
                    Snackbar.make(v,"you've reached  the end of the card", Snackbar.LENGTH_SHORT).show();
                    currentCardDisplayedIndex = 0;
                }
                Flashcard currentcard=allFlashcards.get(currentCardDisplayedIndex);
                tvFlashcardQuestion.setText(currentcard.getQuestion());
                tvFlashcardAnswer.setText(currentcard.getAnswer());

                findViewById(R.id.flashcard_question_textview).startAnimation(leftOutAnim);
                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                        currentCardDisplayedIndex++;
                        flashcardAnswer1.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                        flashcardAnswer2.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                        flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                        tvFlashcardQuestion.startAnimation(rightInAnim);
                        tvFlashcardQuestion.setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                        tvFlashcardAnswer.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                        flashcardAnswer1.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                        flashcardAnswer2.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                        flashcardAnswer3.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                        startTimer();
                        findViewById(R.id.flashcard_question_textview).startAnimation(rightInAnim);


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });
            }
        });

        addQuestionImageView = findViewById(R.id.flashcard_add_question_button);
        addQuestionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                startActivity(intent);
                startActivityForResult(intent, 100);
            }
        });
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();
        if (allFlashcards != null && allFlashcards.size() > 0) {//NOT EMPTY
            Flashcard firstcard = allFlashcards.get(0);
            tvFlashcardQuestion.setText(firstcard.getQuestion());
            tvFlashcardAnswer.setText(firstcard.getAnswer());
        }
    }
    private void startTimer() {
        countDownTimer.cancel();
        countDownTimer.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final TextView flashcardAnswer1 = findViewById(R.id.flashcardAnswer1);
        final TextView flashcardAnswer2 = findViewById(R.id.flashcardAnswer2);
        final TextView flashcardAnswer3 = findViewById(R.id.flashcardAnswer3);
        if (requestCode == 100) {
            //get data
            if (data != null) {//check if there's an Intent
                String questionString = data.getExtras().getString("QUESTION_KEY");
                String answerString = data.getExtras().getString("ANSWER_KEY");
                String wrongAnswer1 = data.getExtras().getString("wrongAnswer1");
                String wrongAnswer2 = data.getExtras().getString("wrongAnswer2");
                tvFlashcardQuestion.setText(questionString);
                tvFlashcardAnswer.setText(answerString);
                flashcardAnswer1.setText(wrongAnswer1);
                flashcardAnswer2.setText(answerString);
                flashcardAnswer3.setText(wrongAnswer2);

                flashcardAnswer1.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                flashcardAnswer2.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.answerOutline));

                Flashcard flashcard = new Flashcard(questionString, answerString);
                flashcardDatabase.insertCard(flashcard);
                allFlashcards = flashcardDatabase.getAllCards();
            }

//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
//
//            String Que = data.getStringExtra("QUESTION_KEY");
//            String Ans = data.getStringExtra("ANSWER_KEY");
//            tvFlashcardQuestion.setText(Que);
//            tvFlashcardAnswer.setText(Ans);
//
//
//            flashcardDatabase.insertCard(new Flashcard(Que,Ans));
//            allFlashcards = flashcardDatabase.getAllCards();
//        }


        }

    }
}