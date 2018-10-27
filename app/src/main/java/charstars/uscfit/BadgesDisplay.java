//package charstars.uscfit;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//public class BadgesDisplay extends AppCompatActivity {
//
//    GridView androidGridView;
//
//    Integer[] imageIDs = {
//            R.drawable.trophy, R.drawable.trophy, R.drawable.trophy,
//            R.drawable.trophy, R.drawable.trophy, R.drawable.trophy,
//            R.drawable.trophy, R.drawable.trophy, R.drawable.trophy,
//            R.drawable.trophy, R.drawable.trophy, R.drawable.trophy
//
////            R.drawable.email, R.drawable.mobile, R.drawable.alram,
////            R.drawable.android, R.drawable.wordpress, R.drawable.web,
////            R.drawable.email, R.drawable.mobile, R.drawable.alram,
////            R.drawable.android, R.drawable.wordpress, R.drawable.web,
////            R.drawable.email, R.drawable.mobile, R.drawable.alram,
////            R.drawable.android, R.drawable.wordpress, R.drawable.web,
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_badges_display);
//
//        androidGridView = (GridView) findViewById(R.id.gridview_android_example);
//        androidGridView.setAdapter(new ImageAdapterGridView(this));
//
//        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent,
//                                    View v, int position, long id) {
//                Toast.makeText(getBaseContext(), "Grid Item " + (position + 1) + " Selected", Toast.LENGTH_LONG).show();
//            }
//        });
//
//    }
//
//    public class ImageAdapterGridView extends BaseAdapter {
//        private Context mContext;
//
//        public ImageAdapterGridView(Context c) {
//            mContext = c;
//        }
//
//        public int getCount() {
//            return imageIDs.length;
//        }
//
//        public Object getItem(int position) {
//            return null;
//        }
//
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ImageView mImageView;
//
//            if (convertView == null) {
//                mImageView = new ImageView(mContext);
//                mImageView.setLayoutParams(new GridView.LayoutParams(130, 130));
//                mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                mImageView.setPadding(16, 16, 16, 16);
//            } else {
//                mImageView = (ImageView) convertView;
//            }
//            mImageView.setImageResource(imageIDs[position]);
//            return mImageView;
//        }
//    }
//}


//package charstars.uscfit;
//
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//
//public class BadgesDisplay extends AppCompatActivity {
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_badges_display);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }
//
//}

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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import charstars.uscfit.DataHandlers.GoalCalculations;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class BadgesDisplay extends AppCompatActivity
{

    ListView show;
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
      // Log.d("HELLOOOO", "Hello again?");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badges_display);

        show = (ListView)findViewById(R.id.badges_list_view);

        ArrayList<String> goals = new ArrayList<String>(){{
            add("Ran 5 miles");
            add("Ran 10 miles");
        }};

//        ArrayAdapter<String> cheeseAdapter =
//                new ArrayAdapter<String>(this,
//                        R.layout.activity_badges_display,
//                        R.id.cheese_name,
//                        cheeses
//                );

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , goals);
        //RecyclerView adapter = new RecyclerView(this, R.layout.activity_badges_display, R.id.cheese_name, cheeses);
        //list.setAdapter(cheeseAdapter);
        show.setAdapter(adapter);

        //adapter.notifyDataSetChanged();
        //show.adapter = adapter;
//        ListView cheeseList = new ListView(this);
//        setContentView(cheeseList);
//        cheeseList.setAdapter(cheeseAdapter);

    }
}

