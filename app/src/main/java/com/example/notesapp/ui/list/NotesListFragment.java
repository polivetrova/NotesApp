package com.example.notesapp.ui.list;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notesapp.R;
import com.example.notesapp.domain.ExistingNotesRepository;
import com.example.notesapp.domain.Note;
import com.example.notesapp.ui.MainActivity;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NotesListFragment extends Fragment implements NotesListView {

    public static NotesListPresenter presenter;
    public static final String KEY_NOTES_LIST = "KEY_NOTES_LIST";
    public static final String ARG_NOTES_LIST = "ARG_NOTES_LIST";
    public static final String FRAGMENT_TYPE = "NoteItemFragmentEditable or Uneditable";
    private NotesListAdapter adapter;
    private RecyclerView recyclerView;

    private SharedPreferences sharedPref = null;
    public static final String SHARED_PREF_KEY = "key";
    ArrayList<Note> data;

    public NotesListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NotesListPresenter(this, new ExistingNotesRepository());
        sharedPref = requireContext().getSharedPreferences("My Preferences", MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        initViews(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).showFloatingActionButton();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.note_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_note:
                adapter.editNote();
                return true;
            case R.id.action_open_note:
                adapter.openNote();
                return true;
            case R.id.action_delete_note:
                adapter.deleteNote();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.notes_root);
        data = presenter.requestNotes();
        checkSharedPref();
        initRecyclerView(data);
    }

    private void checkSharedPref() {
        String savedNotes = sharedPref.getString(SHARED_PREF_KEY, null);
        if (savedNotes != null) {
            try {
                Type type = new TypeToken<ArrayList<Note>>() {
                }.getType();
                data.clear();
                data.addAll(new GsonBuilder().create().fromJson(savedNotes, type));
            } catch (JsonSyntaxException e) {
                Toast.makeText(requireContext(), "JSONing failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initRecyclerView(ArrayList<Note> data) {
        recyclerView.setHasFixedSize(true);

        adapter = new NotesListAdapter(data, this);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);
    }

    public void createFragmentResultBundle(Note note, boolean isEditable) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTES_LIST, note);
        bundle.putBoolean(FRAGMENT_TYPE, isEditable);

        getParentFragmentManager()
                .setFragmentResult(KEY_NOTES_LIST, bundle);
    }

    @Override
    public void putToSharedPref(String jsonNotes) {
        sharedPref.edit().putString(SHARED_PREF_KEY, jsonNotes).apply();
    }
}