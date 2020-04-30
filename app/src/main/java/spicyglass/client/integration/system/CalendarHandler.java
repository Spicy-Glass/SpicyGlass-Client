package spicyglass.client.integration.system;


import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Pair;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarHandler {
    public static void addEvent(Context context, Calendar beginTime, Calendar endTime) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "Defrost");
        context.startActivity(intent); //what we want to do what's in intent variable
        //context actually starts running intent

        SGLogger.info(String.valueOf(intent.getData()));
    }

    public static void removeEvent(Activity activity, int eventID) {
        ContentResolver cr = activity.getApplicationContext().getContentResolver();
        Uri deleteUri = null;
        deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
        cr.delete(deleteUri, null, null);
    }

    /**
     * Request events within a date range that are named "Defrost"
     * @param activity this is the activity request event is being called from
     * @param beginTime start time of the search range
     * @param endTime end time of the search range
     * @return a list of pairs from ID to Start Time
     */
    public static List<Pair<Integer, Date>> requestEvent(Activity activity, Calendar beginTime, Calendar endTime) {
        String[] projection = new String[]{CalendarContract.Events.CALENDAR_ID, CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, CalendarContract.Events.ALL_DAY, CalendarContract.Events.EVENT_LOCATION};


        String selection = "(( " + CalendarContract.Events.DTSTART
                + " >= " + beginTime.getTimeInMillis()
                + " ) AND ( " + CalendarContract.Events.DTSTART
                + " <= " + endTime.getTimeInMillis() + " ))";

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.WRITE_CALENDAR}, 100);
        }
        Cursor cursor = activity.getBaseContext().getContentResolver().query(CalendarContract.Events.CONTENT_URI, projection, selection, null, null);

// output the events
        List<Pair<Integer, Date>> values = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {

            /*
            SGLogger.info(String.valueOf(cursor.getColumnIndex(CalendarContract.EXTRA_EVENT_BEGIN_TIME)));
            SGLogger.info(String.valueOf(cursor.getColumnIndex(CalendarContract.EXTRA_EVENT_END_TIME)));
            SGLogger.info(Arrays.toString(cursor.getColumnNames()));
            */
            //useful for finding the index of the list to see what index you want (ie end date)

            do {
                if(cursor.getString(1).equals("Defrost"))
                    values.add(new Pair<>((cursor.getInt(0)), (new Date(cursor.getLong(3)))));
                //Toast.makeText( activity.getApplicationContext(), "Title: "
                //      + cursor.getString(1)
                //    + " Start-Time: "
                //  + (new Date(cursor.getLong(3))).toString(), Toast.LENGTH_LONG ).show();
            } while ( cursor.moveToNext());
        }
        if (cursor != null)
            cursor.close();
        return values;
    }

    /*
    public static boolean getPermissions(Activity activity) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
        return true;
    }
*/
}