package charstars.uscfit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.MyViewHolder> {

    private List<Activity> activitiesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, defaultCalorieValue, category;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            defaultCalorieValue =  view.findViewById(R.id.defaultCalorieValue);
            category = view.findViewById(R.id.category);
        }
    }


    public ActivityAdapter(List<Activity> activityList) {
        this.activitiesList = activityList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_workout_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Activity activity = activitiesList.get(position);
        holder.name.setText(activity.getName());
        holder.defaultCalorieValue.setText(Integer.toString(activity.getDefaultCalorieValue()));
        holder.category.setText(activity.getCategory());
    }

    @Override
    public int getItemCount() {
        return activitiesList.size();
    }
}