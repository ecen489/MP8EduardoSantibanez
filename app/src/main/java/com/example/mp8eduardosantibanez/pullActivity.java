package com.example.mp8eduardosantibanez;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class pullActivity extends AppCompatActivity {
    private static final String TAG = pullActivity.class.getSimpleName();

    ArrayList<Grade> contacts = new ArrayList<Grade>();
    MyAdapter adapter;
    RecyclerView rvContacts;
    EditText IDText;
    FirebaseDatabase fbdb;
    DatabaseReference dbrf;
    FirebaseAuth mAuth;
    FirebaseUser user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);
        mAuth = FirebaseAuth.getInstance();
        fbdb = FirebaseDatabase.getInstance();
        dbrf = fbdb.getReference();
        IDText = findViewById(R.id.idQuery);

        // Initialize contacts
        // Lookup the recyclerview in activity layout
        rvContacts = (RecyclerView) findViewById(R.id.recycler);
        contacts.add(new Grade(1," "," ",2));
        // Create adapter passing in the sample user data
        adapter = new MyAdapter(contacts);

        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        user = mAuth.getCurrentUser();
    }

    public void LogoutClick(View view) {
        System.out.println(mAuth.getCurrentUser().getEmail().toString());
        Log.i(TAG, "User Logged out Successfully");
        mAuth.signOut();
        user = null;
        Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToPush(View view) {
        Intent pushIntent = new Intent(this, pushActivity.class);
        startActivity(pushIntent);
    }

    public void gradeClick(View view) {

        if (user != null) {

            int studID = Integer.parseInt(IDText.getText().toString());

            DatabaseReference gradeKey = dbrf.child("simpsons/grades/");

            Query query = gradeKey.orderByChild("student_id").equalTo(studID);
            query.addListenerForSingleValueEvent(valueEventListener);

        } else {

            Toast.makeText(getApplicationContext(), "Please login", Toast.LENGTH_SHORT).show();
        }

    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                //Toast.makeText(getApplicationContext(),"listening",Toast.LENGTH_SHORT).show();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.v("fb",snapshot.getValue().toString());
                    Grade grade = snapshot.getValue(Grade.class);
                    contacts.add(grade);
                    int curSize = adapter.getItemCount();


                    adapter.notifyItemRangeInserted(curSize, contacts.size());
                }

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    };
    public void courseClick(View view) {

        if (user != null) {

            int studID = Integer.parseInt(IDText.getText().toString());

            DatabaseReference gradeKey = dbrf.child("simpsons/students/");

            Query query = gradeKey.orderByChild("id").startAt(studID);
            query.addListenerForSingleValueEvent(valueEventListener1);

        } else {

            Toast.makeText(getApplicationContext(), "Please login", Toast.LENGTH_SHORT).show();
        }

    }
    ValueEventListener valueEventListener1 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                //Toast.makeText(getApplicationContext(),"listening",Toast.LENGTH_SHORT).show();
                for (DataSnapshot courseO : dataSnapshot.getChildren()) {
                    Log.v("fb",courseO.getValue().toString());
                    String name=courseO.child("course_name").getValue().toString();
                    String course=courseO.child("course_name").getValue().toString();
                    int cr=Integer.parseInt(course);
                    contacts.add(new Grade(cr,name,"",2));
                    int curSize = adapter.getItemCount();

                    adapter.notifyItemRangeInserted(curSize, contacts.size());
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    };
}
