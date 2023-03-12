package cse340.finalproject;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.SENSOR_SERVICE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import cse340.finalproject.ShakeDetector;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import cse340.finalproject.*;
import cse340.finalproject.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Date;

/**

 A fragment that displays a list of water entries and allows users to add new entries.
 The fragment also implements a shake detector using the device's accelerometer sensor
 to add a new water entry when the device is shaken.
 */
public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ArrayList<WaterEntry> listItems=new ArrayList<WaterEntry>();
    private WaterEntryAdapter adapter;
    private Button addButton;
    private SharedPreferences sharedPreferences;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    /**
     Inflates the fragment view, sets up the UI elements, and initializes the sensor listeners.
     @param inflater The LayoutInflater object that can be used to inflate any views in the fragment
     @param container The parent view that the fragment UI should be attached to
     @param savedInstanceState Saved data about the fragment state, if any
     @return The inflated view for the fragment
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // SharedPreferences
        sharedPreferences = getContext().getSharedPreferences("shared preferences",
                MODE_PRIVATE);
        loadData(sharedPreferences);

        // ListView
        adapter = new WaterEntryAdapter(listItems, getContext());
        ListView cardList = root.findViewById(R.id.listview);
        cardList.setAdapter(adapter);

        // Daily Goal
        TextView daily = root.findViewById(R.id.home_daily_text);
        daily.setText(getResources().getString(R.string.home_daily, adapter.getDailyTotal(),
                sharedPreferences.getString("autoSave", "")));

        // Add Button
        addButton = root.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAddWaterDialog();
            }
        });

        // Accelerometer and Shake Sensor
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                handleShakeEvent();
            }
        });
        return root;
    }
    /**
     Registers the shake detector when the fragment is resumed.
     */
    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }
    /**
     Unregisters the shake detector when the fragment is paused.
     */
    @Override
    public void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
    /**
     Adds a new water entry to the list when the device is shaken.
     */
    private void handleShakeEvent() {
        WaterEntry waterEntry = new WaterEntry(new Date(), 8, "auto");
        listItems.add(waterEntry);
        saveData();
    }
    /**
     Shows the add water dialog when the "add" button is clicked.
     */
    private void showAddWaterDialog() {
        // Create and show add water dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.log_water_form, null);
        builder.setView(view);

        final EditText amountEditText = view.findViewById(R.id.amount_edit_text);
        Button addButton = view.findViewById(R.id.add_button);
        Button cancelButton = view.findViewById(R.id.cancel_button);

        final AlertDialog dialog = builder.create();
        dialog.show();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add water entry to list
                int amount = Integer.parseInt(amountEditText.getText().toString());
                WaterEntry waterEntry = new WaterEntry(new Date(), amount, "Manual");
                listItems.add(waterEntry);
                saveData();
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    /**

     Loads the data from shared preferences and populates the list view with it.
     @param sharedPreferences the shared preferences object used to load data
     */
    private void loadData(SharedPreferences sharedPreferences) {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("entries", null);
        Type type = new TypeToken<ArrayList<WaterEntry>>() {}.getType();
        listItems = gson.fromJson(json, type);
        if (listItems == null) {
            listItems = new ArrayList<>();
        }
    }
    /**

     Saves the data from the list of water entries to shared preferences.
     */
    private void saveData() {
        adapter.notifyDataSetChanged();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared " +
                        "preferences",
                MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listItems);
        editor.putString("entries", json);
        editor.apply();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}