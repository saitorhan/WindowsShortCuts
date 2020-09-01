package com.saitorhan.allwindowsshortcuts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.saitorhan.allwindowsshortcuts.db.DBCrud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OperationSystemsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_systems);
        checkData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void OsClicked(View view) {
        Button button = (Button)view;
        if (button.getId() == R.id.buttonSearchAll) {
Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
startActivity(intent);
        } else {

            Intent intent = new Intent(getApplicationContext(), AppsActivity.class);
            intent.putExtra("os", button.getText().toString());
            startActivity(intent);
        }
    }

    private void checkData() {
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        boolean data = sharedPreferences.getBoolean("data", false);
        if (data) {
            return;
        }

        BufferedReader reader = null;
        try {
            DBCrud dbCrud = new DBCrud(this, true);
            String sql = "INSERT INTO shorts(_id, os, app, keys, todo) VALUES(?,?,?,?,?)";
            dbCrud.database.beginTransaction();
            SQLiteStatement sqLiteStatement = dbCrud.database.compileStatement(sql);

            reader = new BufferedReader(new InputStreamReader(getAssets().open("shortcuts.txt")));
            String s = reader.readLine();
            String[] settings = s.split("####");
            String[] split;
            int i = 0;
            for (String policy : settings) {
                split = policy.split("%%");
                sqLiteStatement.bindString(1, split[0]);
                sqLiteStatement.bindString(2, split[1]);
                sqLiteStatement.bindString(3, split[2]);
                sqLiteStatement.bindString(4, split[3]);
                sqLiteStatement.bindString(5, split[4]);
                sqLiteStatement.execute();
                sqLiteStatement.clearBindings();
            }

            dbCrud.database.setTransactionSuccessful();
            dbCrud.database.endTransaction();
            sharedPreferences.edit().putBoolean("data", true).apply();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException exc) {

        }
    }
}
