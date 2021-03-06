package charstars.uscfit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import charstars.uscfit.Adapters.WorkoutAdapter;
import charstars.uscfit.DataHandlers.UpdateWorkouts;
import charstars.uscfit.RootObjects.Workout;

public class WorkoutList extends AppCompatActivity implements View.OnClickListener {
    private static String email;
    private static List<Workout> workoutList = UpdateWorkouts.getWorkouts();


    CheckBox checkBox;

    TextView tv_label;

    private static RecyclerView mRecyclerView;
    private static WorkoutAdapter mAdapter;
    private static RecyclerView.LayoutManager mLayoutManager;

    private CalendarView mCalendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        workoutList = UpdateWorkouts.getWorkouts();





        setContentView(R.layout.activity_workout_list);
        tv_label = (TextView) findViewById(R.id.tv_workoutLabel);
        //createTable();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_workout_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WorkoutList.this, WorkoutPopUp.class);
                startActivity(i);
            }
        });

        mCalendarView = (CalendarView)findViewById(R.id.cal);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int yyyy, int mm, int dd){
                //get all relevant workout
                //add one to the month lol
                createTableForDate(calendarView, yyyy, mm, dd);
            }
        });

    }

    //for only displaying workouts for a certain date
    public void createTableForDate(CalendarView calendarView, int yyyy, int mm, int dd){
        //mRecyclerView = findViewById(R.id.workoutListLayout);
        List<Workout> workoutListForDate = new ArrayList<Workout>();
        //go through workout list and only choose the ones with a matching date
        System.out.println(yyyy + " " + mm + " " + dd);
        for(int i = 0; i < workoutList.size(); i++){
            Date currDate = workoutList.get(i).getDate();
            int year = currDate.getYear() + 1900;
            int month = currDate.getMonth(); //might need to add one to this, depending on mm
            int day = currDate.getDate();


            if(year == yyyy && mm == month && day == dd){
                workoutListForDate.add(workoutList.get(i));
            }
        }

        if(workoutListForDate.isEmpty()){
            tv_label.setText("There are no workouts for this date.");
        }else{
            tv_label.setText(" ");
        }



        Collections.sort(workoutListForDate);
        mAdapter = new WorkoutAdapter(workoutListForDate);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    public void createTable() {
        mRecyclerView = findViewById(R.id.workoutListLayout);
        Collections.sort(workoutList);
        mAdapter = new WorkoutAdapter(workoutList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    public void onResume() {
        super.onResume();
        createTable();
    }

    public static void onChangeData(List<Workout> workouts) {
        workoutList = workouts;
        //    System.out.println("qianze: " + workouts.get(0).getDescription());
        if (mRecyclerView != null) {
            ((WorkoutAdapter)mRecyclerView.getAdapter()).notifyDataSetChanged();
            Collections.sort(workoutList);
            mAdapter = new WorkoutAdapter(workoutList);
            mRecyclerView.setAdapter(mAdapter);
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.checkBox){
            Workout w = (Workout) v.getTag();
            Log.d("onclick", "getting in here");
            UpdateWorkouts.changeWorkoutCompletionStatus(w);

            onChangeData(workoutList);
        }
    }
}