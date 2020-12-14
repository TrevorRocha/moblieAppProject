package ca.gbc.comp3074.group_project_restaurantapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class EditActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    private RatingBar ratingBar;
    private Spinner tag;
    private EditText name, address, description;
    String getID;
    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        myDB = new DatabaseHelper(this);

        name = (EditText) findViewById(R.id.EditText_name);
        address = (EditText) findViewById(R.id.EditText_address);
        description = (EditText) findViewById(R.id.EditText_description);
        tag = (Spinner) findViewById(R.id.spinner_tags);
        ratingBar = (RatingBar) findViewById(R.id.rating_Bar);
        bundle = getIntent().getExtras();
        if (bundle != null){
            String findID = (String) bundle.get("id");
            int ID = Integer.parseInt(findID);
            getID = ID + "";

            name.setText(myDB.getColumnName(ID));
            address.setText(myDB.getColumnAddress(ID));
            description.setText(myDB.getColumnDescription(ID));
            ratingBar.setRating(Float.parseFloat(myDB.getColumnRating(ID)));

            Spinner tagMenu = findViewById(R.id.spinner_tags);
            myDB.addTags();
            List<String> tags = myDB.loadTags();
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tags);
            tagMenu.setAdapter(arrayAdapter);
            tag.setSelection(arrayAdapter.getPosition(myDB.getColumnTags(ID)));
        }

        Button buttonSaveRestaurant = (Button) findViewById(R.id.button_save_restaurant);
        buttonSaveRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Name = name.getText().toString();
                final String Address = address.getText().toString();
                final String Description = description.getText().toString();
                final String Tag = tag.getSelectedItem().toString();
                final String Rating = String.valueOf(ratingBar.getRating());

                if ((Name.isEmpty()) && Address.isEmpty() && Description.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Error.", Toast.LENGTH_LONG).show();
                }

                myDB.editRestaurant(Integer.parseInt(getID), Name, Address, Description, Tag, Rating);
                Toast.makeText(getApplicationContext(), "Restaurant Saved. Click Back To Return Home.", Toast.LENGTH_LONG).show();
            }
        });
    }


}
