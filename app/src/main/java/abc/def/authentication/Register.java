package abc.def.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText u3,u4,u5;
    Button t5;
    FirebaseAuth Fauth;
    FirebaseFirestore fb;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        u3 = findViewById(R.id.u3);
        u4 = findViewById(R.id.u4);
        u5 = findViewById(R.id.u5);
        t5 = findViewById(R.id.t5);
        Fauth = FirebaseAuth.getInstance();
        fb = FirebaseFirestore.getInstance();


        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = u3.getText().toString().trim();
                String email = u4.getText().toString().trim();
                String password = u5.getText().toString().trim();

                Fauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser fireuser = Fauth.getCurrentUser();
                            fireuser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {





                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Register.this, "Email send", Toast.LENGTH_SHORT).show();


//
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Register.this, "Verification Failure", Toast.LENGTH_SHORT).show();
                                }



                            });
                            Toast.makeText(Register.this, "Success", Toast.LENGTH_SHORT).show();

                            userId = Fauth.getCurrentUser().getUid();
                                    DocumentReference documentReference = fb.collection("user").document(userId);
                                    Map<String,Object> user = new HashMap<>();
                                    user.put("Username",name);
                                    user.put("Email",email);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(Register.this, "User profile is created", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                            Intent intent = new Intent(Register.this,MainActivity.class);
                            startActivity(intent);

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, " Failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}