//abstract public class BadgesDisplay extends AppCompatActivity implements View.OnClickListener{
//    private String email;
//    private List<Goal> defaultGoals = GoalCalculations.getGoals(email);
//    //private List<Badge> defaultBadges = BadgeCalculator.getBadges(email);
//
////    private RecyclerView mRecyclerView;
////    private RecyclerView.Adapter mAdapter;
////    private RecyclerView.LayoutManager mLayoutManager;
////
////    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
////            = new BottomNavigationView.OnNavigationItemSelectedListener() {
////
////        @Override
////        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
////
////            BottomNavigationView navigation;
////            switch (item.getItemId()) {
////                case R.id.navigation_goals:
////                    setContentView(R.layout.activity_goals_display);
////                    createTable();
////
////                    navigation = findViewById(R.id.navigation);
////                    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
////                    return true;
////
////                case R.id.navigation_badges:
////                    setContentView(R.layout.activity_badges_display);
////
//////                    navigation = findViewById(R.id.navigation);
//////                    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
////                    return true;
////
////                case R.id.navigation_addGoal:
////                    setContentView(R.layout.addgoal);
////                    clearAddGoalFields();
////
////                    navigation = findViewById(R.id.navigationAddGoal);
////                    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
////                    return true;
////            }
////            return false;
////        }
////    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//          super.onCreate(savedInstanceState);
////        Intent intent = getIntent();
////        if (savedInstanceState == null) {
////            Bundle extras = getIntent().getExtras();
////            if(extras == null) {
////                email = null;
////            } else {
////                email = extras.getString("EMAIL");
////            }
////        } else {
////            email = (String) savedInstanceState.getSerializable("EMAIL");
////        }
//
//        setContentView(R.layout.activity_badges_display);
//        //createTable();
//
////        GridView gridview = (GridView) findViewById(R.id.gridview);
////        gridview.setAdapter(new ImageAdapter(this));
////
////        gridview.setOnItemClickListener(new OnItemClickListener() {
////            public void onItemClick(AdapterView<?> parent, View v,
////                                    int position, long id) {
////                Toast.makeText(BadgesDisplay.this, "" + position,
////                        Toast.LENGTH_SHORT).show();
////            }
////        });
//
//        String[] cheeses = {
//                "Parmesan",
//                "Ricotta",
//                "Fontina",
//                "Mozzarella",
//                "Cheddar"
//        };
//
//        ArrayAdapter<String> cheeseAdapter =
//                new ArrayAdapter<String>(this,
//                        R.layout.activity_badges_display,
//                        R.id.cheese_name,
//                        cheeses
//                );
//
//        ListView cheeseList = new ListView(this);
//        setContentView(cheeseList);
//        cheeseList.setAdapter(cheeseAdapter);
//
////        BottomNavigationView navigation = findViewById(R.id.navigation);
////        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
////        GridView cheeseGrid = new GridView(this);
////        setContentView(cheeseGrid);
////        cheeseGrid.setNumColumns(2);
////        cheeseGrid.setColumnWidth(60);
////        cheeseGrid.setVerticalSpacing(20);
////        cheeseGrid.setHorizontalSpacing(20);
////        cheeseGrid.setAdapter(cheeseAdapter);
//
//
//    }
//
////    public void createTable(){
////
////        mRecyclerView = findViewById(R.id.goalsLayout);
////
////        mAdapter = new GoalAdapter(defaultGoals);
////        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
////        mRecyclerView.setLayoutManager(mLayoutManager);
////        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
////        mRecyclerView.setAdapter(mAdapter);
////
////    }
////
////    public boolean addGoal(String goalType, int goalNum, String exerciseDescription){
////
////        if(goalType.equals("Miles")){
////            return GoalCalculations.addGoal(new MilesGoal(exerciseDescription, goalNum, 0), email);
////
////        }else{
////            return GoalCalculations.addGoal(new MinutesGoal(exerciseDescription, goalNum, 0), email);
////
////        }
////        //assuming success;
////    }
////
//    public void clearAddGoalFields(){
//        EditText e = findViewById(R.id.exercise);
//        e.getText().clear();
//        e.setHint("ex. run, swim, bike");
//        Spinner spinner = (Spinner) findViewById(R.id.goalSpinner);
//        spinner.setSelection(0);
//        NumberPicker num = (NumberPicker)findViewById(R.id.numberPicker);
//        num.setMinValue(1);
//        num.setMaxValue(1000);
//        num.setValue(1);
//    }
////
////    public void onResume(){
////        super.onResume();
////        createTable();
////    }
////    @Override
////    public void onClick(View v) {
////        if(v.getId() == R.id.goalRowLayout){
////            Goal g = (Goal) v.getTag();
////            if(g.getQuantifier().equals(Quantifier.DAYS.getMeasurement()) || g.getQuantifier().equals(Quantifier.STEPS.getMeasurement())){
////                return;
////            }
////            Intent i = new Intent(BadgesDisplay.this, EditPopUpInfo.class);
////            i.putExtra("GOAL", g);
////            startActivity(i);
////
////
////        }
////        if(v.getId() == R.id.addGoal){
////            EditText e = findViewById(R.id.exercise);
////            Spinner spinner = (Spinner) findViewById(R.id.goalSpinner);
////            NumberPicker num = (NumberPicker)findViewById(R.id.numberPicker);
////
////            String exerciseDescription = e.getText().toString();
////            int goalNum = num.getValue();
////            String goalType = spinner.getSelectedItem().toString();
////
////
////
////            LayoutInflater inflater = getLayoutInflater();
////
////            View layout;
////
////
////            Toast toast = new Toast(getApplicationContext());
////            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
////            toast.setDuration(Toast.LENGTH_SHORT);
////
////
////            if(exerciseDescription.equals("") || exerciseDescription == null || goalNum == 0 || goalType == null || goalType.equals("")){
////                layout = inflater.inflate(R.layout.goalfail,null);
////                toast.setView(layout);
////                toast.show();
////
////                return;
////            }
////            boolean successful = addGoal(goalType, goalNum, exerciseDescription);
////
////            if(successful){
////                layout = inflater.inflate(R.layout.goalsuccess,null);
////                toast.setView(layout);
////                toast.show();
////
////                clearAddGoalFields();
////            }else{
////                layout = inflater.inflate(R.layout.goalerror,null);
////                toast.setView(layout);
////                toast.show();
////            }
////
////            return;
////        }
//
//
//
//
//
////        if(v.getId() == R.id.goback){
////            setContentView(R.layout.activity_goals_display);
////            createTable();
////            return;
////        }
////
////        Goal g = (Goal) v.getTag();
////        Log.d("goalsDisplay",g.getCategory());
////
////        setContentView(R.layout.goal_details);
////
////        TextView title = findViewById(R.id.title);
////        title.setText("GOAL: " + g.getCategory() + "-- " + g.getDescription());
////
////        mRecyclerView = findViewById(R.id.recycler_view);
////
////        // use this setting to improve performance if you know that changes
////        // in content do not change the layout size of the RecyclerView
////        mRecyclerView.setHasFixedSize(true);
////
////        // use a linear layout manager
////        mLayoutManager = new LinearLayoutManager(this);
////        mRecyclerView.setLayoutManager(mLayoutManager);
////
////        // specify an adapter (see also next example)
////        mAdapter = new MyAdapter(g.getDueDates());
////        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
////        mRecyclerView.setAdapter(mAdapter);
//
//    }



