package com.saitorhan.allwindowsshortcuts;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.saitorhan.allwindowsshortcuts.db.DBCrud;

public class AppsActivity extends BaseActivity {

    String os;
ListView appsList;
EditText editText;
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);

        editText = findViewById(R.id.editTextSearch);
        appsList = findViewById(R.id.appsList);
        appsList.setOnItemClickListener(itemClickListener);
        textView = findViewById(R.id.textViewSelactedOs);


        os = getIntent().getStringExtra("os");
        textView.setText(String.format("Selected OS: %s%n", os));

        DBCrud dbCRUD = new DBCrud(this, false);
        Cursor urls = dbCRUD.database.query(true,"shorts", new String[]{"_id", "app", }, "os LIKE ?", new String[]{"%"+os+"%"}, "app", null, "app", null);

        if (urls.getCount() == 0) {
            Toast.makeText(this, getString(R.string.not_found), Toast.LENGTH_LONG).show();
            return;
        }

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.apps, urls, new String[]{"_id", "app",}, new int[]{R.id.textViewLayId, R.id.textViewAppLayout});
        appsList.setAdapter(simpleCursorAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Cursor itemAtPosition = (Cursor) parent.getItemAtPosition(position);
            String app = itemAtPosition.getString(1);
            Intent intent = new Intent(AppsActivity.this, MainActivity.class);
            intent.putExtra("os", os);
            intent.putExtra("app", app);
            startActivity(intent);
        }
    };

    public void searchClick(View view) {
        DBCrud dbCRUD = new DBCrud(this, false);
        Cursor urls = dbCRUD.database.query(true,"shorts", new String[]{"_id", "app", }, "os LIKE ? AND app LIKE ?", new String[]{"%"+os+"%", "%"+editText.getText().toString()+"%"}, "app", null, "app", null);

        if (urls.getCount() == 0) {
            Toast.makeText(this, getString(R.string.not_found), Toast.LENGTH_LONG).show();
            return;
        }


        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.apps, urls, new String[]{"_id", "app",}, new int[]{R.id.textViewLayId, R.id.textViewAppLayout});
        appsList.setAdapter(simpleCursorAdapter);
    }
}
