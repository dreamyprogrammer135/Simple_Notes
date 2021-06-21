package com.dreamyprogrammer.simplenotes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class EditListNotes extends Fragment implements DatePickerFragment.DateReceiver {


    private static final String TASK_ELEMENT_ARGS_KEY = "TASK_ELEMENT_ARGS_KEY";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

    private TaskElement task;
    private EditText editTextNotes;
    private FloatingActionButton saveButton;
    private EditText editTitle;
    private TextView textViewDate;
    private ImageView imageViewBack;

    public static EditListNotes newInstance(TaskElement taskElement) {
        EditListNotes editListNotes = new EditListNotes();
        Bundle args = new Bundle();
        args.putParcelable(TASK_ELEMENT_ARGS_KEY, taskElement);
        editListNotes.setArguments(args);
        return editListNotes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_list_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView(view);
        fillTask(task);
    }

    private void findView(View view) {
        editTextNotes = view.findViewById(R.id.edit_text_notes);
        editTitle = view.findViewById(R.id.edit_title);
        saveButton = view.findViewById(R.id.save_button);
        imageViewBack = view.findViewById(R.id.image_view_back);

        imageViewBack.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });
        saveButton.setOnClickListener(v -> {
            Controller controller = (Controller) getActivity();
            if (task == null) {
                if ((!editTitle.getText().toString().equals("")) && (!editTextNotes.getText().toString().equals(""))) {
                    controller.saveNotes(new TaskElement(
                            editTitle.getText().toString(),
                            getNotesListFinal(task == null ? null : task.getNotes(), getNotesList(editTextNotes.getText().toString())),
                            0,
                            ""
                    ));
                } else controller.saveNotes(null);
            } else {
                task.setName(editTitle.getText().toString());
                task.setNotes(getNotesListFinal(task.getNotes(), getNotesList(editTextNotes.getText().toString())));
                controller.saveNotes(null);
            }
        });
    }


    public List<String> getNotesList(String notesStr) {
        List<String> list = new ArrayList<String>(Arrays.asList(notesStr.split("\n")));
        return list;
    }


    public List<Notes> getNotesListFinal(@Nullable List<Notes> notesOld, List<String> notesStr) {

        List<Notes> notesNew = new ArrayList();
        int index;

        for (int i = 0; i < notesStr.size(); i++) {
            index = -1;
            if(notesOld != null) {
                for (int j = 0; j < notesOld.size(); j++) {
                    if (notesStr.get(i).equals(notesOld.get(j).getNote())) {
                        index = j;
                    }
                }
            }
            if(index !=-1) {
                notesNew.add(new Notes(notesStr.get(i), notesOld.get(index).getCompleted()));
            } else {
                notesNew.add(new Notes(notesStr.get(i), false));
            }
        }
//
//        for (String noteStr : notesStr) {
//            index = notesOld == null ? -1 : notesOld. getIndexToString() ;
//            if (index == -1) {
//                notesNew.add(new Notes(noteStr, false));
//            } else {
//                notesNew.add(new Notes(noteStr, notesOld.get(index).getCompleted()));
//            }
//        }
        return notesNew;
    }


    private void fillTask(TaskElement task) {
        if (task == null) return;
        editTitle.setText(task.getTitle());
        editTextNotes.setText(task.getNotesStr());

    }

//    private TaskElement gatherNote() {
//        return new TaskElement(
//                task == null ? TaskElement.generateNewId() : task.id,
//                subjectEditText.getText().toString(),
//                task == null ? TaskElement.getCurrentDate() : task.creationDate,
//                textEditText.getText().toString(),
//                phoneEditText.getText().toString()
//        );
//}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof Controller)) {
            throw new RuntimeException(getString(R.string.error_implement_controller));
        }
        if (getArguments() != null) {
            task = getArguments().getParcelable(TASK_ELEMENT_ARGS_KEY);
        }
    }

    @Override
    public void setDate(String date) {
        this.textViewDate.setText(date);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment datePickerFragment = new DatePickerFragment(this);
        datePickerFragment.show(requireActivity().getSupportFragmentManager(), null);
    }

    private Controller getContract() {
        return (Controller) getActivity();
    }

    interface Controller {
        void saveNotes(TaskElement taskElement);
    }
}