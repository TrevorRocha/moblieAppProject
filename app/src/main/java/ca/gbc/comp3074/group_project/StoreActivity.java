package ca.gbc.comp3074.group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StoreActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText editName, editAddress, editDescription, editTags;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        btnAdd = (Button)findViewById(R.id.btn_add);
        editName = (EditText)findViewById(R.id.txtEdit_name);
        editAddress = (EditText)findViewById(R.id.txtEdit_address);
        editDescription = (EditText)findViewById(R.id.txtEdit_Description);
        editTags = (EditText)findViewById(R.id.txtEdit_tag);
        AddData();
    }

    public void AddData(){
        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDB.insertData("1", editName.getText().toString(), editAddress.getText().toString(), editDescription.getText().toString(), editTags.getText().toString());
                        if (isInserted == true)
                            Toast.makeText(StoreActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(StoreActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

}