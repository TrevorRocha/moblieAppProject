package ca.gbc.comp3074.group_project_restaurantapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    private RatingBar ratingBar;
    private TextView name, address, description, tag;
    String getID;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        myDB = new DatabaseHelper(this);

        name = (TextView) findViewById(R.id.textView_name);
        address = (TextView) findViewById(R.id.textView_address);
        description = (TextView) findViewById(R.id.textView_description);
        tag = (TextView) findViewById(R.id.textView_tag);
        ratingBar = (RatingBar) findViewById(R.id.rating_Bar);
        bundle = getIntent().getExtras();
        if (bundle != null){
            String findID = (String) bundle.get("id");
            int ID = Integer.parseInt(findID);
            getID = ID + "";

            name.setText(myDB.getColumnName(ID));
            address.setText(myDB.getColumnAddress(ID));
            description.setText(myDB.getColumnDescription(ID));
            tag.setText(myDB.getColumnTags(ID));
            ratingBar.setRating(Float.parseFloat(myDB.getColumnRating(ID)));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.display_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.delete){
            deleteRestaurantFromDB();
        } else if (id == R.id.edit){
            Intent intent = new Intent(DisplayActivity.this, EditActivity.class);
            startActivity(intent);
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteRestaurantFromDB() {
        String findID = (String) bundle.get("id");
        int ID = Integer.parseInt(findID);
        if (myDB.deleteRestaurant(ID)){
            finish();
            Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(getIntent());
    }
}
