package com.example.practicum3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class set_pointer extends AppCompatActivity {
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference_lat,reference_long;
    EditText Longitude,Latitude;
    ArrayList<String> list;
    ArrayList<Integer> Log_list;
    ArrayList<Integer> Lat_list;
    ArrayAdapter adapter;
    private ListView listView;
    private Context context;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pointer);
        Longitude = (EditText)findViewById(R.id.longitude);
        list = new ArrayList<String>();
        Log_list = new ArrayList<Integer>();
        Lat_list = new ArrayList<Integer>();
        Latitude = (EditText)findViewById(R.id.latitude);
        listView = (ListView)findViewById(R.id.ListView);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference_lat = firebaseDatabase.getReference().child(user.getUid()).child("pointers_lat");
        reference_long = firebaseDatabase.getReference().child(user.getUid()).child("pointers_long");
        list = new ArrayList<String>();
        ArrayList<String> escape_gote = new ArrayList<String>();
        escape_gote.add("Your_Location");
//        adapter = new ArrayAdapter<String>(this,R.layout.custom_layout,escape_gote);
//        listView.setAdapter(adapter);
        check_location();
//        reference_lat.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Lat_list.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    Lat_list.add(snapshot.getValue(Integer.class));
//                }
//                DataChange();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        reference_long.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log_list.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    Log_list.add(snapshot.getValue(Integer.class));
//                }
//                DataChange();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });



    }
    public void DataChange(){
        for (int i=0;i<5;i++){
            list.add(Lat_list.get(i).toString() + " ; " + Log_list.get(i).toString());
        }
        adapter.notifyDataSetChanged();
    }
    public void Enter(View view){
        int longitude = Integer.parseInt(Longitude.getText().toString());
        int latitude = Integer.parseInt(Latitude.getText().toString());
        Log_list.add(longitude);
        Log_list.remove(0);
        Lat_list.add(latitude);
        Lat_list.remove(0);
        reference_long.setValue(Log_list);
        reference_lat.setValue(Lat_list);


    }
    public void MapStart(View view){
        startActivity(new Intent(getApplicationContext(),MapsActivity.class));

    }
    public void check_location(){
        final int[] my_lat = new int[1];
        final int[] my_long = new int[1];
        final int[] f_lat = new int[1];
        final int[] f_long = new int[1];
        final DatabaseReference reference = firebaseDatabase.getReference().child(user.getUid()).child("location");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    my_lat[0] = snapshot.child("my_latitude").getValue(Integer.class);
                    my_long[0] = snapshot.child("my_longitude").getValue(Integer.class);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        final DatabaseReference reference2 = firebaseDatabase.getReference().child("G8sG56hTS9VUd1hdjhSlMZYvXxq2").child("location");
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    f_lat[0] = snapshot.child("my_latitude").getValue(Integer.class);
                    f_long[0] = snapshot.child("my_longitude").getValue(Integer.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        int lat_diff = my_lat[0] - f_lat[0];
        int long_diff = my_long[0] - f_long[0];
        int distance = 4761*(lat_diff*lat_diff) + 4761*(long_diff*long_diff);
        //distance in km approx

        if (distance < 1){
            SimpleNotification();
        }
    }

    private void SimpleNotification() {

        //declare an id for your notification
        //id is used in many things especially when setting action buttons and their intents
        int notificationId = 0;
        //init notification and declare specifications        
         NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                 .setSmallIcon(R.drawable.menu_bg)
                 .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.menu_bg))
                 .setContentTitle("Friend near you")
                 .setContentText("A friend is near 1 km of radius")
                 .setAutoCancel(true)
                 .setDefaults(NotificationCompat.DEFAULT_ALL);
         //set a tone when notification appears
        Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(path);
        //call notification manager so it can build and deliver the notification to the OS
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //Android 8 introduced a new requirement of setting the channelId property by using a NotificationChannel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "YOUR_CHANNEL_ID";
            NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }
        notificationManager.notify(notificationId,builder.build());
    }
}

