package com.ziv.contact;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 获取联系人信息
 * <p>
 * Created by ziv on 2017/9/1.
 */

@RunWith(AndroidJUnit4.class)
public class TestContact {
    private final String BASE_AUTH = "content://com.android.contacts";
    private final String RAW_BASE = BASE_AUTH + "/raw_contacts";
    private final String DATA_BASE = BASE_AUTH + "/data";

    private final String MIMETYPE_NAME = "vnd.android.cursor.item/name";
    private final String MIMETYPE_PHONE = "vnd.android.cursor.item/phone_v2";
    private final String MIMETYPE_EMAIL = "vnd.android.cursor.item/email_v2";

    private ContentResolver contentResolver;

    @Before
    public void init(){
        if (contentResolver == null) {
            contentResolver = InstrumentationRegistry.getTargetContext().getContentResolver();
        }
    }

    @Test
    public void getAllContactInfo() throws Exception {
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

    @Test
    public void writeContact() throws Exception{
        ContentValues values = new ContentValues();
        Uri rawUri = Uri.parse(RAW_BASE);

        // 返回 raw_contact 表中的 _id
        Uri insertUri = contentResolver.insert(rawUri, values);
        long id = ContentUris.parseId(insertUri);

        // 操作data表
        Uri dataUri = Uri.parse(DATA_BASE);
        addInformation(dataUri, id, MIMETYPE_NAME, "aling");
        addInformation(dataUri, id, MIMETYPE_PHONE, "66666");
        addInformation(dataUri, id, MIMETYPE_EMAIL, "aling@sina.cn");
    }

    private void addInformation(Uri dataUri, long id, String mimetype, String data){
        ContentValues values = new ContentValues();
        values.put("raw_contact_id", id);
        values.put("mimetype", mimetype);
        values.put("data1", data);
        contentResolver.insert(dataUri, values);
    }
}
