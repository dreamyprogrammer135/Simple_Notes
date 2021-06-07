package com.dreamyprogrammer.simplenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements ListNotes.Controller, EditListNotes.Controller{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new ListNotes())
                .commit();
    }

    @Override
    public void saveNotes(TaskElement taskElement) {

    }

    @Override
    public void openNotes(TaskElement taskElement) {

        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(isLandscape ? R.id.detail_container : R.id.container, EditListNotes.newInstance(taskElement))
                .addToBackStack(null)
                .commit();

//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.container, EditListNotes.newInstance(taskElement))
//                .addToBackStack(null)
//                .commit();
    }
}