package com.example.notesapp.ui.list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notesapp.R;
import com.example.notesapp.domain.ExistingNotesRepository;
import com.example.notesapp.domain.Note1;
import com.example.notesapp.ui.MainActivity;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {

    public static NotesListPresenter presenter;
    public static final String KEY_NOTES_LIST = "KEY_NOTES_LIST";
    public static final String ARG_NOTES_LIST = "ARG_NOTES_LIST";
    public static final String FRAGMENT_TYPE = "NoteItemFragmentEditable or Uneditable";

    public NotesListFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NotesListPresenter(this, new ExistingNotesRepository());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.notes_root);

        List<Note1> data = presenter.requestNotes();
        initRecyclerView(recyclerView, data);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).showFloatingActionButton();
    }

    private void initRecyclerView(RecyclerView recyclerView, List<Note1> data) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        NotesListAdapter adapter = new NotesListAdapter(data);
        recyclerView.setAdapter(adapter);
    }

    public void createFragmentResultBundle(Note1 note, boolean isEditable) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTES_LIST, note);
        bundle.putBoolean(FRAGMENT_TYPE, isEditable);

        getParentFragmentManager()
                .setFragmentResult(KEY_NOTES_LIST, bundle);
    }
}