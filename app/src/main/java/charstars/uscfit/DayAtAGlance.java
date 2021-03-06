package charstars.uscfit;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class DayAtAGlance {
    private static int dayLastUpdated = -1; // hacky, fix later
    private static float dailySteps = 0;
    private static double dailyCalories = 0;
    private static int dailyMinutes = 0;
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public void setDayLastUpdated(int dayLastUpdated) {
        this.dayLastUpdated = dayLastUpdated;
    }

    public void setDailySteps(float dailySteps) {
        this.dailySteps = dailySteps;
    }

    public void setDailyCalories(double dailyCalories) {
        this.dailyCalories = dailyCalories;
    }

    public void setDailyMinutes(int dailyMinutes) {
        this.dailyMinutes = dailyMinutes;
    }

    public void updateDailySteps(int dailyStepsDiff) {
        dailySteps += dailyStepsDiff;
        if(dailySteps < 0) {
            dailySteps = 0;
        }
        updateDB();
    }

    public void updateDailyCalories(double dailyCaloriesDiff) {
        dailyCalories += dailyCaloriesDiff;
        if(dailyCalories < 0) {
            dailyCalories = 0;
        }
    }

    public void updateDailyMinutes(int dailyMinutesDiff) {
        dailyMinutes += dailyMinutesDiff;
        if(dailyMinutes < 0) {
            dailyMinutes = 0;
        }
    }

    public int getDayLastUpdated() {
        return dayLastUpdated;
    }

    public float getDailySteps() {
        return dailySteps;
    }

    public double getDailyCalories() {
        return dailyCalories;
    }

    public int getDailyMinutes() {
        return dailyMinutes;
    }

    public DayAtAGlance() {
    }

    public DayAtAGlance(boolean fromDb) {

        if (fromDb){
            return;
        }
//        if (firstName != "") {
//            return;
//        }

        readData(new FirebaseCallback() {
            @Override
            public void onCallback(DayAtAGlance dayAtAGlance) {
                Calendar cal = Calendar.getInstance();
                int dayofmonth = cal.get(Calendar.DAY_OF_MONTH);
                if (dayAtAGlance.getDayLastUpdated() != dayofmonth) {
                    // not updated today
                    setDayLastUpdated(dayofmonth);
                    setDailyCalories(0);
                    setDailyMinutes(0);
                    setDailySteps(0);
                    updateDB();
                }
            }
        });
    }

    public static void updateDB() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference("Users");
        DatabaseReference myRef1 = myRef.child(currentUser.getUid());
        DatabaseReference myRef2 = myRef1.child("DayStats");

        DayAtAGlance day = new DayAtAGlance();
        day.setDailyCalories(dailyCalories);
        day.setDailyMinutes(dailyMinutes);
        day.setDailySteps(dailySteps);
        day.setDayLastUpdated(dayLastUpdated);
        myRef2.setValue(day);
    }

    private void readData(final FirebaseCallback firebaseCallback) {
        // otherwise, need to query!
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference userListRef = database.getReference("Users"); // will not be null
        DatabaseReference currentUserRef = userListRef.child(currentUser.getUid());
        if (currentUserRef == null) { // will be null the first time
            return;
        }
        DatabaseReference userInfoRef = currentUserRef.child("DayStats");
        // Read from the database

        userInfoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                DayAtAGlance dayAtAGlance = dataSnapshot.getValue(DayAtAGlance.class);
                if (dayAtAGlance != null){
                    firebaseCallback.onCallback(dayAtAGlance);
                }
                else {
                    // first time using day at glance!
                    Calendar cal = Calendar.getInstance();
                    int dayofmonth = cal.get(Calendar.DAY_OF_MONTH);
                    dayLastUpdated = dayofmonth;
                    dailyCalories = 0;
                    dailyMinutes = 0;
                    dailySteps = 0;
                    updateDB();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });
    }

    private interface FirebaseCallback {
        void onCallback(DayAtAGlance dayAtAGlance);
    }
}
