package si.uni_lj.fe.tnuv.dezurka;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        registerBtn = findViewById(R.id.register_btn);
    }

    @Override
    public void onStart() {
        super.onStart();

        registerBtn.setOnClickListener(v -> {
            EditText username = findViewById(R.id.username_input);
            EditText email = findViewById(R.id.email_input);
            EditText password = findViewById(R.id.password_input);
            EditText passwordConfirm = findViewById(R.id.confirm_password_input);
            if (username == null || username.length() == 0 ||
                email == null || email.length() == 0 ||
                password == null || password.length() == 0 ||
                passwordConfirm == null || passwordConfirm.length() == 0
            ) return;
            createAccount(
                    username.getText().toString(),
                    email.getText().toString(),
                    password.getText().toString(),
                    passwordConfirm.getText().toString()
            );
        });
    }

    public void createAccount(String username, String email, String password, String passwordConfirm) {
        if (!password.equals(passwordConfirm)) {
            Toast.makeText(RegisterActivity.this,
                    getResources().getString(R.string.passwords_do_not_match_msg),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                                .build();

                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        Map<String, Object> userData = new HashMap<>();
                        userData.put("campus", "Rožna Dolina");
                        userData.put("dorm", "Dom " + ((int) (Math.random()*13 + 1)));
                        userData.put("room", "" + ((int) (Math.random()*900+1)));
                        userData.put("full_name", username);
                        userData.put("owned_dates", Arrays.asList());


                        db.collection("users")
                                .add(userData)
                                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));

                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this,
                                                getResources().getString(R.string.register_successful_msg),
                                                Toast.LENGTH_SHORT).show();
                                        loadDashboard();
                                    }
                                });
                    } else {
                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                        //updateUI(null);
                    }
                });
    }

    private void loadDashboard() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
}