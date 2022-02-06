package com.example.notesapp.ui.list;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.domain.Note;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {

    private final ArrayList<Note> notesSource;
    private final Fragment fragment;
    private Context context;
    private int adapterPosition;

    public NotesListAdapter(ArrayList<Note> notesSource, Fragment fragment) {
        this.notesSource = notesSource;
        this.fragment = fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public NotesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.notes_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesListAdapter.ViewHolder holder, int position) {
        holder.bind(notesSource.get(position));
    }

    @Override
    public int getItemCount() {
        return notesSource.size();
    }

    public void editNote() {
        NotesListFragment.presenter.openNote(notesSource.get(adapterPosition), true);
    }

    public void openNote() {
        NotesListFragment.presenter.openNote(notesSource.get(adapterPosition), false);
    }

    public void deleteNote() {
        new AlertDialog.Builder(context)
                .setTitle("Want to delete the note?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    NotesListFragment.presenter.deleteNote(notesSource.get(adapterPosition));
                    notifyItemRemoved(adapterPosition);
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView itemNoteNameField;
        private final MaterialTextView itemNoteDateField;
        private final MaterialTextView itemNoteDescriptionField;

        @RequiresApi(api = Build.VERSION_CODES.N)
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemNoteNameField = itemView.findViewById(R.id.notes_list_item_name_field);
            itemNoteDateField = itemView.findViewById(R.id.notes_list_item_date_field);
            itemNoteDescriptionField = itemView.findViewById(R.id.notes_list_item_description_field);

            registerContextMenu(itemView);
            float contextMenuCoordinateY = itemView.getY();

            itemView.setOnClickListener(v -> {
                itemView.showContextMenu(itemView.getRight(), contextMenuCoordinateY + 16);
                adapterPosition = getAdapterPosition();
            });
        }

        private void registerContextMenu(@NonNull View itemView) {
            if (fragment != null) {
                fragment.registerForContextMenu(itemView);
            }
        }

        public void bind(Note note) {
            itemNoteNameField.setText(note.getNoteName());
            itemNoteDateField.setText(note.getDate());
            itemNoteDescriptionField.setText(note.getNoteDescription());
        }
    }
}
