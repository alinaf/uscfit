package charstars.uscfit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Map<Activity, Date> mDataset;
    private Activity[] keyList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView category, duedate;
        public MyViewHolder(View v) {
            super(v);
       //     name = v.findViewById(R.id.name);
            category = v.findViewById(R.id.category);
            duedate = v.findViewById(R.id.duedate);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Map<Activity, Date> mDataset) {
        this.mDataset = mDataset;
        this.keyList = new Activity[mDataset.keySet().size()];
        this.keyList = mDataset.keySet().toArray(keyList);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_row, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Activity activity = keyList[position];
       // holder.name.setText(activity.getName());
        holder.category.setText(activity.getCategory());
        holder.duedate.setText(mDataset.get(activity).toString());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return keyList.length;
    }
}