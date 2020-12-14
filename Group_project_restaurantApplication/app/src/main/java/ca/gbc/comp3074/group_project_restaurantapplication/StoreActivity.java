package ca.gbc.comp3074.group_project_restaurantapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    ArrayAdapter adapter;
    ArrayList arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        myDB = new DatabaseHelper(this);
        arrayList = myDB.getRestaurantList();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        Spinner tagMenu = findViewById(R.id.spinner_tags);
        myDB.addTags();
        List<String> tags = myDB.loadTags();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tags);
        tagMenu.setAdapter(arrayAdapter);

        Button buttonSaveRestaurant = (Button) findViewById(R.id.button_save_restaurant);
        final TextView name = (TextView) findViewById(R.id.txtEdit_name);
        final TextView address = (TextView) findViewById(R.id.txtEdit_address);
        final Spinner tag = (Spinner) findViewById(R.id.spinner_tags);
        final TextView description = (TextView) findViewById(R.id.txtEdit_description);
        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        buttonSaveRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Name = name.getText().toString();
                final String Address = address.getText().toString();
                final String Description = description.getText().toString();
                final String Tag = tag.getSelectedItem().toString();
                final String Rating = String.valueOf(ratingBar.getRating());

                if ((Name.isEmpty()) && Address.isEmpty() && Description.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Error Please Fill Out All Forms.", Toast.LENGTH_LONG).show();
                }

                myDB.addRestaurant(Name, Address, Description, Tag, Rating);
                Toast.makeText(getApplicationContext(), "Restaurant Added. Click Back To Return Home.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
