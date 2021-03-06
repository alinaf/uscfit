package charstars.uscfit;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import charstars.uscfit.DataHandlers.GoalCalculations;
import charstars.uscfit.DatabaseHandlers.StepsDatabaseManager;

import static java.lang.Character.isDigit;

public class StepsDisplay extends AppCompatActivity implements SensorEventListener {


    private static String email;
    private static List<Goal> defaultGoals;

    SensorManager sensorManager;

    TextView tv_steps;

    TextView tv_goalNum;

    boolean running = false;

    //this is for the bottom navigation (eventually able to set both weekly and daily steps goals)
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            BottomNavigationView navigation;
            switch (item.getItemId()) {
                case R.id.navigation_addGoal:
                    setContentView(R.layout.addstepsgoal);
                    clearAddGoalFields();

                    //navigation = findViewById(R.id.navigationAddGoal); idk what these do, may replace w/button anyway
                    //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                    return true;
            }
            return false;
        }
    };

    public void clearAddGoalFields(){//this is used for formatting the popup that appears when you try to add a goal
        //nothing here yet
    }


    public void onClick(View v) {
        defaultGoals = GoalCalculations.getGoals(email);
        System.out.println("clicked");
        if(v.getId() == R.id.goalRowLayout){
            Goal g = (Goal) v.getTag();
            /*
            if(g.getQuantifier().equals(Quantifier.DAYS.getMeasurement()) || g.getQuantifier().equals(Quantifier.STEPS.getMeasurement())){
                return;
            }*/
            Intent i = new Intent(StepsDisplay.this, EditPopUpInfo.class);
            i.putExtra("GOAL", g);
            startActivity(i);


        }
        if(v.getId() == R.id.addStepsGoal_button){
            EditText e = findViewById(R.id.numSteps_input);
            //Spinner spinner = (Spinner) findViewById(R.id.goalSpinner);
            //NumberPicker num = (NumberPicker)findViewById(R.id.numberPicker);

            String exerciseDescription = "walk";
            String numStepsString = e.getText().toString();
            //System.out.println("qianze: " + numStepsString);
            int numStepsInt = Integer.parseInt(numStepsString);
            //String goalType = spinner.getSelectedItem().toString(); perhaps use in future for daily or weekly goal
                    //next sprint: allow user to schedule steps for a specific week or day

            LayoutInflater inflater = getLayoutInflater();

            View layout;


            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);

            //if user does not put in a steps count
            if(exerciseDescription == null || numStepsInt == 0){
                layout = inflater.inflate(R.layout.goalfail,null);
                toast.setView(layout);
                toast.show();

                return;
            }

            boolean successful = addGoal("steps", numStepsInt, exerciseDescription);

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
    }




    public boolean addStepsGoal(String goalType, String goalNumString, String exerciseDescription){
        int goalNum = 0;
        if(goalNumString == null || goalNumString.equals("")){
            return false;
        }
        for (char ch : goalNumString.toCharArray()){
            if(!isDigit(ch)){
                return false;
            }
        }
        {
            return true;
        }
    }

    //ADD A FIELD FOR NEW DATE
    public boolean addGoal(String goalType, int goalNum, String exerciseDescription){
            Log.d("updating", exerciseDescription);
            return GoalCalculations.addGoal(new StepsGoal(new Date(), goalNum, 0), email);
    }

    public int getMostRecentStepGoal(){
        //call function in StepCalculations to get the most recent steps goal.

        return StepsDatabaseManager.getLatestStepGoal();
    }

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


        setContentView(R.layout.activity_steps_display);

        tv_steps = (TextView) findViewById(R.id.tv_steps);//you only need to do this because you're doing logic with this layout element

        tv_goalNum = (TextView) findViewById(R.id.tv_goalNum);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);//tbh idk what's goin on here


        GraphView graph = (GraphView) findViewById(R.id.graph);
        //get dates and information from database (probably put this in a function later)
        GregorianCalendar mon = new GregorianCalendar();
        GregorianCalendar tues = new GregorianCalendar();
        GregorianCalendar wed = new GregorianCalendar();
        GregorianCalendar thurs = new GregorianCalendar();
        GregorianCalendar fri = new GregorianCalendar();
        GregorianCalendar sat = new GregorianCalendar();
        GregorianCalendar sun = new GregorianCalendar();//really the last one will be today
        mon.set(2018, 9, 29);
        tues.set(2018, 9, 30);
        wed.set(2018, 9, 31);
        thurs.set(2018, 10, 1);
        fri.set(2018, 10, 2);
        sat.set(2018, 10, 3);
        sun.set(2018, 10, 4);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint((Date)mon.getTime(), 1000),
                new DataPoint((Date)tues.getTime(), 2000),
                new DataPoint((Date)wed.getTime(), 3000),
                new DataPoint((Date)thurs.getTime(), 4000),
                new DataPoint((Date)fri.getTime(), 5000),
                //new DataPoint((Date)sat.getTime(), 2800),
                //new DataPoint((Date)sun.getTime(), 6800)
        });
        graph.addSeries(series);

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(5); // only 4 because of the space

// set manual x bounds to have nice steps
        graph.getViewport().setMinX(mon.getTime().getTime());
        graph.getViewport().setMaxX(sun.getTime().getTime());
        //graph.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null){
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        }else{
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(running){
            tv_steps.setText(String.valueOf(event.values[0]));
            // TO DO: test on Android to see if this actually works
//            DayAtAGlance day = new DayAtAGlance();
//            day.setDailySteps(event.values[0]);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
