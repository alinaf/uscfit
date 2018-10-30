package charstars.uscfit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import charstars.uscfit.Adapters.GoalAdapter;
import charstars.uscfit.DataHandlers.GoalCalculations;
import charstars.uscfit.RootObjects.Quantifier;

public class GoalsDisplay extends AppCompatActivity implements View.OnClickListener{
    private static String email;
    private static List<Goal> defaultGoals = GoalCalculations.getGoals(email);

    private static RecyclerView mRecyclerView;
    private static RecyclerView.Adapter mAdapter;
    private static RecyclerView.LayoutManager mLayoutManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            BottomNavigationView navigation;
            switch (item.getItemId()) {
                case R.id.navigation_goals:
                    setContentView(R.layout.activity_goals_display);
                    createTable();

                    navigation = findViewById(R.id.navigationGoals);
                    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                    return true;

                case R.id.navigation_badges:
                    Log.d("nav to badges", "badge");
                    setContentView(R.layout.activity_badges_display);
                    Intent i = new Intent(GoalsDisplay.this, BadgesDisplay.class);
                    startActivity(i);
                    navigation = findViewById(R.id.navigation);
                    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                    return true;

                case R.id.navigation_addGoal:
                    Log.d("nav to add", "add goal");
                    setContentView(R.layout.addgoal);
                    clearAddGoalFields();

                    navigation = findViewById(R.id.navigationAddGoal);
                    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                    return true;
            }
            Log.d("didnt get nav???", "create");
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                email = null;
            } else {
                email = extras.getString("EMAIL");
            }
        } else {
            email = (String) savedInstanceState.getSerializable("EMAIL");
        }

        createTable();

    }

    public static void onChangeData(List<Goal> goals){
        defaultGoals = goals;
        ((GoalAdapter) mRecyclerView.getAdapter()).notifyDataSetChanged();
        mAdapter = new GoalAdapter(defaultGoals);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void createTable(){
        setContentView(R.layout.activity_goals_display);
        BottomNavigationView navigation = findViewById(R.id.navigationGoals);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        this.defaultGoals = GoalCalculations.getGoals(email);
        mRecyclerView = findViewById(R.id.goalsLayout);
        Log.d("inside table", "create");
        Log.d("inside table", defaultGoals.toString());
        mAdapter = new GoalAdapter(defaultGoals);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

    }

    public boolean addGoal(String goalType, int goalNum, String exerciseDescription){

        if(goalType.equals("Miles")){
           return GoalCalculations.addGoal(new MilesGoal(exerciseDescription, goalNum, 0), email);

        }else{
            Log.d("updating", exerciseDescription);
            return GoalCalculations.addGoal(new MinutesGoal(exerciseDescription, goalNum, 0), email);

        }
        //assuming success;
    }

    public void clearAddGoalFields(){
        EditText e = findViewById(R.id.exercise);
        e.getText().clear();
        e.setHint("ex. run, swim, bike");
        Spinner spinner = (Spinner) findViewById(R.id.goalSpinner);
        spinner.setSelection(0);
        NumberPicker num = (NumberPicker)findViewById(R.id.numberPicker);
        num.setMinValue(1);
        num.setMaxValue(1000);
        num.setValue(1);
    }

    public void onResume(){
        super.onResume();
        createTable();
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.goalRowLayout){
            Goal g = (Goal) v.getTag();
            if(g.getQuantifier().equals(Quantifier.DAYS.getMeasurement()) || g.getQuantifier().equals(Quantifier.STEPS.getMeasurement())){
                return;
            }
            Intent i = new Intent(GoalsDisplay.this, EditPopUpInfo.class);
            i.putExtra("GOAL", g);
            startActivity(i);


        }
        if(v.getId() == R.id.addGoal){
            EditText e = findViewById(R.id.exercise);
            Spinner spinner = (Spinner) findViewById(R.id.goalSpinner);
            NumberPicker num = (NumberPicker)findViewById(R.id.numberPicker);

            String exerciseDescription = e.getText().toString();
            int goalNum = num.getValue();
            String goalType = spinner.getSelectedItem().toString();



            LayoutInflater inflater = getLayoutInflater();

            View layout;


            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);


            if(exerciseDescription.equals("") || exerciseDescription == null || goalNum == 0 || goalType == null || goalType.equals("")){
                layout = inflater.inflate(R.layout.goalfail,null);
                toast.setView(layout);
                toast.show();

                return;
            }
            boolean successful = addGoal(goalType, goalNum, exerciseDescription);

            if(successful){
                layout = inflater.inflate(R.layout.goalsuccess,null);
                toast.setView(layout);
                toast.show();

                clearAddGoalFields();
            }else{
                layout = inflater.inflate(R.layout.goalerror,null);
                toast.setView(layout);
                toast.show();
            }

            return;
        }
//        if(v.getId() == R.id.goback){
//            setContentView(R.layout.activity_goals_display);
//            createTable();
//            return;
//        }
//
//        Goal g = (Goal) v.getTag();
//        Log.d("goalsDisplay",g.getCategory());
//
//        setContentView(R.layout.goal_details);
//
//        TextView title = findViewById(R.id.title);
//        title.setText("GOAL: " + g.getCategory() + "-- " + g.getDescription());
//
//        mRecyclerView = findViewById(R.id.recycler_view);
//
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        // specify an adapter (see also next example)
//        mAdapter = new MyAdapter(g.getDueDates());
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.setAdapter(mAdapter);

    }


}