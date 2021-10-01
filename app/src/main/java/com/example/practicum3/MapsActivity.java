package com.example.practicum3;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    final double[] my_lat = new double[1];
    final double[] my_long = new double[1];
    final double[] f_lat = new double[1];
    final double[] f_long = new double[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase =FirebaseDatabase.getInstance();
        check_location();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        double i = -34,j= 151;
        sydney = new LatLng(f_lat[0],f_long[0]);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Friend"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    /**
     *
    */

    public void check_location(){

        final DatabaseReference reference = firebaseDatabase.getReference().child(user.getUid()).child("location");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    my_lat[0] = snapshot.child("my_latitude").getValue(Double.class);
                    my_long[0] = snapshot.child("my_longitude").getValue(Double.class);

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
                    f_lat[0] = snapshot.child("my_latitude").getValue(Double.class);
                    f_long[0] = snapshot.child("my_longitude").getValue(Double.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

    }
}