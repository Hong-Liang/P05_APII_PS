package com.example.p05_apii_ps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class ModifyActivity extends AppCompatActivity {

    Button btnUpdate, btnDelete, btnCancel;
    Song data;
    EditText etID, etTitle, etSinger, etYear;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify);

        btnCancel = findViewById(R.id.btnCancel);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        etID = findViewById(R.id.etID);
        etTitle = findViewById(R.id.etST);
        etYear = findViewById(R.id.etY);
        etSinger = findViewById(R.id.etSing);
        rg = findViewById(R.id.rgStars);

        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");

        etID.setText(data.getId() + "");
        etTitle.setText(data.getTitle());
        etSinger.setText(data.getSinger());
        etYear.setText(data.getYear());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbh = new DBHelper(ModifyActivity.this);
                dbh.deleteNote(data.getId());
                dbh.close();
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedButtonId = rg.getCheckedRadioButtonId();
                // Get the radio button object from the Id we had gotten above
                RadioButton rb = (RadioButton) findViewById(selectedButtonId);
                int rbv = Integer.parseInt(rb.getText().toString());
                DBHelper dbh = new DBHelper(ModifyActivity.this);
                String title = etTitle.getText().toString();
                String year = etYear.getText().toString();
                String singer = etSinger.getText().toString();
                data.setTitle(title);
                data.setSinger(singer);
                data.setYear(year);
                data.setStars(rbv);
                dbh.updateNote(data);
                dbh.close();
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}
