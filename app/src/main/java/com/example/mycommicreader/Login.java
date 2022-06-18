package com.example.mycommicreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycommicreader.databinding.ActivityLoginBinding;
import com.example.mycommicreader.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            binding = ActivityLoginBinding.inflate(getLayoutInflater());
            View view = binding.getRoot();
            setContentView(view);
            mAuth = FirebaseAuth.getInstance();
            firestore = FirebaseFirestore.getInstance();
            binding.login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DEBUG", "signInWithEmail:success");
                    String em = binding.email.getText().toString();
                    String pa = binding.password.getText().toString();
                    login(em, pa);
//                Navigation.findNavController(view).navigate(R.id.list);
                }
            });
        } catch (Exception e) {
            Log.d("DEBUG", e.getMessage());
        }
    }
    private void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("DEBUG", "signInWithEmail:success");
                            Toast.makeText(Login.this, "Authentication successful.",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent();
                            i.putExtra("userID",user.getUid());
                            setResult(RESULT_OK,i);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("DEBUG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}