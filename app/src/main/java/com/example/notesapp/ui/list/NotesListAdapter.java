package com.example.notesapp.ui.list;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.domain.Note;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {

    private final List<Note> notesSource;
    private Context context;

    public NotesListAdapter(List<Note> notesSource) {
        this.notesSource = notesSource;
    }

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView itemNoteNameField;
        private final MaterialTextView itemNoteDateField;
        private final MaterialTextView itemNoteDescriptionField;
        private final AppCompatImageView openTheNoteButton;
        private final AppCompatImageView editTheNoteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemNoteNameField = itemView.findViewById(R.id.notes_list_item_name_field);
            itemNoteDateField = itemView.findViewById(R.id.notes_list_item_date_field);
            itemNoteDescriptionField = itemView.findViewById(R.id.notes_list_item_description_field);
            openTheNoteButton = itemView.findViewById(R.id.open_note_button);
            editTheNoteButton = itemView.findViewById(R.id.edit_note_button);

            itemView.setOnClickListener(v -> {
                openTheNoteButton.setVisibility(View.VISIBLE);
                editTheNoteButton.setVisibility(View.VISIBLE);

                openTheNoteButton.setOnClickListener(v1 -> NotesListFragment.presenter.openNote(notesSource.get(getAdapterPosition()), false));

                editTheNoteButton.setOnClickListener(v2 -> NotesListFragment.presenter.openNote(notesSource.get(getAdapterPosition()), true));

                new Handler().postDelayed(() -> {
                    openTheNoteButton.setVisibility(View.INVISIBLE);
                    editTheNoteButton.setVisibility(View.INVISIBLE);
                }, 5000);
            });

            itemView.setOnLongClickListener(v -> {
                new AlertDialog.Builder(context)
                        .setTitle("Want to delete the note?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            NotesListFragment.presenter.deleteNote(notesSource.get(getAdapterPosition()));
                            itemView.setVisibility(View.GONE);
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .show();
                return false;
            });
        }

        public void bind(Note note) {
            itemNoteNameField.setText(note.getNoteName());
            itemNoteDateField.setText(note.getDate());
            itemNoteDescriptionField.setText(note.getNoteDescription());
        }
    }
}
