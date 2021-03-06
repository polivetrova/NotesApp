package com.example.notesapp.ui;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.notesapp.domain.Note;
import com.example.notesapp.ui.list.NotesListFragment;
import com.example.notesapp.ui.noteItem.NoteItemFragmentEditable;
import com.example.notesapp.R;
import com.example.notesapp.ui.noteItem.NoteItemFragmentUneditable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    FloatingActionButton addNoteButton;
    DrawerLayout drawer;
    FragmentManager manager = getSupportFragmentManager();
    public static String backstackKeyNotesList = "Notes list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        getSupportFragmentManager().setFragmentResultListener(NotesListFragment.KEY_NOTES_LIST, this, (requestKey, result) -> {
            if (result.getBoolean(NotesListFragment.FRAGMENT_TYPE)) {
                showEditableNoteItemFragment(result.getParcelable(NotesListFragment.ARG_NOTES_LIST));
            } else {
                showUneditableNoteItemFragment(result.getParcelable(NotesListFragment.ARG_NOTES_LIST));
            }
        });

        getSupportFragmentManager().setFragmentResultListener(NoteItemFragmentUneditable.KEY_NOTE_ITEM, this, ((requestKey, result) -> {
            showEditableNoteItemFragment(result.getParcelable(NoteItemFragmentUneditable.ARG_NOTE_2));
        }));

        addNoteButton = findViewById(R.id.add_note_button);
        addNoteButton.setOnClickListener(v -> showEditableNoteItemFragment(new Note("", "", "")));
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initDrawer();
    }

    private void initDrawer() {
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        MaterialTextView dateTextView = header.findViewById(R.id.drawer_date_display);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            switch (id) {
                case R.id.nav_notes_main:
                    addNoteButton.show();
                    manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    drawer.closeDrawers();
                    return true;
                case R.id.nav_about:
                    openItemsFromMenu(new AboutFragment());
                    return true;
                case R.id.nav_tasks:
                    openItemsFromMenu(new TasksFragment());
                    return true;
            }
            return false;
        });
        displayDate(dateTextView);
    }

    public static void displayDate(MaterialTextView dateField) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT);
        String date = dateFormat.format(calendar.getTime());
        dateField.setText(date);
    }

    private void openItemsFromMenu(Fragment fragment) {
        addNoteButton.setVisibility(View.GONE);
        manager.beginTransaction()
                .addToBackStack("")
                .replace(R.id.fragment_container, fragment)
                .commit();
        drawer.closeDrawers();
    }

    private void showUneditableNoteItemFragment(Note note) {
        NoteItemFragmentUneditable noteItemFragmentUneditable = NoteItemFragmentUneditable.newInstance(note);
        startTransaction(noteItemFragmentUneditable);
    }

    private void showEditableNoteItemFragment(Note note) {
        NoteItemFragmentEditable noteItemFragmentEditable = NoteItemFragmentEditable.newInstance(note);
        startTransaction(noteItemFragmentEditable);
    }

    private void startTransaction(Fragment fragment) {
        addNoteButton.hide();
        manager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(backstackKeyNotesList)
                .commit();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof TextInputEditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public void showFloatingActionButton() {
        addNoteButton.show();
    }
}