package com.example.myapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
EditText nameEditText;
EditText descEditText;
SeekBar scarinessSeekBar;
EditText idEditText2;
Integer scarinessLevel = 0;
Button addButton;
Button deleteButton4;
Button updateButton3;
Button viewAllButton2;
DatabaseHelper dbHelper;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);

//find the database file in your mobile at:
        //data/data/app_name/databases

        nameEditText = findViewById(R.id.nameEditText);
        descEditText = findViewById(R.id.descEditText);
        scarinessSeekBar = findViewById(R.id.scarinessSeekBar);
        idEditText2 = findViewById(R.id.idEditText2);

        scarinessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                scarinessLevel = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        
        addButton = findViewById(R.id.addButton);
        viewAllButton2 = findViewById(R.id.viewAllButton2);
        updateButton3 = findViewById(R.id.updatebutton3);
        deleteButton4 = findViewById(R.id.deletebutton4);
        
        
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add(view);
            }
        });
        viewAllButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAll(v);
            }
        });
        updateButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(v);
            }
        });
        deleteButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(v);
                
            }
        });
        
    }

    private void delete(View v) {
        String id = idEditText2.getText().toString();
        boolean result = dbHelper.delete(Integer.valueOf(id));
        if(result)
            Snackbar.make(v, "Your monster was deleted", Snackbar.LENGTH_SHORT).show();
        else
            Snackbar.make(v , "Your monster wasn't deleted", Snackbar.LENGTH_SHORT).show();


    }

    private void update(View v) {
        String id = idEditText2.getText().toString();
        String name = nameEditText.getText().toString();
        String description = descEditText.getText().toString();
       boolean result =  dbHelper.update(Integer.valueOf(id), name, description, scarinessLevel);

        if(result)
            Snackbar.make(v, "Your monster was updated", Snackbar.LENGTH_SHORT).show();
        else
            Snackbar.make(v , "Your monster wasn't updated", Snackbar.LENGTH_SHORT).show();

    }

    private void viewAll(View v) {
        Cursor dataCursor = dbHelper.getAll();
        if(dataCursor.getCount()==0){
            dataCursor.close();
            showMessage("Records", "Nothing Found");
        }
        else{
            StringBuffer buffer = new StringBuffer();
            while(dataCursor.moveToNext())
            {
                buffer.append("id: " + dataCursor.getString(0) + "\n");
                buffer.append("name: " + dataCursor.getString(1) + "\n");
                buffer.append("description: " + dataCursor.getString(2) + "\n");
                buffer.append("scariness: " + dataCursor.getString(3) + "\n");
                buffer.append("image: " + dataCursor.getString(4) + "\n");
                buffer.append("___________________" + "\n");
            }
            dataCursor.close();
            showMessage("Data", buffer.toString());
        }


    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    private void add(View view) {
        //call db to insert the monster
      String name = nameEditText.getText().toString();
      String description = descEditText.getText().toString();

      boolean result = dbHelper.insert(name, description, scarinessLevel);
       if(result)
           Snackbar.make(view, "Your monster was created", Snackbar.LENGTH_SHORT).show();
       else
           Snackbar.make(view, "Your monster wasn't created", Snackbar.LENGTH_SHORT).show();

    }
}
