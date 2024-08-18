package com.example.firebase_realtime_authenticate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {
    EditText edt1,edt2;
    Button btn;
    TextView txt;
    FirebaseDatabase data;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        edt1 = findViewById(R.id.username);
        edt2 = findViewById(R.id.password);
        btn = findViewById(R.id.btnLogin);
        txt = findViewById(R.id.txtRegister);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateUsername() || !validatePassword()){

                }
                else{
                    checkUser();
                }
            }
        });
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Singup.class);
                startActivity(intent);
            }
        });


    }
    public Boolean validateUsername(){
        String val = edt1.getText().toString();
        if(val.isEmpty()){
            edt1.setError("User name cannot be enter");
            return false;
        }
        else{
            edt1.setError(null);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val = edt2.getText().toString();
        if(val.isEmpty()){
            edt2.setError("Password cannot be enter");
            return false;
        }
        else{
            edt2.setError(null);
            return true;
        }
    }
    public void checkUser(){
        String username = edt1.getText().toString();
        String password = edt2.getText().toString();

        data = FirebaseDatabase.getInstance();
        reference = data.getReference("users");

        Query query = reference.orderByChild("username").equalTo(username);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    edt1.setError(null);
                    String passwordFromDB = snapshot.child(username).child("password").getValue(String.class);
                    if(Objects.equals(passwordFromDB,password)){
                        edt1.setError(null);
                        Intent intent = new Intent(Login.this,MainActivity.class);
                        startActivity(intent);
                    }else{
                        edt2.setError("Invalid Credential");
                        edt2.requestFocus();
                    }
                }else{
                    edt1.setError("User does not exist");
                    edt1.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}