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

    private static final String TASKS_TABLE_NAME = "tasks";
    private List<TaskElement> cache = new ArrayList<>();



    private FirebaseFirestore database;

    public FirebaseRepoImpl() {

        database = FirebaseFirestore.getInstance();
        database.collection(TASKS_TABLE_NAME).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshots) {
                if (snapshots == null) return;
                cache = new ArrayList<>();
                for (DocumentSnapshot document : snapshots.getDocuments()) {
                    cache.add(document.toObject(TaskElement.class));
                }
            }
        });
    }


    @Override
    public List<TaskElement> getTasks() {
        return cache;
    }

    @Override
    public void deleteTask(int id) {

    }

    @Override
    public void updateTask(TaskElement task) {

    }

    @Override
    public void createTask(TaskElement task) {
        database.collection(TASKS_TABLE_NAME).add(task);
    }


}
