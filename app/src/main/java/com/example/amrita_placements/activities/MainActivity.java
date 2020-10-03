package com.example.amrita_placements.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.amrita_placements.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText reg_num;
    EditText password;
    FirebaseAuth fAuth;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_test);
        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        findViews();
        login = (Button) findViewById(R.id.login_id);

        login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                final String user_reg_number = reg_num.getText().toString();
                final String pass_word = password.getText().toString();
                if (TextUtils.isEmpty(user_reg_number)) {
                    reg_num.setError("Registration number is required");
                    return;
                }
                if (TextUtils.isEmpty(pass_word)) {
                    password.setError("password is empty");
                    return;
                }
               //DocumentReference documentref = FirebaseFirestore.getInstance().collection("students").document(user_reg_number);

                DocumentReference reference = db.collection("students").document(user_reg_number);
                reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                        String got_pass = "not got";
                        got_pass = documentSnapshot.getString("PASSWORD");
                            if(pass_word.equals(got_pass))
                            {
                                A();
                            }
                            else
                            {
                                password.setError("Wrong password");
                            }
                        }
                        else
                        {
                            reg_num.setError("Registration number is invalid");
                        }
                    }
                });
        }
        });
    }
    public void A()
    {
        Intent intent = new Intent(this, verification.class);
        startActivity(intent);
    }

    @SuppressLint("WrongViewCast")
    void findViews()
    {
        reg_num = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }
}
