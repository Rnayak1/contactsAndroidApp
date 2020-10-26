package com.example.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // macros
//    private int READ_CONTACT_PERMISSION = 1;
//    // variables
//    ArrayList<String> contactImage = new ArrayList<String>();
//    ArrayList<String> contactName = new ArrayList<String>();
//    ArrayList<String> contactNumber = new ArrayList<String>();
//    RecyclerView r1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        if (isAirplaneModeOff(this)) {
            Log.d(TAG, "onCreate: Airplane mode");
            Toast.makeText(this, "Airplane mode on", Toast.LENGTH_LONG).show();
            setContentView(R.layout.airplane_mode_on);
        } else {
            setContentView(R.layout.activity_main);
            // contacts class
            Contacts contact = new Contacts(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isAirplaneModeOff(this)) {
            Log.d(TAG, "onCreate: Airplane mode");
            Toast.makeText(this, "Airplane mode on", Toast.LENGTH_LONG).show();
            setContentView(R.layout.airplane_mode_on);
        } else {
            setContentView(R.layout.activity_main);
            Contacts contact = new Contacts(this);
        }
    }

    private static boolean isAirplaneModeOff(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: menu created");
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: " + item.getItemId());
        switch (item.getItemId()) {
            case R.id.toggleAirplaneMode:
                Toast.makeText(this, "Toggled Airplane mode", Toast.LENGTH_LONG).show();
                break;
            case R.id.exitApp:
                Toast.makeText(this, "App will be exited", Toast.LENGTH_LONG).show();
                break;
            case R.id.refreshContact:
                Toast.makeText(this, "contact list updated", Toast.LENGTH_LONG).show();
                break;
            case R.id.switchContacts: {
                String itemText = item.getTitle().toString();
                if(itemText == "Hide Contacts") {
                    item.setTitle("Show Contacts");
                    setContentView(R.layout.airplane_mode_on);
                    break;
                } else {
                    item.setTitle("Hide Contacts");
                    setContentView(R.layout.activity_main);
                    Contacts contact = new Contacts(this);
                }
                break;
            }
        }
        return true;
    }

    public void addContact(View view) {
        Log.d(TAG, "addContact: creating new activity");
        Toast.makeText(this, " made success", Toast.LENGTH_LONG).show();
        Intent i1 = new Intent(this, AirplaneModeOn.class);
        startActivity(i1);
    }

    public void exitApp(MenuItem item) {
        finish();
    }
}
