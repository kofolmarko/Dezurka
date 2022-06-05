package si.uni_lj.fe.tnuv.dezurka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ConstraintLayout registerBtn;
    private ConstraintLayout loginBtn;
    private TextView registerTv;
    private TextView loginTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        registerBtn = findViewById(R.id.register_btn);
        loginBtn = findViewById(R.id.login_btn);

        registerTv = registerBtn.findViewById(R.id.text);
        loginTv = loginBtn.findViewById(R.id.text);
        registerTv.setText("Registracija");
        loginTv.setText("Prijava");
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            loadDashboard();
            Toast.makeText(SignInActivity.this,
                    getResources().getString(R.string.signIn_successful_msg) + currentUser.getEmail(),
                    Toast.LENGTH_SHORT).show();
        }

        registerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
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

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(SignInActivity.this,
                                getResources().getString(R.string.signIn_successful_msg) + user.getEmail(),
                                Toast.LENGTH_SHORT).show();
                        loadDashboard();
                    } else {
                        Toast.makeText(SignInActivity.this, task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadDashboard() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
}