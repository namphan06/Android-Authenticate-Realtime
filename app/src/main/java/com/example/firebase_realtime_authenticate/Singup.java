package com.example.firebase_realtime_authenticate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Singup extends AppCompatActivity {
    EditText edt1,edt2,edt3,edt4;
    Button btn;
    TextView txt;
    FirebaseDatabase data;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_singup);
        edt1 = findViewById(R.id.name);
        edt2 = findViewById(R.id.email);
        edt3 = findViewById(R.id.username);
        edt4 = findViewById(R.id.password);
        btn = findViewById(R.id.btnSingup);
        txt = findViewById(R.id.txtLogin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data = FirebaseDatabase.getInstance();
                reference = data.getReference("users");
                String name = edt1.getText().toString();
                String email = edt2.getText().toString();
                String username = edt3.getText().toString();
                String password = edt4.getText().toString();
                Users user = new Users(name,email,username,password);
                reference.child(name).setValue(user);
                Toast.makeText(Singup.this, "Sing up successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Singup.this, Login.class);
                startActivity(intent);

            }
        });
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Singup.this, Login.class);
                startActivity(intent);
            }
        });

    }

}