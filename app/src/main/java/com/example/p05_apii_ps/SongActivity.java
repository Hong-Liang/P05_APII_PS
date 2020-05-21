package com.example.p05_apii_ps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SongActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Song> al;
    ArrayAdapter aa, aaYear;
    int requestCode = 1;
    Button btnshow;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        lv = findViewById(R.id.lv);
        btnshow = findViewById(R.id.btn5stars);
        spinner = findViewById(R.id.spinner);

        DBHelper db = new DBHelper(SongActivity.this);
        al = db.getAllNotes();

        aa = new SongAdapter(this, R.layout.row, al);
        aaYear = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);
        spinner.setAdapter(aaYear);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Song data = al.get(position);
                Intent i = new Intent(SongActivity.this,
                        ModifyActivity.class);
                i.putExtra("data", data);
                startActivityForResult(i, requestCode);
            }
        });

        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbh = new DBHelper(SongActivity.this);
                al.clear();
                al.addAll(dbh.getSpecificByStar("5"));
                dbh.close();

                aa.notifyDataSetChanged();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = spinner.getSelectedItem().toString();
                DBHelper dbh = new DBHelper(SongActivity.this);

                al.clear();
                al.addAll(dbh.getSpecificByYear(selected));
                dbh.close();

                aa.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Only handle when 2nd activity closed normally
        //  and data contains something
        if (resultCode == RESULT_OK) {
            if (requestCode == requestCode) {
                DBHelper db = new DBHelper(SongActivity.this);
                al.clear();
                al.addAll(db.getAllNotes());
                db.close();
                aa.notifyDataSetChanged();
            }
        }
    }
}
