package com.wildcardenter.myfab.schoolbuslocation.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wildcardenter.myfab.schoolbuslocation.R;
import com.wildcardenter.myfab.schoolbuslocation.adapters.SharedLocationAdapter;
import com.wildcardenter.myfab.schoolbuslocation.models.Location;
import com.wildcardenter.myfab.schoolbuslocation.models.UserDetail;

import java.util.ArrayList;
import java.util.List;

public class ParentLandingActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private List<UserDetail> list;
    SharedLocationAdapter adapter;
    DatabaseReference reference;
    ValueEventListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_landing);
        mAuth=FirebaseAuth.getInstance();
        recyclerView=findViewById(R.id.LocationListRecycler);
        list=new ArrayList<>();
        LinearLayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter=new SharedLocationAdapter(this,list);
        recyclerView.setAdapter(adapter);
        populateRecyclerView();
    }

    private void populateRecyclerView() {
        reference= FirebaseDatabase.getInstance().getReference("Shared_locations");
        listener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Location location=snapshot.getValue(Location.class);
                    list.add(location);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUi();
    }

    private void updateUi() {
        if (mAuth.getCurrentUser() != null) {
            Log.d("Login", "User is Signed in");
        } else {
            Intent intent = new Intent(ParentLandingActivity.this, LogInActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        reference.removeEventListener(listener);
    }
}
