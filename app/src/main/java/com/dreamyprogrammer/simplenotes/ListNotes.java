package com.dreamyprogrammer.simplenotes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ListNotes extends Fragment {

    private List<TaskElement> taskElements = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private TaskRepo repo;


    private Runnable subscriber = () -> {
        adapter.setData(repo.getTasks());
        adapter.notifyDataSetChanged();
        taskElements = repo.getTasks();
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView(view);
        setupRecyclerView();
    }

    @Override
    public void onDestroy() {
        repo.unsubscribe(subscriber);
        super.onDestroy();
    }

    private void findView(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        recyclerView = view.findViewById(R.id.recyclerView);
        bottomNavigationView = view.findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::navigate);
    }

    private boolean navigate(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_task: {
                ((Controller) getActivity()).createNotes();
            }
            break;
            case R.id.item_folder: {
                Toast.makeText(getContext(), getResources().getString(R.string.error_text), Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.item_sort: {
                Toast.makeText(getContext(), getResources().getString(R.string.error_text), Toast.LENGTH_SHORT).show();
            }
            break;
            default:
                return true;
        }
        return true;
    }


    private void setupRecyclerView() {

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        repo = new FirebaseRepoImpl();

        adapter = new TaskAdapter(repo.getTasks());
        adapter.setData(repo.getTasks());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((view, position) -> {
            ((Controller) getActivity()).editNotes(taskElements.get(position));
        });

        repo.subscribe(subscriber);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof Controller)) {
            throw new RuntimeException(getString(R.string.error_implement_controller));
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        ((AppCompatActivity) getActivity()).getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            ((Controller) getActivity()).openAboutFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addNote(TaskElement newTask) {
        repo.createTask(newTask);
        adapter.setData(repo.getTasks());
    }

    interface Controller {
        void createNotes();

        void editNotes(TaskElement taskElement);

        void openAboutFragment();
    }

}