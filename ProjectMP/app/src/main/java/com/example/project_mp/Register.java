package com.example.project_mp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    //used to be AppCompactActivity
    private static final String TAG = "Register";
    EditText username,password,Cpassword;
    Button BTNregister,BTNswitch;
    FirebaseAuth FBauth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.ETusernameRgs);
        password = findViewById(R.id.ETpasswordRgs);
        Cpassword = findViewById(R.id.ETconfirmpasswordRgs);
        BTNregister = findViewById(R.id.BTNregister);
        BTNswitch= findViewById(R.id.BTNloginRgs);

        FBauth = FirebaseAuth.getInstance();

        if(FBauth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        BTNregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String StrUsername = username.getText().toString().trim();
                String StrPassword = password.getText().toString().trim();
                String StrCPassword = Cpassword.getText().toString().trim();

                if(StrUsername.isEmpty()){
                    return;
                }
                if(StrPassword.isEmpty()){
                    return;
                }
                if(!StrCPassword.equals(StrPassword)){
                    Toast.makeText(Register.this,"confirm password is different",Toast.LENGTH_SHORT).show();
                    return;
                }

                FBauth.createUserWithEmailAndPassword(StrUsername,StrPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"USER CREATED",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            Toast.makeText(Register.this, "Error !"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        BTNswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });

    }
}
