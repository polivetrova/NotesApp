package com.example.notesapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.example.notesapp.R;

public class TasksFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatTextView text = view.findViewById(R.id.tasks_default_text);
        text.setOnClickListener(v -> new AlertDialog.Builder(requireContext())
                .setTitle("Sorry!")
                .setMessage("We are still working on this feature.")
                .setPositiveButton("Cool!", (dialog, which) -> dialog.dismiss())
                .show());
    }
}
