package com.dreamyprogrammer.simplenotes;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;



public class MainActivity extends AppCompatActivity implements ListNotes.Controller, EditListNotes.Controller {


    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragmentManager.beginTransaction()
                .add(R.id.container, new ListNotes())
                .commit();
    }

    @Override
    public void saveNotes(TaskElement taskElement) {
        //todo Будет нужно когда инфу буду прокидывать в обе стороны.
        // пока думаю что куда вообще будет прокидываться.
    }

    @Override
    public void openNotes(TaskElement taskElement) {
        fragmentManager.beginTransaction()
                .replace(R.id.container, EditListNotes.newInstance(taskElement))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new AboutTheApp())
                    .addToBackStack(null)
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }
}