package com.saitorhan.allwindowsshortcuts;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.saitorhan.allwindowsshortcuts.db.DBCrud;

public class MainActivity extends BaseActivity {

    ListView listView;
String os1, app1;
EditText editText;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindControls();
        Intent intent = getIntent();
        os1 = intent.getStringExtra("os");
        app1 =intent.getStringExtra("app");
        textView.setText(String.format("Selected %s on %s%n", app1, os1));
        getdata(os1, app1);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void bindControls() {
        listView = findViewById(R.id.list);
        editText=findViewById(R.id.editTextSearchShort);
        textView = findViewById(R.id.textViewSelectedApp);
    }

    private void getdata(String os, String app) {
        DBCrud dbCRUD = new DBCrud(this, false);
        Cursor urls = dbCRUD.database.query("shorts", new String[]{"_id","keys", "todo"}, "os LIKE ? AND app = ?", new String[]{"%"+os+"%", app}, null, null, null, null);

        if (urls.getCount() == 0) {
            Toast.makeText(this, getString(R.string.not_found), Toast.LENGTH_LONG).show();
            return;
        }

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.layout, urls, new String[]{"_id", "keys", "todo"}, new int[]{R.id.textViewId, R.id.textViewKeySearch, R.id.textViewToDo});
        listView.setAdapter(simpleCursorAdapter);
    }

    public void searchClick(View view) {


        DBCrud dbCRUD = new DBCrud(this, false);
        Cursor urls = dbCRUD.database.query("shorts", new String[]{"_id","keys", "todo"}, "os LIKE ? AND app = ? AND todo LIKE ?", new String[]{"%"+os1+"%", app1, "%"+editText.getText().toString()+"%"}, null, null, null, null);

        if (urls.getCount() == 0) {
            Toast.makeText(this, getString(R.string.not_found), Toast.LENGTH_LONG).show();
            return;
        }


        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.layout, urls, new String[]{"_id", "keys", "todo"}, new int[]{R.id.textViewId, R.id.textViewKeySearch, R.id.textViewToDo});
        listView.setAdapter(simpleCursorAdapter);
    }
}
