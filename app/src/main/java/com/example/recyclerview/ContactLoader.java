package com.example.recyclerview;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.lang.ref.WeakReference;


public class ContactLoader extends AsyncTask <Void, Void , String>{
     private static final String TAG = "ContactLoader";

     private WeakReference<MainActivity> activityWeakReference;

     ContactLoader(MainActivity activity){
         Log.d(TAG, "ContactLoader: Thread Started");
         activityWeakReference = new WeakReference<MainActivity>(activity);
     }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "onPreExecute: execution");
        super.onPreExecute();
        MainActivity activity = activityWeakReference.get();
        if(activity == null || activity.isFinishing()){
            Log.d(TAG, "onPreExecute: execution failed");
            return;
        }
        Toast.makeText(activity, "OnPreExecute" , Toast.LENGTH_LONG).show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        Log.d(TAG, "doInBackground: thread in background");
        for (int i = 0; i < 10 ;i++){
            publishProgress();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        return "Finished!";
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        Log.d(TAG, "onProgressUpdate: progess updated");
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: Thread completed");
        super.onPostExecute(s);
        MainActivity activity = activityWeakReference.get();
        Toast.makeText(activity, "Task finished" , Toast.LENGTH_LONG).show();
    }
}
