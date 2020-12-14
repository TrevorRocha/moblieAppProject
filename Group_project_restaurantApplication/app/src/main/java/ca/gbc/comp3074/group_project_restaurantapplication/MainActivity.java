package ca.gbc.comp3074.group_project_restaurantapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ListView listView;
    SearchView searchView;
    ArrayList arrayList;
    ArrayAdapter adapter;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);
        arrayList = myDB.getRestaurantList();

        Button btnRefreshList = (Button) findViewById(R.id.button_loadList);
        btnRefreshList.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                displayAllRestaurants();
            }
        });

        listView = findViewById(R.id.restaurantListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String key = adapter.getItem(position).toString();
                String[] itemID = key.split(" - " , 2);
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra("id", itemID[0]);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(getIntent());
    }

    private void displayAllRestaurants() {
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView = findViewById(R.id.restaurantListView);
        listView.setAdapter(adapter);
        if (arrayList.isEmpty()){
            arrayList.addAll(myDB.getRestaurantList());
            adapter.notifyDataSetChanged();
            listView.invalidateViews();
            listView.refreshDrawableState();
        }
        searchView = (SearchView) findViewById(R.id.searchBox);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        displayAllRestaurants();
        String search = newText;
        adapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.about){
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return false;
        } else if (id == R.id.store){
            Intent intent = new Intent(MainActivity.this, StoreActivity.class);
            startActivity(intent);
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}