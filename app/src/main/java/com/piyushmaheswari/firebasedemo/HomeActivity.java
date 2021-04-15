package com.piyushmaheswari.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    private Button logout, addValue;
    private EditText addValueText;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logout = findViewById(R.id.logOut);
        addValue = findViewById(R.id.add);
        addValueText = findViewById(R.id.value);
        listView = findViewById(R.id.listView);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(HomeActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = addValueText.getText().toString();

                if (text.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "No Value", Toast.LENGTH_SHORT).show();
                } else {
//                    FirebaseDatabase.getInstance().getReference().child("Piyush").push().child("Name").setValue(text);
                    FirebaseDatabase.getInstance().getReference().child("Languages").child("Name").setValue(text);
                }
            }
        });

        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.list_item, list);
        listView.setAdapter(arrayAdapter);


//        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Languages");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear();
//                for(DataSnapshot dataSnapshot:snapshot.getChildren())
//                {
//                    list.add(dataSnapshot.getValue().toString());
//                }
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("info");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Info info = dataSnapshot.getValue(Info.class);
                    String text = info.getName() + " : " + info.getEmail();
                    list.add(text);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        HashMap<String, Object> map=new HashMap<>();
//        map.put("Piyush", "Maheswari");
//        map.put("Piyush2", "Maheswari2");
//
//        FirebaseDatabase.getInstance().getReference().child("Piyush").child("MultipleValues").updateChildren(map);
//        FirebaseDatabase.getInstance().getReference().child("Piyush").child("Android").setValue("Java");


        /**
         * Add Data in map
         * */
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        HashMap<String, Object> map = new HashMap<>();
        map.put("Piyush", "Maheswari");
        map.put("Piyush2", "Maheswari2");

        firebaseFirestore.collection("maps").document("names").set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(HomeActivity.this, "Values Added", Toast.LENGTH_SHORT).show();
                }
            }
        });


        /**
         * Merge Data in map
         * */
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("capital", false);
//
//        firebaseFirestore.collection("maps").document("names").set(map, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful())
//                {
//                    Toast.makeText(HomeActivity.this, "Merge Successful", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        /**
         * Add Data with random id
         * */
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("name", "Japan");
//        map.put("capital", "Tokyo");
//
//
//        firebaseFirestore.collection("maps").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentReference> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(HomeActivity.this, "Added Data Successful", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

        /**
         * Update data in db
         * */
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("maps").document("names");
//        documentReference.update("capital", true);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        Toast.makeText(HomeActivity.this, snapshot.getData().toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(HomeActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




    }
}