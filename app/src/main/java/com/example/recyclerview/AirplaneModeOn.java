package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AirplaneModeOn extends AppCompatActivity {
    private static final String TAG = "AirplaneModeOn";
    EditText contactName , contactNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airplane_mode_on);
        contactName = (EditText)findViewById(R.id.contactName);
        contactNumber = (EditText) findViewById(R.id.contactNumber);
    }

    public void discardContact(View view) {
        Log.d(TAG, "discardContact: Discarding contact");
        Toast.makeText(this, "contact not saved", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void saveContact(View view) {
        contactName = (EditText) findViewById(R.id.contactName);
        contactNumber = (EditText) findViewById(R.id.contactNumber);
        if (contactName.length() < 1 || contactName == null) {
            Toast.makeText(this, "Contact Name cannot be empty", Toast.LENGTH_LONG).show();
            return;
        } else if (contactNumber.length() < 10 || contactName == null) {
            Toast.makeText(this, "Contact Number should be of length 10", Toast.LENGTH_LONG).show();
            return;
        } else {
            Toast.makeText(this, "Contact Saved", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
