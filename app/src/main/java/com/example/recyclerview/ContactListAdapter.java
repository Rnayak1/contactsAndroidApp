package com.example.recyclerview;
import com.example.recyclerview.R;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {
    private static final String TAG = "ContactListAdapter";
    private ArrayList <String> contactName = new ArrayList<>();
    private  ArrayList<String> contactNubmer = new ArrayList<>();
    private ArrayList<String> contactImage = new ArrayList<>();
    private Context ctx;
    public ContactListAdapter(Context ct, ArrayList<String> cImage, ArrayList<String> cName, ArrayList<String> cNumber){
        Log.d(TAG, "ContactListAdapter: context"+ct);
        ctx = ct;
        contactImage = cImage;
        contactName = cName;
        contactNubmer = cNumber;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Creating holder");
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts , parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: creating contact");
        holder.contactImage.setText(contactImage.get(position));
        holder.contactNumber.setText(contactNubmer.get(position));
        holder.contactName.setText(contactName.get(position));
        holder.contactParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on :"+ contactImage.get(position) + " at position :"+ position);
                Toast.makeText(ctx , contactName.get(position), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "ViewHolder";
        RelativeLayout contactParent;
        TextView contactImage,contactName,contactNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder: creating viewHolder");

            contactParent = itemView.findViewById(R.id.contactParent);
            contactImage = itemView.findViewById(R.id.contactImage);
            contactName = itemView.findViewById(R.id.contactName);
            contactNumber = itemView.findViewById(R.id.contactNumber);
        }
    }
}
