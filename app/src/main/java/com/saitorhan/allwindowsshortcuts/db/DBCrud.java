package com.saitorhan.allwindowsshortcuts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.saitorhan.allwindowsshortcuts.R;

public class DBCrud {

    public SQLiteDatabase database;
    DbProcess dbProcessor;

    public DBCrud(Context context, boolean writable) {
        int dbVersion = Integer.parseInt(context.getString(R.string.dbVersion));

        dbProcessor = new DbProcess(context, context.getString(R.string.dbName), null, dbVersion);
        database = writable ? dbProcessor.getWritableDatabase() : dbProcessor.getReadableDatabase();
    }
}
