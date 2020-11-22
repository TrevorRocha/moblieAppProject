package ca.gbc.comp3074.group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

public class EditActivity extends AppCompatActivity {

    Button btn_gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        btn_gps = (Button)findViewById(R.id.btn_gps);

        btn_gps.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, GpsActivity.class);
                startActivity(intent);
            }
        });
    }
}