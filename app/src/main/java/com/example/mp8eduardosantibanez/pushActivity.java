package com.example.mp8eduardosantibanez;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class pushActivity extends AppCompatActivity {
    FirebaseDatabase fbdb;
    DatabaseReference dbrf;
    int radioID = R.id.ralph;
    int dbID = 404;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        fbdb = FirebaseDatabase.getInstance();
        dbrf = fbdb.getReference();
    }

    public void radioClick(View view) {
        radioID = view.getId();
        if(radioID==R.id.bart){dbID = 123;}
        if(radioID==R.id.ralph){dbID = 404;}
        if(radioID==R.id.milhouse){dbID = 456;}
        if(radioID==R.id.lisa){dbID = 888;}
    }


    public void pushtofb(View view) {

        EditText edTxt= findViewById(R.id.id);
        String id = edTxt.getText().toString();
        int id1=Integer.parseInt(id);
        edTxt.getText().clear();

        EditText edTxt1= findViewById(R.id.name);
        String nam = edTxt1.getText().toString();
        edTxt1.getText().clear();

        EditText edTxt2= findViewById(R.id.grade);
        String grad = edTxt2.getText().toString();
        edTxt2.getText().clear();


        DatabaseReference passID = dbrf.child("simpsons/grades/");
        DatabaseReference newGrade=passID.push();
        newGrade.child("student_id").setValue(dbID);
        newGrade.child("course_id").setValue(id1);
        newGrade.child("course_name").setValue(nam);
        newGrade.child("grade").setValue(grad);

        Intent pushIntent = new Intent(this, pullActivity.class);
        startActivity(pushIntent);
    }
}