package spicyglass.client.ui.screens;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import spicyglass.client.R;

public class DefrostScheduler extends AppCompatActivity implements View.OnClickListener {

    Button ScheduleDefrost;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String txtDate, txtTime;
    Button add, del;
    TableLayout mytable;
    TableRow myrow;
    Calendar endtime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_handler);


        add = (Button) findViewById(R.id.addcalbut);
        del = (Button) findViewById(R.id.deletecalbut);
        mytable = (TableLayout) findViewById(R.id.CalTable);

        add.setOnClickListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_CALENDAR}, 100);
    }



    public void addRow(String jess) {
        myrow = new TableRow(this);
        TextView text1 = new TextView(this);
        text1.setText(jess);
        text1.setGravity(Gravity.CENTER);
        text1.setTextSize(15);
        myrow.addView(text1);
        mytable.addView(myrow);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addcalbut) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            Context context = this;
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate = dayOfMonth + "-" + monthOfYear + 1 + "-" + year;
                            mHour = c.get(Calendar.HOUR_OF_DAY);
                            mMinute = c.get(Calendar.MINUTE);
                            // Launch Time Picker Dialog
                            TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                                    new TimePickerDialog.OnTimeSetListener() {

                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                            txtTime = hourOfDay + ":" + minute;
                                        }
                                    }, mHour, mMinute, false);
                            timePickerDialog.show();
                            Calendar endtime = c;
                            endtime.add(Calendar.MINUTE, 15);
                            DefrostScheduler E = new DefrostScheduler();
                            // E.addEvent(this, c, endtime) ;
                            Log.d(DefrostScheduler.class.toString(), "Event added " + c.toString() + " " + endtime.toString());
                            String[] list = {c.toString(), endtime.toString()};
                            addRow(c.toString());
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

        }

    }
}



