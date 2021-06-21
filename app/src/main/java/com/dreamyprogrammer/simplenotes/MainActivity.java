package com.dreamyprogrammer.simplenotes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


public class MainActivity extends AppCompatActivity implements ListNotes.Controller, EditListNotes.Controller,
                                                                CheckListNotesFragment.Controller{


    private static final String TASK_LIST_FRAGMENT_TAG = "TASK_LIST_FRAGMENT_TAG";
    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragmentManager.beginTransaction()
                .add(R.id.container, new ListNotes(), TASK_LIST_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void saveNotes(TaskElement taskElement) {
        getSupportFragmentManager().popBackStack();
        ListNotes noteListFragment = (ListNotes) getSupportFragmentManager().findFragmentByTag(TASK_LIST_FRAGMENT_TAG);
        if (taskElement != null){
            noteListFragment.addNote(taskElement);
        }
    }

    @Override
    public void openAboutFragment() {
        fragmentManager.beginTransaction()
                .replace(R.id.container, new AboutTheApp())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void editNotes(TaskElement taskElement) {
        fragmentManager.beginTransaction()
                .replace(R.id.container, new CheckListNotesFragment().newInstance(taskElement))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void createNotes() {
        fragmentManager.beginTransaction()
                .replace(R.id.container, EditListNotes.newInstance(null))
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void editCheckNotes(TaskElement taskElement) {
        fragmentManager.beginTransaction()
                .replace(R.id.container, EditListNotes.newInstance(taskElement))
                .addToBackStack(null)
                .commit();
    }
}