package com.dreamyprogrammer.simplenotes;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<TaskElement> taskElements = new ArrayList<TaskElement>();
    private OnItemClickListener itemClickListener;

    public TaskAdapter(List<TaskElement> taskElements) {
        this.taskElements = taskElements;
    }

    // Передаем в конструктор источник данных
    // В нашем случае это массив, но может быть и запросом к БД
    public void setData(List<TaskElement> notes) {
        taskElements = notes;
//        notifyDataSetChanged();
    }

    // Создать новый элемент пользовательского интерфейса
    // Запускается менеджером
    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
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
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder viewHolder, int i) {
        // Получить элемент из источника данных (БД, интернет...)
        // Вынести на экран используя ViewHolder
        // Заполнение View вынести в отдельный метод
        viewHolder.textViewTask.setText(taskElements.get(i).getTitle());
        //todo пока заглушка, потом метод подсчитывает выполненные задачи и невыполненные
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
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView textViewTask;
        private MaterialTextView textViewCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTask = itemView.findViewById(R.id.text_view_task);
            textViewCount = itemView.findViewById(R.id.text_view_count);
        }

        public void setOnClickListener(final OnItemClickListener listener) {
            textViewTask.setOnClickListener((v) -> {
                int adapterPosition = getAdapterPosition();
                if (adapterPosition == RecyclerView.NO_POSITION) return;
                listener.onItemClick(v, adapterPosition);
            });
            textViewTask.setOnLongClickListener(this::initPopupMenu);
        }

        public boolean onPopupMenuClicked(MenuItem menuItem) {
            TaskRepo repo;
            repo = new FirebaseRepoImpl();

            if (menuItem.getItemId() == R.id.popup_menu_item_delete) {
                int adapterPosition = getAdapterPosition();
                if (adapterPosition == RecyclerView.NO_POSITION) return false;
                repo.deleteTask(taskElements.get(adapterPosition));
                taskElements.remove(adapterPosition);
                setData(repo.getTasks());
            } else {
                throw new RuntimeException("unknown popup menu item");
            }
            return true;
        }

        private boolean initPopupMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(itemView.getContext(), itemView);
            popupMenu.inflate(R.menu.popup_menu_list);
            popupMenu.setOnMenuItemClickListener(this::onPopupMenuClicked);
            popupMenu.show();
            return true;
        }
    }
}

