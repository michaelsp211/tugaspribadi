package com.learn.personal.androidmodulone;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.learn.personal.androidmodulone.models.Student;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    List<Student> students;
    private final OnItemDeleteClickListener deleteListener;
    private final OnItemEditClickListener editListener;

    public interface OnItemDeleteClickListener {
        void onItemClick(Student student);
    }

    public interface OnItemEditClickListener {
        void onItemClick(Student student);
    }

    public ListAdapter(List<Student> students, OnItemDeleteClickListener deleteListener, OnItemEditClickListener editListener) {
        this.students = students;
        this.deleteListener = deleteListener;
        this.editListener = editListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView listItemName;
        public final TextView listItemNik;
        public final ImageButton listItemButtonDelete;
        public final ImageButton listItemButtonEdit;

        public ViewHolder(View view) {
            super(view);

            listItemName = (TextView) view.findViewById(R.id.list_item_name);
            listItemNik = (TextView) view.findViewById(R.id.list_item_nik);
            listItemButtonDelete = (ImageButton) view.findViewById(R.id.list_item_delete);
            listItemButtonEdit = (ImageButton) view.findViewById(R.id.list_item_edit);
        }
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.listItemName.setText(students.get(position).getName());
        viewHolder.listItemNik.setText(students.get(position).getNik());
        viewHolder.listItemButtonDelete.setOnClickListener(view -> {
            deleteListener.onItemClick(students.get(position));
            students.remove(position);
            notifyDataSetChanged();
        });

        viewHolder.listItemButtonEdit.setOnClickListener(view -> editListener.onItemClick(students.get(position)));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }
}
