package com.example.foodplannerapplication.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.foodplannerapplication.R;
import com.example.foodplannerapplication.models.Meal;
import com.example.foodplannerapplication.models.PlannedMeal;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class BottomSheet {
    private Date date;

    private TextView psWeekDay;

    private static Meal myMeal;

    private int mealType, day;

    private static final String TAG = "BottomSheet";
    private BottomSheetDialog bottomSheetDialog;

    private static BottomSheet instance;

    public static BottomSheet getInstance(Context context, Meal meal, OnBottomConfirmed onBottomConfirmed) {
        myMeal = meal;
        if (instance == null) {
            instance = new BottomSheet(context, meal, onBottomConfirmed);
        }
        return instance;
    }

    public void show() {
        if (!bottomSheetDialog.isShowing()) {
            bottomSheetDialog.show();
        }
    }

    public void dismiss() {
        if (bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
    }


    private BottomSheet(Context context, Meal meal, OnBottomConfirmed onBottomConfirmed) {
        mealType = -1;
        day = -1;
        myMeal = meal;
        createBottomSheetDialog(context, onBottomConfirmed);
    }

    private void showDatePickerDialog(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date startDate = calendar.getTime();//fri => 6
        int tomorrowDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        Log.i(TAG, "tomorrowDayOfWeek: " + tomorrowDayOfWeek);
        int daysUntilNextFriday = Calendar.FRIDAY - tomorrowDayOfWeek;//6-7
        Log.i(TAG, "daysUntilNextFriday: " + daysUntilNextFriday);
        if (daysUntilNextFriday < 0) {
            daysUntilNextFriday += 7;
        }
        calendar.add(Calendar.DAY_OF_WEEK, daysUntilNextFriday);
        Date maxDate = calendar.getTime();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.set(Calendar.HOUR, 0);
            mCalendar.set(Calendar.MINUTE, 0);
            mCalendar.set(Calendar.SECOND, 0);
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            Log.i(TAG, mCalendar.getTime().getTime() + "");


            date = new Date(mCalendar.getTime().getTime());


            String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);
            day = getDayNum(selectedDate.split(",")[0]);
            psWeekDay.setText(selectedDate);
        }, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(startDate.getTime());
        datePickerDialog.getDatePicker().setMaxDate(maxDate.getTime());
        datePickerDialog.show();
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.orange));
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.orange));
    }

    private void createBottomSheetDialog(Context context, OnBottomConfirmed onBottomConfirmed) {

        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_layout);


        ChipGroup cgType = bottomSheetDialog.findViewById(R.id.cg_type);
        psWeekDay = bottomSheetDialog.findViewById(R.id.ps_week_day);
        Button btnConfirm = bottomSheetDialog.findViewById(R.id.btn_confirm);

        cgType.setOnCheckedChangeListener((group, checkedId) -> {
            Chip chip = group.findViewById(checkedId);
            if (chip != null) {
                switch (chip.getText().toString().trim()) {
                    case "Breakfast":
                        mealType = Constants.MealTypes.BREAKFAST;
                        break;
                    case "Lunch":
                        mealType = Constants.MealTypes.LUNCH;
                        break;
                    case "Dinner":
                        mealType = Constants.MealTypes.DINNER;
                        break;
                    default:
                        mealType = -1;
                }
            } else {
                mealType = -1;
            }
        });

        psWeekDay.setOnClickListener(v -> showDatePickerDialog(context));

        btnConfirm.setOnClickListener(v -> {
            if (myMeal != null) {
                if (isValid(context)) {
                    PlannedMeal plannedMeal = new PlannedMeal(date.getTime(), day, mealType, myMeal);
                    onBottomConfirmed.onClick(plannedMeal);
                }
            }
        });
        //bottomSheetDialog.show();
    }

    private boolean isValid(Context context) {
        if (mealType == -1) {
            Toast.makeText(context, "Choose your meal type.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (day == -1) {
            Toast.makeText(context, "Choose a date to plan the meal.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private int getDayNum(String dayStr) {
        switch (dayStr) {
            case "Saturday":
                return Constants.DAYS.SATURDAY;
            case "Sunday":
                return Constants.DAYS.SUNDAY;
            case "Monday":
                return Constants.DAYS.MONDAY;
            case "Tuesday":
                return Constants.DAYS.TUESDAY;
            case "Wednesday":
                return Constants.DAYS.WEDNESDAY;
            case "Thursday":
                return Constants.DAYS.THURSDAY;
            case "Friday":
                return Constants.DAYS.FRIDAY;
            default:
                return -1;
        }
    }

    public interface OnBottomConfirmed {
        void onClick(PlannedMeal plannedMeal);
    }
}
