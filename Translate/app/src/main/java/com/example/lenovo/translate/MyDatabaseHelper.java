package com.example.lenovo.translate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by lenovo on 2019/7/6.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE = "create table word("
            +"id integer primary key autoincrement,"
            +"inputText text,"
            +"translation text)";
    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE);
        Toast.makeText(mContext,"Create Succeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
