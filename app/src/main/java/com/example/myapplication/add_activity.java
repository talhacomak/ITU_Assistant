package com.example.myapplication;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.shawnlin.numberpicker.NumberPicker;
import com.skyhope.eventcalenderlibrary.ActivityDatabase;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class add_activity extends AppCompatActivity {
    Context c1 = this;
    AlarmManager alarmManager;
    com.shawnlin.numberpicker.NumberPicker dayList;
    TextView tw1, tw2, tw3, currentTime;
    private TimePicker alarm_time_picker, notify_time_picker;
    private DatePickerDialog datePickerDialog;
    private Button dateButton, stop_alarm_button, register_button;
    private CheckBox checkAlarmBox, checkNofityBox;
    private EditText activity, explanation;
    LinearLayout notificationLayout, alarmLayout;
    ActivityDatabase activityDB;

    boolean isAlarmOn, isNotifyOn;
    int activity_id, selection;

    Ringtone ringtone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        set_layouts();

        dayList = findViewById(R.id.day_list);
        initReminderLists();
        
        dateButton = findViewById(R.id.datePickerButton);
        String date = getTodaysDate();
        if (getIntent().getStringExtra("date") != null) date = getIntent().getStringExtra("date");
        dateButton.setText(date);

        initDatePicker();
        
        checkAlarmBox = findViewById(R.id.alarm);
        checkNofityBox = findViewById(R.id.reminder);
        alarmLayout = findViewById(R.id.alarmLayout);
        notificationLayout = findViewById(R.id.notificationLayout);
        alarm_time_picker = findViewById(R.id.alarm_time_picker);
        notify_time_picker = findViewById(R.id.notify_time_picker);
        currentTime = findViewById(R.id.currentTime);
        activity = findViewById(R.id.activity);
        explanation = findViewById(R.id.explanation);
        register_button = findViewById(R.id.register);
        stop_alarm_button = findViewById(R.id.stop_alarm_button);

        selection = 0;

        initAlarmBox();
        initNotifyBox();
        createNotifyChannel();

        activityDB = new ActivityDatabase(c1);
        //activityDB.deleteTable();
        activityDB.createTable();

        setViewsIfIntent();

        getAlarmIfExist();
    }

    private void setViewsIfIntent() {
        activity_id = getIntent().getIntExtra("id", -1);
        if(activity_id != -1){
            Cursor cursor = activityDB.getRowByID(activity_id);
            if(cursor.moveToFirst()){
                if(cursor.getString(1) != null)
                    activity.setText(cursor.getString(1));
                if(cursor.getString(6) != null)
                    explanation.setText(cursor.getString(6));
                if(cursor.getString(3) != null) {
                    alarmLayout.setVisibility(VISIBLE);
                    checkAlarmBox.setChecked(true);
                    Integer[] time = getIntFormatOfTime(cursor.getString(3));
                    alarm_time_picker.setMinute(time[1]);
                    alarm_time_picker.setHour(time[0]);
                }
                if(cursor.getString(4) != null){
                    notificationLayout.setVisibility(VISIBLE);
                    checkNofityBox.setChecked(true);
                    Integer[] time = getIntFormatOfTime(cursor.getString(4));
                    notify_time_picker.setMinute(time[1]);
                    notify_time_picker.setHour(time[0]);
                }
                if(cursor.getString(7) != null){
                    dateButton.setText(cursor.getString(7));
                }
                selection = cursor.getInt(5);
            }
        }
        else
            initTimePickers();
    }

    private Integer[] getIntFormatOfTime(String time_string) {
        String[] toSplit =  time_string.split(":");
        return new Integer[]{Integer.parseInt(toSplit[0]), Integer.parseInt(toSplit[1])};
    }

    private void getAlarmIfExist(){
        if(getIntent().getExtras() != null && getIntent().getExtras().containsKey("alarm")){
            stop_alarm_button.setVisibility(VISIBLE);
            register_button.setVisibility(GONE);
            startTheAlarm();
        }
    }

    private void startTheAlarm() {
        ringtone = RingtoneManager.getRingtone(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        ringtone.play();
    }

    public void stopTheAlarm(View view){
        ringtone.stop();
        stop_alarm_button.setVisibility(GONE);
        register_button.setVisibility(VISIBLE);
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = makeDateString(dayOfMonth, month+1, year);
                dateButton.setText(date);
            }
        };

        String date = dateButton.getText().toString();
        int[] dateNumbers = dateToNumber(date);
        int style = R.style.SpinnerDatePickerDialogTheme;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, dateNumbers[0], dateNumbers[1], dateNumbers[2]);
        // datePickerDialog.getDatePicker().setSpinnersShown(true);  // doesn't work
        // datePickerDialog.getDatePicker().setCalendarViewShown(false); // doesn't work
    }

    private String getTodaysDate(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month++;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    static public String makeDateString(int day, int month, int year){
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private void initTimePickers(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);
        alarm_time_picker.setHour(hour);
        alarm_time_picker.setMinute(min);
        notify_time_picker.setHour(hour);
        notify_time_picker.setMinute(min);
    }

    private String makeTimeString(int hourOfDay, int minute) {
        return hourOfDay + ":" + getMinuteFormat(minute);
    }

    public void openDatePicker(View view){
        datePickerDialog.show();
    }

    public void register(View view){
        String activity_name = null;
        if(activity.getText() != null)
            activity_name = activity.getText().toString();
        String exp = null;
        if(explanation.getText() != null)
            exp = explanation.getText().toString();
        String alarm_time = null;
        if(checkAlarmBox.isChecked())
            alarm_time = makeTimeString(alarm_time_picker.getHour(), alarm_time_picker.getMinute());
        String notify_time = null;
        if(checkNofityBox.isChecked())
            notify_time = makeTimeString(notify_time_picker.getHour(), notify_time_picker.getMinute());
        int day_before = selection;
        if(activity_id == -1)
            activity_id = activityDB.addData(activity_name, "manuel", alarm_time, notify_time, day_before, exp, dateButton.getText().toString());
        else{
            boolean res = activityDB.updateData(activity_id, activity_name, alarm_time, notify_time, day_before, exp, dateButton.getText().toString());
        }
        setAlarmIfOn();
        setNotificationIfOn();

        Intent in = new Intent(c1, activities.class);
        startActivity(in);
    }

    private String getMinuteFormat(int minute) {
        if(minute<10)
            return "0" + minute;
        else
            return String.valueOf(minute);
    }

    private void setAlarmIfOn() {
        if(isAlarmOn){
            Intent intent = new Intent(c1, AlarmIntent.class);
            intent.putExtra("id", activity_id);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(c1, 0, intent, 0);
            String alarmDate = dateButton.getText().toString();
            long alarmTimeInMilis = getMilisFromDate(alarmDate);
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTimeInMilis, pendingIntent);

            /*
            final int alarmTime = timePicker.getHour()*60 + timePicker.getMinute();
            final String alarmDate = dateButton.getText().toString();
            final Ringtone ringtone = RingtoneManager.getRingtone(c1, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new Timer Task() {
                @Override
                public void run() {
                    int current_time = getCurrentTime();
                    if(alarmDate.equals(getTodaysDate()) && alarmTime == current_time){
                        ringtone.play();
                    }
                    else ringtone.stop();
                }
            }, 0, 1000); */
        }
    }

    private long getMilisFromDate(String alarmDate) {
        int[] numberDate = dateToNumber(alarmDate);
        Calendar calendar = Calendar.getInstance();
        int hour = alarm_time_picker.getHour();
        int min = alarm_time_picker.getMinute();

        calendar.set(numberDate[0], numberDate[1], numberDate[2], hour, min);
        return calendar.getTimeInMillis();
    }


    private int[] dateToNumber(String date) {
        String[] toSplit = date.split(" ");
        int month = getIntFormatOfMonth(toSplit[0]);
        month--;
        int day = Integer.parseInt(toSplit[1]);
        int year = Integer.parseInt(toSplit[2]);
        return new int[]{year, month, day};
    }

    private int getCurrentTime() {
        String[] arrOfStr = currentTime.getText().toString().split(":");
        int current_time = Integer.parseInt(arrOfStr[0])*60;
        String[] arrOfStr2 = arrOfStr[1].split(" ");
        current_time += Integer.parseInt(arrOfStr2[0]);
        if(arrOfStr2[1].equals("PM")) current_time += 12*60;
        return current_time;
    }

    private void setNotificationIfOn() {
        if(isNotifyOn){
            Intent intent = new Intent(c1, AlarmIntent.class);
            intent.putExtra("id", activity_id);
            if(activity.getText() != null)
                intent.putExtra("activity_name", activity.getText().toString());
            if(explanation.getText() != null)
                intent.putExtra("explanation", explanation.getText().toString());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(c1, 0, intent, 0);
            String alarmDate = dateButton.getText().toString();
            long alarmTimeInMilis = getMilisFromDate(alarmDate);
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTimeInMilis, pendingIntent);
        }
    }

    private int getIntFormatOfMonth(String month){
        if(month.equals("JAN"))
            return 1;
        else if(month.equals("FEB"))
            return 2;
        else if(month.equals("MAR"))
            return 3;
        else if(month.equals("APR"))
            return 4;
        else if(month.equals("MAY"))
            return 5;
        else if(month.equals("JUN"))
            return 6;
        else if(month.equals("JUL"))
            return 7;
        else if(month.equals("AUG"))
            return 8;
        else if(month.equals("SEP"))
            return 9;
        else if(month.equals("OCT"))
            return 10;
        else if(month.equals("NOV"))
            return 11;
        else if(month.equals("DEC"))
            return 12;
        return 1;
    }

    static public String getMonthFormat(int month){
        if(month == 1)
            return "JAN";
        else if(month == 2)
            return "FEB";
        else if(month == 3)
            return "MAR";
        else if(month == 4)
            return "APR";
        else if(month == 5)
            return "MAY";
        else if(month == 6)
            return "JUN";
        else if(month == 7)
            return "JUL";
        else if(month == 8)
            return "AUG";
        else if(month == 9)
            return "SEP";
        else if(month == 10)
            return "OCT";
        else if(month == 11)
            return "NOV";
        else if(month == 12)
            return "DEC";
        return "JAN";
    }

    private void initNotifyBox() {
        checkNofityBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNofityBox.isChecked()){
                    showNotification();
                }
                else {
                    hideNotification();
                }
            }
        });
    }

    private void initAlarmBox() {
        checkAlarmBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAlarmBox.isChecked()){
                    isAlarmOn = true;
                    showAlarms();
                }
                else {
                    hideAlarms();
                    isAlarmOn = false;
                }
            }
        });
    }

    private void hideAlarms() {
        alarmLayout.setVisibility(GONE);
    }

    private void showAlarms() {
        alarmLayout.setVisibility(VISIBLE);
    }


    private void hideNotification() {
        notificationLayout.setVisibility(GONE);
    }


    private void showNotification() {
        notificationLayout.setVisibility(VISIBLE);
    }


    private void initReminderLists() {
        NumberStringLists numberStringLists = new NumberStringLists();
        dayList.setMinValue(0);
        dayList.setValue(selection);
        dayList.setMaxValue(NumberStringLists.dayNumbers.length-1);
        dayList.setDisplayedValues(NumberStringLists.dayNumbers);
        dayList.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                selection = newVal;
            }
        });

    }

    public void set_layouts(){
        tw1 = findViewById(R.id.textView1);
        tw2 = findViewById(R.id.textView2);
        tw3 = findViewById(R.id.textView3);
        tw3.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tw3.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int theWidth = tw3.getWidth();
                tw1.setWidth(theWidth);
                tw2.setWidth(theWidth);
            }
        });
    }

    public void createNotifyChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence Name = "LemubitReminderChannel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyLemubit", Name, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(c1, activities.class);
        startActivity(intent);
    }
}
