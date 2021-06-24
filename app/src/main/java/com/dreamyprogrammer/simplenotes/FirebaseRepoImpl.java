package com.dreamyprogrammer.simplenotes;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseRepoImpl implements TaskRepo {

    public static final String ID_KEY = "id";
    private static final String TASKS_TABLE_NAME = "tasks";
    private List<TaskElement> cache = new ArrayList<>();
    private List<Runnable> subscribers = new ArrayList<>();

    private FirebaseFirestore database;

    public FirebaseRepoImpl() {

        database = FirebaseFirestore.getInstance();
        database.collection(TASKS_TABLE_NAME).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                refillCache(queryDocumentSnapshots);
            }
        });
        database.collection(TASKS_TABLE_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                refillCache(value);
            }
        });
        notifySubscribers();
    }

    private void refillCache(@Nullable QuerySnapshot snapshot) {
        if (snapshot == null) return;
        cache = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            cache.add(document.toObject(TaskElement.class));
        }
        notifySubscribers();
    }


    @Override
    public List<TaskElement> getTasks() {
        return cache;
    }

    @Override
    public void updateTask(TaskElement task) {
        findNote(task, document -> {
            database.collection(TASKS_TABLE_NAME).document(document.getId()).delete();
            database.collection(TASKS_TABLE_NAME).add(task);
        });
    }

    @Override
    public void createTask(TaskElement task) {
        database.collection(TASKS_TABLE_NAME).add(task);
        notifySubscribers();
    }

    @Override
    public void deleteTask(TaskElement task) {
        findNote(task, document ->
                database.collection(TASKS_TABLE_NAME).document(document.getId()).delete()
        );
    }

    private void findNote(TaskElement task, OnNoteFoundListener noteFoundListener) {
        database.collection(TASKS_TABLE_NAME).whereEqualTo(ID_KEY, task.getId()).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        noteFoundListener.onNoteFound(document);
                    }
                });
    }

    private void notifySubscribers() {
        for (Runnable subscriber : subscribers) {
            subscriber.run();
        }
    }

    @Override
    public void subscribe(Runnable subscriber) {
        this.subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(Runnable subscriber) {
        this.subscribers.remove(subscriber);
    }

    private interface OnNoteFoundListener {
        void onNoteFound(DocumentSnapshot document);
    }


}
