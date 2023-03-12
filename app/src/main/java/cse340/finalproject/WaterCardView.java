package cse340.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**

 The WaterCardView class provides a custom card view to display water consumption data.
 It includes a date, amount, and method of consumption for a single water entry.
 */
public class WaterCardView extends CardView {
    private TextView dateTextView;
    private TextView amountTextView;
    private TextView methodTextView;
    /**
     Constructs a new WaterCardView object.
     @param context the context in which the view is created
     */
    public WaterCardView(Context context) {
        super(context);
        init();
    }
    /**
     Initializes the view by inflating the layout and getting references to the child views.
     */
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.water_card_view, this, true);
        dateTextView = findViewById(R.id.date_text);
        amountTextView = findViewById(R.id.amount_text);
        methodTextView = findViewById(R.id.method_text);
    }
    /**
     Sets the water entry data to display in the view.
     @param waterEntry the WaterEntry object containing the data to display
     */
    public void setWaterEntry(WaterEntry waterEntry) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault());
        dateTextView.setText(getResources().getString(R.string.date,
                dateFormat.format(waterEntry.getDate())));
        amountTextView.setText(getResources().getString(R.string.amount,
                waterEntry.getAmount()));
        methodTextView.setText(getResources().getString(R.string.mode,
                String.valueOf(waterEntry.getMethod())));
    }
}
