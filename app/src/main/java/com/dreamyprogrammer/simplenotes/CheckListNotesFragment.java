package com.dreamyprogrammer.simplenotes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CheckListNotesFragment extends Fragment {

    private static final String CHECK_ELEMENT_ARGS_KEY = "CHECK_ELEMENT_ARGS_KEY";

    private TaskElement task;
    private List<String> checkElements = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private RecyclerView checkRecyclerView;
    private CheckListAdapter adapter;
    private Toolbar toolbar;
    private TextView editTitleCheck;
    private ImageView imageViewCheckEdit;
    private ImageView imageViewBackCheck;

    public static CheckListNotesFragment newInstance(TaskElement taskElement) {
        CheckListNotesFragment checkListNotesFragment = new CheckListNotesFragment();
        Bundle args = new Bundle();
        args.putParcelable(CHECK_ELEMENT_ARGS_KEY, taskElement);
        checkListNotesFragment.setArguments(args);
        return checkListNotesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chek_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView(view);
        setupRecyclerView();
    }

    private void findView(View view) {
        checkRecyclerView = view.findViewById(R.id.check_recycler_view);
        editTitleCheck = view.findViewById(R.id.edit_title_check);
        imageViewCheckEdit = view.findViewById(R.id.image_view_check_edit);
        imageViewBackCheck = view.findViewById(R.id.image_view_back_check);
    }

    private void setupRecyclerView() {

        TaskRepo repo;
        repo = new FirebaseRepoImpl();

        layoutManager = new LinearLayoutManager(getContext());
        checkRecyclerView.setLayoutManager(layoutManager);
        editTitleCheck.setText(task.getTitle());

        imageViewCheckEdit.setOnClickListener(v -> {
            ((Controller) getActivity()).editCheckNotes(task);
        });

        imageViewBackCheck.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        adapter = new CheckListAdapter(task.getNotes());
        checkRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((view, position) -> {
            if (task.getNotes().get(position).getCompleted() == false) {
                task.getNotes().get(position).setCompleted(true);
            } else {
                task.getNotes().get(position).setCompleted(false);
            }
            adapter.setData(task.getNotes());
            repo.updateTask(task);
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof EditListNotes.Controller)) {
            throw new RuntimeException(getString(R.string.error_implement_controller));
        }
        if (getArguments() != null) {
            task = getArguments().getParcelable(CHECK_ELEMENT_ARGS_KEY);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        ((AppCompatActivity) getActivity()).getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    interface Controller {
        void editCheckNotes(TaskElement taskElement);
    }
}