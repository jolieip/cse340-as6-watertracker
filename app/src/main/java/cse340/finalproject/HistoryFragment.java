package cse340.finalproject;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import cse340.finalproject.*;
import cse340.finalproject.databinding.FragmentHistoryBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment  {

    private FragmentHistoryBinding binding;
    private List<WaterEntry> array = new ArrayList<>();
    private View listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Load data into list view

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared " +
                        "preferences",
                MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("entries", null);
        Type type = new TypeToken<ArrayList<WaterEntry>>() {}.getType();
        array = gson.fromJson(json, type);
        if (array == null) {
            array = new ArrayList<>();
        }


        Spinner spinner = (Spinner) root.findViewById(R.id.spinner);
        ListView cards = root.findViewById(R.id.historyview);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.date_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        WaterEntryAdapter listAdapter = new WaterEntryAdapter(array, getContext());
        cards.setAdapter(listAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)
            {
                //Called when item is selected, use position of item to find it from list of items
                String filter = parent.getItemAtPosition(pos).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
                //Called when no item is selected
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}