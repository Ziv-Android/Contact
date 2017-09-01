package com.ziv.contact;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Created by ziv on 2017/9/1.
 */

public class TestContactOld extends AndroidTestCase{
    private final String BASE_AUTH = "content://com.android.contacts";
    private final String RAW_BASE = BASE_AUTH + "/raw_contacts";
    private final String DATA_BASE = BASE_AUTH + "/data";

    public void testGetAllContactInfo() throws Exception {
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri rawUri = Uri.parse(RAW_BASE);
        Cursor rawCursor = contentResolver.query(rawUri, null, null, null, null);
        if (rawCursor == null) {
            Log.e("JunitTest", "RawCursor is null.");
            return;
        }
        Uri dataUri = Uri.parse(DATA_BASE);
        while (rawCursor.moveToNext()) {
            String id = rawCursor.getString(rawCursor.getColumnIndex("_id"));
            Log.e("JunitTest", "id = " + id);
            Cursor dataCursor = contentResolver.query(dataUri, null, "raw_contact_id=?", new String[]{id}, null);
            if (dataCursor == null) {
                Log.e("JunitTest", "DataCursor is null.");
                break;
            }
            String[] columnNames = dataCursor.getColumnNames();
            for (int i = 0; i < columnNames.length; i++) {
                Log.e("JunitTest", columnNames[i]);
            }
            while (dataCursor.moveToNext()) {
                String data1 = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                String mimetype = dataCursor.getString(dataCursor.getColumnIndex("mimetype"));
                Log.e("JunitTest", "data1 = " + data1 + " mimetype = " + mimetype);
            }
            dataCursor.close();
        }
        rawCursor.close();
    }
}
