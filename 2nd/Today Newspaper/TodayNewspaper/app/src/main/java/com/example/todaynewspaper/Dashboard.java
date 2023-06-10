package com.example.todaynewspaper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        Button button = findViewById(R.id.button4);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button4) {
            openLocationClass();
        }
    }

    private void openLocationClass() {
        Intent intent = new Intent(this, Location.class);
        startActivity(intent);
    }
}
