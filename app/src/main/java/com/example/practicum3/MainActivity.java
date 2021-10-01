package com.example.practicum3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth fAuth;
    //private FirebaseDatabase database;
    //LocationManager locationManager;
    //LocationListener locationListener;
    //HelperClass helperClass;
   // private DatabaseReference myRef;
    private String email;
    private EditText roll_no,password;
    //String s;
    public void tryal (View view){
        startActivity(new Intent(getApplicationContext(), Main_Menu.class));
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //database = FirebaseDatabase.getInstance();
//        Location location = new Location(123,456);
        //myRef = database.getReference("User");
        roll_no = (EditText)findViewById(R.id.Roll_no);
        password = (EditText)findViewById(R.id.Password);
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null ){
            Intent i = new Intent(getApplicationContext(),Main_Menu.class);
            //i.putExtra("RollNo",roll_no.getText().toString());
            startActivity(i);
            finish();
        }

    }


public void Register(View view){
    email = roll_no.getText().toString().concat("@iiitu.ac.in");

    fAuth.createUserWithEmailAndPassword(email,password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Sign in new user
                           // myRef.child(roll_no.getText().toString()).setValue("");
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = firebaseDatabase.getReference();
                            FirebaseUser user = fAuth.getCurrentUser();
                            myRef.child(roll_no.getText().toString()).setValue(user.getUid());

                            Log.i("Registeration Result","createUserWithEmail:SuccessFull");
                            SharedPreferences sharedPreferences = getSharedPreferences("RollNo",0);
                            final SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("id",roll_no.getText().toString());
                            ArrayList<Double> Latitude_list = new ArrayList<Double>();
                            ArrayList<Double> Longitude_list = new ArrayList<Double>();
//                            for (int i=0;i<5;i++) {
//                                Latitude_list.add(0d);
//                                Longitude_list.add(0d);
//                            }
//                HashMap<String,Double> map = new HashMap<String, Double>();
//                map.put("p1_lat",0d);
//                map.put("p2_lat",0d);
//                map.put("p3_lat",0d);
//                map.put("p4_lat",0d);
//                map.put("p5_lat",0d);
//                HashMap<String,Double> map2 = new HashMap<String, Double>();
//                map2.put("p1_lon",0d);
//                map2.put("p2_lon",0d);
//                map2.put("p3_lon",0d);
//                map2.put("p4_lon",0d);
//                map2.put("p5_lon",0d);


//                            myRef.child(user.getUid()).child("pointers_lat").setValue(Latitude_list);
//                            myRef.child(user.getUid()).child("pointers_long").setValue(Longitude_list);


//                            FirebaseUser user = fAuth.getCurrentUser();
//                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//                            databaseReference.child(roll_no.getText().toString()).child("password").setValue(password.getText().toString());
                            Intent i = new Intent(getApplicationContext(),Main_Menu.class);
                            i.putExtra("RollNo",roll_no.getText().toString());
                            finish();
                        }
                        else{
                            // Registeration failed
                            Toast.makeText(MainActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                        }

                    }
                });




}
public void LogIn(View view){
    email = roll_no.getText().toString().concat("@iiitu.ac.in");
    Log.i("email.String",email);
    Log.i("password",password.getText().toString());
        fAuth.signInWithEmailAndPassword(email,password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = fAuth.getCurrentUser();
                            Intent i = new Intent(getApplicationContext(),Main_Menu.class);
                            i.putExtra("RollNo",roll_no.getText().toString());
                            startActivity(i);
                            finish();
                        }
                    }
                });


}



}