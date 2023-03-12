package cse340.finalproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**

 Adapter class for displaying a list of WaterEntry objects using a ListView.
 */
public class WaterEntryAdapter extends BaseAdapter {
    List<WaterEntry> items;
    private Context context;
    /**
     Constructor for the WaterEntryAdapter class.
     @param items a List of WaterEntry objects to be displayed in the ListView
     @param context the context of the activity using the adapter
     */
    public WaterEntryAdapter(List<WaterEntry> items, Context context) {
        super();
        this.items = items;
        this.context = context;
    }
    /**
     Gets the number of items in the adapter.
     @return the number of WaterEntry objects in the adapter
     */
    @Override
    public int getCount() {
        return items.size();
    }
    /**
     Gets the WaterEntry object at the specified index.
     @param i the index of the item to retrieve
     @return the WaterEntry object at the specified index
     */
    @Override
    public Object getItem(int i) {
        return items.get(i);
    }
    /**
     Gets the unique ID for the item at the specified index.
     @param i the index of the item to retrieve the ID for
     @return the unique ID for the WaterEntry object at the specified index
     */
    @Override
    public long getItemId(int i) {
        return items.get(i).hashCode();
    }
    /**
     Calculates the total amount of water consumed in the current day.
     @return the total amount of water consumed in the current day
     */
    public double getDailyTotal() {
        double total = 0.0;
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String d1 = formatter.format(today);
        for (WaterEntry item: items) {
            String d2 = formatter.format(item.getDate());
            if (d1.compareTo(d2) == 0) {
                total += item.getAmount();
            }
        }
        return total;
    }
    /**
     Gets the View for the item at the specified index.
     @param i the index of the item to retrieve the View for
     @param view the recycled View to use, if available
     @param viewGroup the parent ViewGroup for the View
     @return the View for the WaterEntry object at the specified index
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        WaterCardView entry = new WaterCardView(context);
        entry.setWaterEntry(items.get(i));
        return entry;
    }
}
