package com.dreamyprogrammer.simplenotes;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import static com.dreamyprogrammer.simplenotes.R.*;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.ViewHolder> {

    private List<Notes> checkElements;
    private CheckListAdapter.OnItemClickListener itemClickListener;

    public CheckListAdapter(List<Notes> checks) {
        this.checkElements = checks;
    }

    // Передаем в конструктор источник данных
    // В нашем случае это массив, но может быть и запросом к БД
    public void setData(List<Notes> checks) {
        checkElements = checks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CheckListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Создаем новый элемент пользовательского интерфейса
        // Через Inflater
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(layout.item_task_chek_list_layout, viewGroup, false);
        CheckListAdapter.ViewHolder vh = new CheckListAdapter.ViewHolder(v);
        if (itemClickListener != null) {
            vh.setOnClickListener(itemClickListener);
        }

        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.textViewCheck.setText(checkElements.get(i).getNote());
        viewHolder.checkboxList.setChecked(checkElements.get(i).getCompleted());
        if (checkElements.get(i).getCompleted() == true) {
            viewHolder.textViewCheck.setPaintFlags(viewHolder.textViewCheck.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.listItemCheckLayout.setBackgroundColor(viewHolder.listItemCheckLayout.getContext().getResources().getColor(color.teal_200));

        } else {
            viewHolder.textViewCheck.setPaintFlags(viewHolder.textViewCheck.getPaintFlags()  & (~Paint.STRIKE_THRU_TEXT_FLAG));
            viewHolder.listItemCheckLayout.setBackgroundColor(viewHolder.listItemCheckLayout.getContext().getResources().getColor(color.color_item));
        }


    }

    @Override
    public int getItemCount() {
        return checkElements == null ? 0 : checkElements.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(CheckListAdapter.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView textViewCheck;
        private CardView listItemCheckLayout;
        private CheckBox checkboxList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCheck = itemView.findViewById(id.text_view_check);
            listItemCheckLayout = itemView.findViewById(id.list_item_check_layout);
            checkboxList = itemView.findViewById(id.checkbox_list);
        }

        public void setOnClickListener(final CheckListAdapter.OnItemClickListener listener) {
            listItemCheckLayout.setOnClickListener((v) -> {
                int adapterPosition = getAdapterPosition();
                if (adapterPosition == RecyclerView.NO_POSITION) return;
                listener.onItemClick(v, adapterPosition);
            });
        }
    }
}
