package com.example.zainrasheed.firestoretoyapp;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
        private static final String KEY_TITLE = "Name";
        private static final String KEY_DESCRIPTION="Description";
        private FirebaseFirestore db = FirebaseFirestore.getInstance();
        private DocumentReference docref = db.collection("User_collection").document("Users");
        EditText t1,t2;
        TextView viewdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1=findViewById(R.id.text1);
        t2=findViewById(R.id.text2);
        viewdata=findViewById(R.id.VIEW);

    }

    public void UploadData(View view) {
        String t11=t1.getText().toString();
        String t22=t2.getText().toString();

        Map<String, Object> post=new HashMap<>();
        post.put(KEY_TITLE,t11);
        post.put(KEY_DESCRIPTION,t22);

        db.collection("User_collection").document("Users").set(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG,e.toString());
                    }
                });

    }

    public void LoadData(final View view) {
        docref.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String title=documentSnapshot.getString(KEY_TITLE);
                            String desc=documentSnapshot.getString(KEY_DESCRIPTION);
                            viewdata.setText("Title:"+title+"\nDescription:"+desc);
                        }else {
                            Toast.makeText(MainActivity.this, "Doc doesn't exisy!", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });



    }
}
