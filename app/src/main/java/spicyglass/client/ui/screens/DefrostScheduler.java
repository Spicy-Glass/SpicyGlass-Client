package spicyglass.client.ui.screens;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import spicyglass.client.R;
import spicyglass.client.integration.system.CalendarHandler;

public class DefrostScheduler extends AppCompatActivity implements View.OnClickListener {

    Button ScheduleDefrost;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String txtDate, txtTime;
    Button add, del;
    TableLayout mytable;
    TableRow myrow;
    Calendar endtime;
    EditText dateinput, timeinput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_handler);


        add = (Button) findViewById(R.id.addcalbut);
        del = (Button) findViewById(R.id.deletecalbut);
        mytable = (TableLayout) findViewById(R.id.CalTable);
        add.setOnClickListener(this);
        del.setOnClickListener(this);
    }


    public void addRow(Calendar begintime) {
        myrow = new TableRow(this);
        CheckBox check = new CheckBox(this);
        check.setChecked(false);
        TextView text1 = new TextView(this);
        text1.setText("Defrost Event at " + begintime.getTime().toString());
        text1.setGravity(Gravity.CENTER);
        text1.setTextSize(15);
        myrow.addView(text1);
        myrow.addView(check);
        mytable.addView(myrow);


        endtime = Calendar.getInstance();
        endtime.set(begintime.get(Calendar.YEAR), begintime.get(Calendar.MONTH),
                begintime.get(Calendar.DAY_OF_MONTH), begintime.get(Calendar.HOUR_OF_DAY),
                begintime.get(Calendar.MINUTE), 0);
        endtime.add(Calendar.MINUTE, 15);
        CalendarHandler.addEvent(this, begintime, endtime);
    }

    public void deleteRow(Calendar begintime, int i) {
        mytable.removeViews(i, 1);

        endtime = Calendar.getInstance();
        endtime.set(begintime.get(Calendar.YEAR), begintime.get(Calendar.MONTH),
                begintime.get(Calendar.DAY_OF_MONTH), begintime.get(Calendar.HOUR_OF_DAY),
                begintime.get(Calendar.MINUTE), 1);
        endtime.add(Calendar.MINUTE, 15);
        List<Pair<Integer, Date>> int_date = CalendarHandler.requestEvent(this, begintime, endtime);
        Pair pair = int_date.get(0);
        int key = (Integer) pair.first;
        CalendarHandler.removeEvent(this, key);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addcalbut:
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                Context context = this;
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                txtDate = monthOfYear + "/" + dayOfMonth + "/" + year;
                                mHour = c.get(Calendar.HOUR_OF_DAY);
                                mMinute = c.get(Calendar.MINUTE);
                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                c.set(Calendar.YEAR, year);
                                c.set(Calendar.MONTH, monthOfYear);
                                // Launch Time Picker Dialog
                                TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                                        new TimePickerDialog.OnTimeSetListener() {

                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                txtTime = hourOfDay + ":" + minute;
                                                c.set(Calendar.HOUR, hourOfDay);
                                                c.set(Calendar.MINUTE, minute);
                                                addRow(c);
                                            }

                                        }, mHour, mMinute, true);
                                DefrostScheduler E = new DefrostScheduler();

                                timePickerDialog.show();

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.deletecalbut:

                for (int i = 1; i < mytable.getChildCount(); i++) {
                    TableRow mytablerow = (TableRow) mytable.getChildAt(i);
                    CheckBox CB = (CheckBox) mytablerow.getChildAt(1);
                    if (CB.isChecked()) {
                        TextView mytextview = (TextView) mytablerow.getChildAt(0);
                        String mytextviewtext = mytextview.getText().toString();
                        int datetimeIndex = mytextviewtext.indexOf("at ") + 3;
                        String datetime = mytextviewtext.substring(datetimeIndex);
                        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                        Calendar mycal = Calendar.getInstance();
                        try {
                            mycal.setTime(format.parse(datetime));
                            deleteRow(mycal, i);
                            // Continue
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }
    }
}



