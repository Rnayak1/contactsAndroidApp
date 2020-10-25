package com.example.recyclerview;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Contacts {
    private static final String TAG = "Contacts";
    private int READ_CONTACTS = 1;
    private int WRITE_CONTACTS = 1;

    // data for recycler view
    ArrayList<String> contactImage = new ArrayList<String>();
    ArrayList<String> contactName = new ArrayList<String>();
    ArrayList<String> contactNumber = new ArrayList<String>();

    private WeakReference<MainActivity> mainActivityWeakReference;
    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.M)
    Contacts(MainActivity activity){
        Log.d(TAG, "Contacts: constructor called" + activity);
        mainActivityWeakReference = new WeakReference<MainActivity>(activity);
        context = activity.getBaseContext();
        ReadContactRequestPermission();
    }

    private void ReadContactRequestPermission() {
        Log.i(TAG, "ReadContactRequestPermission: Reading permission");
        final MainActivity activity = mainActivityWeakReference.get();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_CONTACTS)) {
                    new AlertDialog.Builder(activity)
                            .setTitle("Allow Reading Contact")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                } else {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS);
                }
            } else {
                getContacts();
            }
        } else {
            getContacts();
        }
    }


    private void getContacts() {
        Log.d(TAG, "getContacts: getting contacts");
        MainActivity activity = mainActivityWeakReference.get();
        ArrayList<String> contacts = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        String name = null, id =null, phoneNumber = null;
        Log.d(TAG, "getContacts: "+cr);
        try {
            Cursor contactList = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, "UPPER(" + ContactsContract.Contacts.DISPLAY_NAME + ") ASC");
            if ((contactList != null) && (contactList.getCount() > 0)) {
                while (contactList.moveToNext()) {
                    Log.d(TAG, "getContacts: contactList =>"+ contactList.getColumnNames().toString());

                    if(contactList.getInt(contactList.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        id = contactList.getString(contactList.getColumnIndex(ContactsContract.Contacts._ID));
                        name = contactList.getString(contactList.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        Cursor phoneQuery = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"= ?" , new String[] {id},
                                "UPPER("+ContactsContract.Contacts.DISPLAY_NAME+") ASC");
                        while(phoneQuery.moveToNext()){
                            phoneNumber = phoneQuery.getString(phoneQuery.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }
                        phoneQuery.close();
                    }
                    Log.d(TAG, "getContacts() returned: " + id + " " +name + " "+ phoneNumber );

                    // inserting data for recycler view
                    contactName.add(name);
                    contactImage.add(Character.toString(name.charAt(0)));
                    contactNumber.add(phoneNumber);
                }
                Toast.makeText(activity, "count = "+contactList.getCount(), Toast.LENGTH_SHORT).show();
                contactList.close();
            } else {
                Log.e(TAG, "getContacts: failed to get Contacts ",null );
            }

            initRecyclerView();
        } catch(Exception e) {
            Log.d(TAG, "getContacts: error in cs");
            Log.e(TAG, "getContacts: error", e );
            e.printStackTrace();
        }
        return;
    }
    
    private  void initRecyclerView() {
        MainActivity activity = mainActivityWeakReference.get();
        Log.d(TAG, "initRecyclerView: initializing RecyclerView");
        RecyclerView recyclerView = activity.findViewById(R.id.recycler_view);
        ContactListAdapter contactList = new ContactListAdapter(context, contactImage, contactName, contactNumber);
        recyclerView.setAdapter(contactList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }
    
    private void writeContactRequestPermission() {
        Log.i(TAG, "ReadContactRequestPermission: Reading permission");
        final MainActivity activity = mainActivityWeakReference.get();
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity , Manifest.permission.READ_CONTACTS)){
            new AlertDialog.Builder(activity)
                    .setTitle("Allow Reading Contact")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.READ_CONTACTS} , READ_CONTACTS);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.READ_CONTACTS}, READ_CONTACTS);
        }

    }
}
