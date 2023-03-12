package cse340.finalproject;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import cse340.finalproject.R;
import cse340.finalproject.databinding.FragmentSettingsBinding;

/**

 A fragment that displays and handles user settings.
 */
public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Retrieve the daily_goal EditText field and set its text to the autoSave preference value
        root.findViewById(R.id.settings);
        EditText field = root.findViewById(R.id.daily_goal);
        SharedPreferences prefs = getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
        field.setText(prefs.getString("autoSave", ""));
        // Add a TextWatcher to the daily_goal EditText field to update the autoSave preference value
        // when the text is changed
        field.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){

            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }

            @Override
            public void afterTextChanged(Editable s){
                prefs.edit().putString("autoSave", s.toString()).commit();
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