package com.saitorhan.allwindowsshortcuts;

import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.saitorhan.allwindowsshortcuts.db.DBCrud;

public class SearchActivity extends BaseActivity {

    ListView listAll;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listAll = findViewById(R.id.listAll);
        editText = findViewById(R.id.editTextAllSearch);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void searchClick(View view) {
        DBCrud dbCRUD = new DBCrud(this, false);
        Cursor urls = dbCRUD.database.query("shorts", new String[]{"_id", "os", "app", "keys", "todo"}, "todo LIKE ?", new String[]{"%"+editText.getText().toString()+"%"}, null, null, "os", null);

        if (urls.getCount() == 0) {
            Toast.makeText(this, getString(R.string.not_found), Toast.LENGTH_LONG).show();
            return;
        }


        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.searchresult, urls, new String[]{"_id", "os", "app", "keys", "todo"}, new int[]{R.id.textViewIdAll, R.id.textViewOsSearchAll, R.id.textViewAppSearchAll, R.id.textViewKeySearchAll, R.id.textViewToDoSeachAll});
        listAll.setAdapter(simpleCursorAdapter);
    }
}
