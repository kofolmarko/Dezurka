package si.uni_lj.fe.tnuv.dezurka;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button registerBtn;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        registerBtn = findViewById(R.id.register_btn);
        loginBtn = findViewById(R.id.login_btn);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //reload();
            Toast.makeText(LoginActivity.this,
                    getResources().getString(R.string.signIn_successful_msg) + currentUser.getEmail(),
                    Toast.LENGTH_SHORT).show();
        }

        registerBtn.setOnClickListener(v -> {
            EditText email = findViewById(R.id.email_input);
            EditText password = findViewById(R.id.password_input);
            if (email == null || email.length() == 0 || password == null || password.length() == 0) return;
            createAccount(
                    email.getText().toString(),
                    password.getText().toString()
            );
        });
        loginBtn.setOnClickListener(v -> {
            EditText email = findViewById(R.id.email_input);
            EditText password = findViewById(R.id.password_input);
            if (email == null || email.length() == 0 || password == null || password.length() == 0) return;
            signIn(
                    email.getText().toString(),
                    password.getText().toString()
            );
        });
    }

    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        //updateUI(user);
                        Toast.makeText(LoginActivity.this,
                                getResources().getString(R.string.register_successful_msg),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                        //updateUI(null);
                    }
                });
    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        // updateUI(user);
                        Toast.makeText(LoginActivity.this,
                                getResources().getString(R.string.signIn_successful_msg) + user.getEmail(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                        //updateUI(null);
                    }
                });
    }
}