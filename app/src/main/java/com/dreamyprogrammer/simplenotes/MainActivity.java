package com.dreamyprogrammer.simplenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TaskElement taskElement = new TaskElement("111",1);
        Date date = taskElement.getTimeReminder();
        String id = taskElement.getId();
    }
}