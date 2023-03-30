package com.benginio.flashcards_;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

public class FlashcardDatabase {

    private AppDatabase db;

    public FlashcardDatabase(Context context) {
        db = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class, "flashcard-database"
                )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public void initFirstCard() {
        if (db.flashcardDao().getAll().isEmpty()) {
            insertCard(new Flashcard("Who is the 44th President of the United States", "Barack Obama"));
        }
    }

    public List<Flashcard> getAllCards() {
        return db.flashcardDao().getAll();
    }

    public void insertCard(Flashcard flashcard) {
        db.flashcardDao().insertAll(flashcard);
    }

    public void deleteCard(String flashcardQuestion) {
        List<Flashcard> allCards = db.flashcardDao().getAll();
        for (Flashcard card : allCards) {
            if (card.getQuestion().equals(flashcardQuestion)) {
                db.flashcardDao().delete(card);
            }
        }
    }

    public void updateCard(Flashcard flashcard) {
        db.flashcardDao().update(flashcard);
    }

    public void deleteAll() {
        for (Flashcard flashcard : db.flashcardDao().getAll()) {
            db.flashcardDao().delete(flashcard);
        }
    }

}

