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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText r1,r2;
    Button u1,u2;
    FirebaseAuth Fauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        r1 = findViewById(R.id.r1);
        r2 = findViewById(R.id.r2);
        u1 = findViewById(R.id.u1);
        u2=findViewById(R.id.u2);
        Fauth = FirebaseAuth.getInstance();

        u2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);
            }
        });
        u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = r1.getText().toString().trim();
                String pass = r2.getText().toString().trim();

                Fauth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,Homepage.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//                Intent intent = new Intent(MainActivity.this,Register.class);
//                startActivity(intent);
            }
        });
    }
}