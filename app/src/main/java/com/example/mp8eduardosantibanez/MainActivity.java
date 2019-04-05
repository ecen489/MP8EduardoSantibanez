package com.example.mp8eduardosantibanez;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button createButton;
    Button loginButton;
    EditText emailText;
    EditText passwordText;
    FirebaseDatabase fbdb;
    DatabaseReference dbrf;
    FirebaseAuth mAuth;
    FirebaseUser user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createButton=findViewById(R.id.createAct);
        loginButton=findViewById(R.id.login);
        emailText=findViewById(R.id.email);
        passwordText=findViewById(R.id.password);
        fbdb = FirebaseDatabase.getInstance();
        dbrf = fbdb.getReference();
        mAuth = FirebaseAuth.getInstance();
    }
    public void CreateClick(View view) {

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(getApplicationContext(),"Created Account",Toast.LENGTH_SHORT).show();
                            user = mAuth.getCurrentUser(); //The newly created user is already signed in
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void LoginClick(View view) {

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_SHORT).show();
                            user = mAuth.getCurrentUser(); //The user is signed in
                        } else {
                            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        Intent intent=new Intent(getApplicationContext(),pullActivity.class);
        startActivity(intent);


    }

}
