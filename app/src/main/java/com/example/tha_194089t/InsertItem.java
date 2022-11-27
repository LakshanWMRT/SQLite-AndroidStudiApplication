package com.example.tha_194089t;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class InsertItem extends AppCompatActivity {

    AwesomeValidation awesomeValidation;
    String id, name, description, price;
    EditText name_input, description_input, price_input;
    Button add_button, update_button, delete_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_item);

        name_input = findViewById(R.id.name_input);
        description_input = findViewById(R.id.description_input);
        price_input = findViewById(R.id.price_input);
        add_button = findViewById(R.id.add_button);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        Intent i = getIntent();
        if (i.getExtras().getInt("type") == 1) {
            update_button.setVisibility(View.GONE);
            delete_button.setVisibility(View.GONE);
            awesomeValidation.addValidation(this, R.id.name_input,
                    RegexTemplate.NOT_EMPTY, R.string.name_error);
            add_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (awesomeValidation.validate()) {
                        DatabaseConnector myDB = new DatabaseConnector(InsertItem.this);
                        myDB.addItem(name_input.getText().toString().trim(),
                                description_input.getText().toString().trim(),
                                Double.valueOf(price_input.getText().toString().trim()));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Unsuccessful! Try Again",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            add_button.setVisibility(View.GONE);
            getAndSetIntentData();
            final String description_in = description_input.getText().toString().trim();

            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setTitle(name);
            }
            update_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //And only then we call this
                    if (description_in.length() <= 50) {
                        DatabaseConnector myDB = new DatabaseConnector(InsertItem.this);
                        name = name_input.getText().toString().trim();
                        description = description_input.getText().toString().trim();
                        price = price_input.getText().toString().trim();
                        myDB.updateData(id, name, description, price);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "The description cannot exceed 50 characters. !",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmDialog();
                }
            });
        }


    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("description") && getIntent().hasExtra("price")) {
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            description = getIntent().getStringExtra("description");
            price = getIntent().getStringExtra("price");

            //Setting Intent Data
            name_input.setText(name);
            description_input.setText(description);
            price_input.setText(price);
            Log.d("stev", name + " " + description + " " + price);
        } else {
            Toast.makeText(this, "Empty .", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Do you want to delete  " + name + " ? This action cannnot be undone");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseConnector myDB = new DatabaseConnector(InsertItem.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

}
