package com.dreamyprogrammer.simplenotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
public class ListNotes extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    public List<String> list;
    private AdapterTask adapter;

    public static ListNotes newInstance(String param1, String param2) {
        ListNotes fragment = new ListNotes();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

    private void findView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    private void setupRecyclerView() {

        ArrayList<TaskElement> taskElements = new ArrayList<>();

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        TaskElement taskElement1 = new TaskElement("Разобраться с фрагментами", 0);
        TaskElement taskElement2 = new TaskElement("Купить велосипед", 0);
        TaskElement taskElement3 = new TaskElement("Поиграть с детьми", 0);
        TaskElement taskElement4 = new TaskElement("Разобрать в шкафу", 0);
        TaskElement taskElement5 = new TaskElement("Сделать грядки", 0);
        taskElements.add(taskElement1);
        taskElements.add(taskElement2);
        taskElements.add(taskElement3);
        taskElements.add(taskElement4);
        taskElements.add(taskElement5);


        adapter = new AdapterTask(taskElements);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((view, position, typeClick) -> {
            Snackbar.make(view, "Откроется фрагмент для редактирования заметки", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });
    }
}