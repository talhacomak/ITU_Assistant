package com.skyhope.eventcalenderlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import static android.graphics.Color.RED;
import static android.view.Gravity.CENTER;

/*
 *  ****************************************************************************
 *  * Created by : Md Tariqul Islam on 11/29/2018 at 3:03 PM.
 *  * Email : tariqul@w3engineers.com
 *  *
 *  * Purpose:
 *  *
 *  * Last edited by : Md Tariqul Islam on 11/29/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

public class CalenderEvent extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "CalenderEvent";
    ActivityDatabase activityDB;

    private LinearLayout weekOneLayout;
    private LinearLayout weekTwoLayout;
    private LinearLayout weekThreeLayout;
    private LinearLayout weekFourLayout;
    private LinearLayout weekFiveLayout;
    private LinearLayout weekSixLayout;

    private static final String[] MONTH_NAMES = {"January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"};

    private LinearLayout[] weeks;
    private TextView[] days;
    private LinearLayout[] daysContainer;
    private TextView[] eventsTextViewList;

    private static final String TEXT_EVENT = " TEXT";
    private static final String COLOR_EVENT = " COLOR";

    private Button buttonPrevious, buttonNext;

    private TextView textViewMonthName;

    private Calendar mCalendar;

    private static final String CUSTOM_GREY = "#a0a0a0";
    private Context c1;
    private String todays_date;

    //selected color element
    private TextView mSelectedTexView;
    private LinearLayout mSelectedLinearLayout;
    private int mPreviousColor;

    // View component
    private View mainView;

    // View setter component;
    private int mBackgroundColor, mSelectorColor, mSelectedDayTextColor, mCurrentMonthDayColor;
    private int mOffMonthDayColor, mMonthTextColor, mWeekNameColor;

    private Drawable nextButtonDrawable, prevButtonDrawable;

    // Default component
    private static final int DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    private static final int DEFAULT_SELECTED_DAY_COLOR = Color.WHITE;
    private static final int DEFAULT_CURRENT_MONTH_DAY_COLOR = Color.BLACK;
    private static final int DEFAULT_OFF_MONTH_DAY_COLOR = Color.parseColor(CUSTOM_GREY);
    private static final int DEFAULT_SELECTOR_COLOR = Color.parseColor("#C2CA83");
    private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#808080");


    public CalenderEvent(Context context) {
        super(context);
    }

    public CalenderEvent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initView(context);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        weekOneLayout = findViewById(R.id.calendar_week_1);
        weekTwoLayout = findViewById(R.id.calendar_week_2);
        weekThreeLayout = findViewById(R.id.calendar_week_3);
        weekFourLayout = findViewById(R.id.calendar_week_4);
        weekFiveLayout = findViewById(R.id.calendar_week_5);
        weekSixLayout = findViewById(R.id.calendar_week_6);

        buttonNext = findViewById(R.id.button_next);
        buttonPrevious = findViewById(R.id.button_previous);

        textViewMonthName = findViewById(R.id.text_view_month_name);

        // week container
        LinearLayout linearLayoutWeak = findViewById(R.id.linear_layout_week);

        weeks = new LinearLayout[6];
        days = new TextView[6 * 7];
        eventsTextViewList = new TextView[6 * 7];
        daysContainer = new LinearLayout[6 * 7];

        weeks[0] = weekOneLayout;
        weeks[1] = weekTwoLayout;
        weeks[2] = weekThreeLayout;
        weeks[3] = weekFourLayout;
        weeks[4] = weekFiveLayout;
        weeks[5] = weekSixLayout;

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        mCalendar = Calendar.getInstance();
        todays_date = getMonthFormat(mCalendar.get(Calendar.MONTH) + 1) + " " +  mCalendar.get(Calendar.DAY_OF_MONTH)  + " " + mCalendar.get(Calendar.YEAR);

        activityDB = new ActivityDatabase(getContext());

        Log.d(TAG, "Today, " + todays_date);

        initDaysInCalender(getdaysLayoutParams(), c1, metrics);

        initCalender(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH));

        buttonPrevious.setOnClickListener(this);
        buttonNext.setOnClickListener(this);

        // set view element
        mainView.setBackgroundColor(mBackgroundColor);

        textViewMonthName.setTextColor(mMonthTextColor);

        for (int i = 0; i < linearLayoutWeak.getChildCount(); i++) {
            TextView textViewWeek = (TextView) linearLayoutWeak.getChildAt(i);
            textViewWeek.setTextColor(mWeekNameColor);
        }

        if (nextButtonDrawable != null) {
            buttonNext.setBackground(nextButtonDrawable);
        }
        if (prevButtonDrawable != null) {
            buttonPrevious.setBackground(prevButtonDrawable);
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_next) {
            gotoNextMonth();
        } else if (view.getId() == R.id.button_previous) {
            gotoPreviousMonth();
        }
    }


    private void initCalender(int selectedYear, int selectedMonth) {
        mCalendar.set(selectedYear, selectedMonth, 1);
        String month_year_view = MONTH_NAMES[selectedMonth] + " " + selectedYear;
        textViewMonthName.setText(month_year_view);

        int daysInCurrentMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int index = 0;
        int firstDayOfCurrentMonth = mCalendar.get(Calendar.DAY_OF_WEEK);
        int previousLeftMonthDays = firstDayOfCurrentMonth - 1;
        int nextMonthDays = 42 - (daysInCurrentMonth + previousLeftMonthDays);

        // now set previous date

        if (previousLeftMonthDays != 0) {
            int prevMonth;
            int prevYear;
            if (selectedMonth > 0) {
                mCalendar.set(selectedYear, selectedMonth - 1, 1);
                prevMonth = selectedMonth - 1;
                prevYear = selectedYear;
            } else {
                mCalendar.set(selectedYear - 1, 11, 1);
                prevMonth = 11;
                prevYear = selectedYear - 1;
            }

            int previousMonthTotalDays = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            mCalendar.set(prevYear, prevMonth, (previousMonthTotalDays - previousLeftMonthDays));
            int prevCurrentDay = mCalendar.get(Calendar.DATE);
            prevCurrentDay++;
            for (int i = 0; i < previousLeftMonthDays; i++) {
                days[index].setText(String.valueOf(prevCurrentDay));
                days[index].setTextColor(mOffMonthDayColor);
                String date = getMonthFormat(prevMonth + 1) + " " + prevCurrentDay + " " + prevYear;

                Cursor events = activityDB.getRowsByDate(date);

                if (events.moveToFirst()) {
                    eventsTextViewList[index].setText("E");
                    eventsTextViewList[index].setVisibility(VISIBLE);
                    eventsTextViewList[index].setTextColor(RED);
                } else {
                    eventsTextViewList[index].setVisibility(INVISIBLE);
                }

                final int finalIndex = index;
                days[index].setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (mSelectedTexView != null) {
                            mSelectedLinearLayout.setBackgroundResource(android.R.color.transparent);
                            mSelectedTexView.setTextColor(mPreviousColor);
                        }
                        mPreviousColor = mOffMonthDayColor;
                        mSelectedTexView = days[finalIndex];
                        daysContainer[finalIndex].setBackgroundResource(R.drawable.draw_selector);

                        Drawable backgroundDrawable = DrawableCompat.wrap(daysContainer[finalIndex].getBackground()).mutate();
                        DrawableCompat.setTint(backgroundDrawable, mSelectorColor);

                        mSelectedLinearLayout = daysContainer[finalIndex];
                        days[finalIndex].setTextColor(mSelectedDayTextColor);

                    }
                });

                prevCurrentDay++;
                index++;
            }
        }


        // now set current month date

        mCalendar.set(selectedYear, selectedMonth, 1);

        for (int i = 1; i <= daysInCurrentMonth; i++) {
            days[index].setText(String.valueOf(i));
            days[index].setTextColor(mCurrentMonthDayColor);
            days[index].setGravity(CENTER);

            String date = getMonthFormat(selectedMonth + 1) + " " + i + " " + selectedYear;

            Cursor events = activityDB.getRowsByDate(date);

            if (events.moveToFirst()) {
                days[index].setTextColor(RED);
                days[index].setBackground(ContextCompat.getDrawable(c1, R.drawable.borders));
                eventsTextViewList[index].setText("E");
                eventsTextViewList[index].setVisibility(VISIBLE);
                eventsTextViewList[index].setTextColor(RED);
            } else {
                eventsTextViewList[index].setVisibility(INVISIBLE);
            }

            if (todays_date.equals(date)) {
                if (mSelectedTexView != null) {
                    mSelectedLinearLayout.setBackgroundResource(android.R.color.transparent);
                    mSelectedTexView.setTextColor(mPreviousColor);
                }
                mPreviousColor = mCurrentMonthDayColor;
                mSelectedTexView = days[index];
                daysContainer[index].setBackgroundResource(R.drawable.draw_selector);

                Drawable backgroundDrawable = DrawableCompat.wrap(daysContainer[index].getBackground()).mutate();
                DrawableCompat.setTint(backgroundDrawable, mSelectorColor);

                mSelectedLinearLayout = daysContainer[index];
                days[index].setTextColor(mSelectedDayTextColor);
            }


            final int finalIndex = index;
            days[index].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "Hello");

                    if (mSelectedTexView != null) {
                        mSelectedLinearLayout.setBackgroundResource(android.R.color.transparent);
                        mSelectedTexView.setTextColor(mPreviousColor);
                    }
                    mPreviousColor = mCurrentMonthDayColor;
                    mSelectedTexView = days[finalIndex];
                    daysContainer[finalIndex].setBackgroundResource(R.drawable.draw_selector);

                    Drawable backgroundDrawable = DrawableCompat.wrap(daysContainer[finalIndex].getBackground()).mutate();
                    DrawableCompat.setTint(backgroundDrawable, mSelectorColor);

                    mSelectedLinearLayout = daysContainer[finalIndex];
                    days[finalIndex].setTextColor(mSelectedDayTextColor);

                }
            });

            index++;
        }

        int nextYear;
        int nextMonth;
        if (selectedMonth < 11) {
            nextYear = selectedYear;
            nextMonth = selectedMonth + 1;
        } else {
            nextYear = selectedYear + 1;
            nextMonth = 0;
        }

        // set rest of days
        for (int i = 1; i <= nextMonthDays; i++) {
            days[index].setText(String.valueOf(i));
            days[index].setTextColor(mOffMonthDayColor);

            String date = getMonthFormat(nextMonth + 1) + " " + i + " " + nextYear;

            Cursor events = activityDB.getRowsByDate(date);

            if (events.moveToFirst()) {
                eventsTextViewList[index].setText("E");
                eventsTextViewList[index].setVisibility(VISIBLE);
                eventsTextViewList[index].setTextColor(RED);
            } else {
                eventsTextViewList[index].setVisibility(INVISIBLE);
            }


            final int finalIndex = index;
            days[index].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "Hello");
                    if (mSelectedTexView != null) {
                        mSelectedTexView.setTextColor(mPreviousColor);
                        mSelectedLinearLayout.setBackgroundResource(android.R.color.transparent);
                    }
                    mPreviousColor = mOffMonthDayColor;
                    mSelectedTexView = days[finalIndex];
                    mSelectedLinearLayout = daysContainer[finalIndex];
                    daysContainer[finalIndex].setBackgroundResource(R.drawable.draw_selector);

                    Drawable backgroundDrawable = DrawableCompat.wrap(daysContainer[finalIndex].getBackground()).mutate();
                    DrawableCompat.setTint(backgroundDrawable, mSelectorColor);

                    days[finalIndex].setTextColor(mSelectedDayTextColor);

                }
            });

            index++;
        }

    }

    private void gotoNextMonth() {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);

        if (month < 11) {
            mCalendar.set(year, month + 1, 1);
        } else {
            mCalendar.set(year + 1, 0, 1);
        }

        initCalender(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH));
    }

    private void gotoPreviousMonth() {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);

        if (month > 0) {
            mCalendar.set(year, month - 1, 1);
        } else {
            mCalendar.set(year - 1, 11, 1);
        }

        initCalender(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH));
    }


    private void initDaysInCalender(LayoutParams buttonParams, Context context, DisplayMetrics metrics) {
        int engDaysArrayCounter = 0;

        for (int weekNumber = 0; weekNumber < 6; ++weekNumber) {
            for (int dayInWeek = 0; dayInWeek < 7; ++dayInWeek) {

                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setLayoutParams(buttonParams);
                linearLayout.setOrientation(VERTICAL);
                linearLayout.setGravity(CENTER);

                final TextView day = new TextView(context);
                day.setTextColor(Color.parseColor(CUSTOM_GREY));
                day.setBackgroundColor(Color.TRANSPARENT);
                day.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                day.setTextSize((int) metrics.density * 5);
                day.setSingleLine();
                day.setPadding(10, 10, 10, 10);
                day.setGravity(CENTER);


                TextView textView = new TextView(context);
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setTextSize(8);
                textView.setMaxLines(1);

                int maxLength = 2;
                InputFilter[] fArray = new InputFilter[1];
                fArray[0] = new InputFilter.LengthFilter(maxLength);
                textView.setFilters(fArray);
                textView.setText("H");
                textView.setGravity(CENTER);
                textView.setVisibility(INVISIBLE);

                linearLayout.addView(day);
                linearLayout.addView(textView);

                days[engDaysArrayCounter] = day;
                eventsTextViewList[engDaysArrayCounter] = textView;

                weeks[weekNumber].addView(linearLayout);

                daysContainer[engDaysArrayCounter] = linearLayout;

                engDaysArrayCounter++;
            }
        }
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CalenderEvent);
        mBackgroundColor = typedArray.getColor(R.styleable.CalenderEvent_calender_background, DEFAULT_BACKGROUND_COLOR);
        mSelectorColor = typedArray.getColor(R.styleable.CalenderEvent_selector_color, DEFAULT_SELECTOR_COLOR);
        mSelectedDayTextColor = typedArray.getColor(R.styleable.CalenderEvent_selected_day_text_color, DEFAULT_SELECTED_DAY_COLOR);
        mCurrentMonthDayColor = typedArray.getColor(R.styleable.CalenderEvent_current_month_day_color, DEFAULT_CURRENT_MONTH_DAY_COLOR);
        mOffMonthDayColor = typedArray.getColor(R.styleable.CalenderEvent_off_month_day_color, DEFAULT_OFF_MONTH_DAY_COLOR);
        mMonthTextColor = typedArray.getColor(R.styleable.CalenderEvent_month_color, DEFAULT_TEXT_COLOR);
        mWeekNameColor = typedArray.getColor(R.styleable.CalenderEvent_week_name_color, DEFAULT_TEXT_COLOR);
        nextButtonDrawable = typedArray.getDrawable(R.styleable.CalenderEvent_next_icon);
        prevButtonDrawable = typedArray.getDrawable(R.styleable.CalenderEvent_previous_icon);
        typedArray.recycle();
    }

    private void initView(Context context) {
        this.c1 = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mainView = inflater.inflate(R.layout.main_calender, this);
        }
    }

    private LayoutParams getdaysLayoutParams() {
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        buttonParams.weight = 1;
        return buttonParams;
    }

    private String getMonthFormat(int month){
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

    public void go_to_month_year(){

    }

}