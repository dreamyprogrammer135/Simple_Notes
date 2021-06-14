package com.dreamyprogrammer.simplenotes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class EditListNotes extends Fragment implements DatePickerFragment.DateReceiver {


    private static final String TASK_ELEMENT_ARGS_KEY = "TASK_ELEMENT_ARGS_KEY";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

    private TaskElement task;
    private EditText editTextNotes;
    private Button saveButton;
    private TextView textViewDate;
    private EditText editTitle;

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
        editTextNotes = view.findViewById(R.id.edit_text_notes);
        editTitle = view.findViewById(R.id.edit_title);
        saveButton = view.findViewById(R.id.save_button);

        editTitle.setText(task.getTitle());
        editTextNotes.setText(task.getNotes());

        saveButton.setOnClickListener(v -> {
  //          getContract().saveNotes(gatherNote());
            Controller controller = (Controller) getActivity();
            controller.saveNotes(new TaskElement(
                    editTextNotes.getText().toString(),
                    0
            ));
        });


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