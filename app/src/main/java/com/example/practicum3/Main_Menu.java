package com.example.practicum3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main_Menu extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayList<Double> Latitude_list;
    ArrayList<Double> Longitude_list;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            if ( grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 20, locationListener);
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);
        //getting user location
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

//        myRef.child("19214").child("Location").setValue("trial 1");
//        myRef.child("19214").child("Pointer 1").setValue("123");
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                HelperClass helperClass =  new HelperClass(location.getLatitude(),location.getLongitude());
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String roll_no = user.getUid();
                Log.i("user display name",roll_no);
                myRef.child(roll_no).child("location").setValue(helperClass);

//               // condition that pointers set via users are not overridden;
//                if (true) {
//                    for (int i = 0; i < 5; i++) {
//                        Latitude_list.add(helperClass.getMy_latitude());
//                        Longitude_list.add(helperClass.getMy_longitude());
//                    }
//                    myRef.child(user.getUid()).child("pointers_lat").setValue(Latitude_list);
//                    myRef.child(user.getUid()).child("pointers_long").setValue(Longitude_list);
//                }
                //Log.i("location",location.toString()); good
                //myRef.child("Roll_no").setValue(location.toString()); good

            }
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        } else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,20,locationListener);

        }
        ///----------*------
    }


    public void Friends_Available(View view){
        startActivity(new Intent(getApplicationContext(),Friends_Available.class));
    }
    public void Your_Info(View view){
        startActivity(new Intent(getApplicationContext(),Your_info.class));
    }
    public void Send_Request(View view){
        startActivity(new Intent(getApplicationContext(),Friends_Available.class));
    }
    public void Friend_Schedule(View view){
        Toast.makeText(this, "This feature is not available", Toast.LENGTH_SHORT).show();
    }
    public void Set_pointer(View view){
        startActivity(new Intent(getApplicationContext(),set_pointer.class));
    }
    public void LogOut(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}