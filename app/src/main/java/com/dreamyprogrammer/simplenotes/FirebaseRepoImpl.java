package com.dreamyprogrammer.simplenotes;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    public static String collectionName;

    private FirebaseFirestore database;

    public FirebaseRepoImpl() {

//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        assert currentUser != null;
        collectionName = UserAuth.getUserData().toString();

        database = FirebaseFirestore.getInstance();
        database.collection(collectionName).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                refillCache(queryDocumentSnapshots);
            }
        });
        database.collection(collectionName).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
            database.collection(UserAuth.getUserData().toString()).document(document.getId()).delete();
            database.collection(UserAuth.getUserData().toString()).add(task);
        });
    }

    @Override
    public void createTask(TaskElement task) {
        database.collection(UserAuth.getUserData().toString()).add(task);
        notifySubscribers();
    }

    @Override
    public void deleteTask(TaskElement task) {
        findNote(task, document ->
                database.collection(UserAuth.getUserData().toString()).document(document.getId()).delete()
        );
    }

    private void findNote(TaskElement task, OnNoteFoundListener noteFoundListener) {
        database.collection(UserAuth.getUserData().toString()).whereEqualTo(ID_KEY, task.getId()).get()
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
