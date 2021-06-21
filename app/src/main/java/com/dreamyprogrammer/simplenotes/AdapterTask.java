package com.dreamyprogrammer.simplenotes;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterTask extends RecyclerView.Adapter<AdapterTask.ViewHolder> {

    private List<TaskElement> taskElements = new ArrayList<>();
    private OnItemClickListener itemClickListener;

    // Передаем в конструктор источник данных
    // В нашем случае это массив, но может быть и запросом к БД
    public void setData(ArrayList<TaskElement> notes) {
        taskElements = notes;
        notifyDataSetChanged();
    }

    public AdapterTask(ArrayList<TaskElement> taskElements) {
        this.taskElements = taskElements;
    }

    // Создать новый элемент пользовательского интерфейса
    // Запускается менеджером
    @NonNull
    @Override
    public AdapterTask.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Создаем новый элемент пользовательского интерфейса
        // Через Inflater
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task_layout, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        if (itemClickListener != null) {
            vh.setOnClickListener(itemClickListener);
        }
        // Здесь можно установить всякие параметры
        return vh;
    }

    // Заменить данные в пользовательском интерфейсе
    // Вызывается менеджером
    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(@NonNull AdapterTask.ViewHolder viewHolder, int i) {
        // Получить элемент из источника данных (БД, интернет...)
        // Вынести на экран используя ViewHolder
        // Заполнение View вынести в отдельный метод
        viewHolder.textViewЕask.setText(taskElements.get(i).getName());
        viewHolder.textViewCount.setText("4/9");
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // Вернуть размер данных, вызывается менеджером
    @Override
    public int getItemCount() {
        return taskElements == null ? 0 : taskElements.size();
    }

    //Интерфейс для обработки нажатий
    public interface OnItemClickListener {
        void onItemClick(View view, int position, int typeClick);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView textViewЕask;
        private MaterialTextView textViewCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewЕask = itemView.findViewById(R.id.text_view_task);
            textViewCount = itemView.findViewById(R.id.text_view_count);
        }

        public void setOnClickListener(final OnItemClickListener listener) {
            textViewЕask.setOnClickListener((v) -> {
                int adapterPosition = getAdapterPosition();
                if (adapterPosition == RecyclerView.NO_POSITION) return;
                listener.onItemClick(v, adapterPosition, 1);
            });
        }
    }
}

