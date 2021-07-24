package com.sur_pavel.holidaysermons;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.sur_pavel.holidaysermons.databinding.FragmentFirstBinding;

import org.jetbrains.annotations.NotNull;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String holiday = ((EditText) binding.textInputEditText).getText().toString();
                OpenSecondActivity(view.getContext(), holiday);
            }
        });
        ArrayAdapter<String> aa = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_list_item_1, Data.holidays);
        binding.listView.setAdapter(aa);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                String holiday = ((TextView) itemClicked).getText().toString();
                OpenSecondActivity(view.getContext(), holiday);
            }
        });
    }

    private void OpenSecondActivity(Context context, String holiday) {
        Toast.makeText(context, holiday,
                Toast.LENGTH_SHORT).show();
        Intent secActivity = new Intent(context, SecondActivity.class);
        secActivity.putExtra("item", holiday);
        startActivity(secActivity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}