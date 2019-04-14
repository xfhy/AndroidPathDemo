package com.xfhy.processdemo.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.xfhy.processdemo.Book;
import com.xfhy.processdemo.R;
import com.xfhy.processdemo.file.User;

public class ProviderActivity extends AppCompatActivity {

    private static final String TAG = "ProviderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        /*Uri uri = Uri.parse("content://com.xfhy.processdemo.contentprovider");
        getContentResolver().query(uri, null, null, null, null);
        getContentResolver().query(uri, null, null, null, null);
        getContentResolver().query(uri, null, null, null, null);*/

        Uri bookUri = Uri.parse("content://com.xfhy.processdemo.contentprovider/book");
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", 6);
        contentValues.put("name", "程序设计的艺术");
        getContentResolver().insert(bookUri, contentValues);

        Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null);
        if (bookCursor != null) {
            while (bookCursor.moveToNext()) {
                Book book = new Book();
                book.bookId = bookCursor.getInt(0);
                book.bookName = bookCursor.getString(1);
                Log.e(TAG, "query book:" + book.toString());
            }
            bookCursor.close();
        }

        Uri userUri = Uri.parse("content://com.xfhy.processdemo.contentprovider/user");
        Cursor userCursor = getContentResolver().query(userUri, new String[]
                {"_id", "name", "sex"}, null, null, null);
        while (userCursor.moveToNext()) {
            User user = new User();
            user.userId = userCursor.getInt(0);
            user.userName = userCursor.getString(1);
            user.isMale = userCursor.getInt(2) == 1;
            Log.e(TAG, "query user:" + user.toString());
        }
        userCursor.close();
    }
}
