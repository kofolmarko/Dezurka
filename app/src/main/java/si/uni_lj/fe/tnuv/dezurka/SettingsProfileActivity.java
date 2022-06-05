package si.uni_lj.fe.tnuv.dezurka;

import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SettingsProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    ConstraintLayout logoutBtn;
    ConstraintLayout myStudentBtn;
    TextView name;
    TextView room;
    TextView location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_profile);

        setupHamburgerMenu(this);
        setupToolbar(getString(R.string.settings_text_3), this);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        logoutBtn = findViewById(R.id.btn_logout);
        myStudentBtn = findViewById(R.id.btn_mojstudent);
        name = findViewById(R.id.name_text);
        room = findViewById(R.id.room_text);
        location = findViewById(R.id.location_text);

        setText();
        setOnClickListeners();
    }

    private void setText() {
        TextView logoutBtnText = logoutBtn.findViewById(R.id.text);
        TextView myStudentBtnText = myStudentBtn.findViewById(R.id.text);

        logoutBtnText.setText(R.string.logout_text);
        myStudentBtnText.setText(R.string.mystudent_text);

        DocumentReference currentUserData = db.collection("users").document(mAuth.getCurrentUser().getUid());
        currentUserData.get().addOnSuccessListener(documentSnapshot -> {
            name.setText((String)documentSnapshot.get("full_name"));
            room.setText(documentSnapshot.get("dorm") + ", Soba " + documentSnapshot.get("room"));
            location.setText((String)documentSnapshot.get("campus"));
        });
    }

    private void setOnClickListeners() {
        logoutBtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, SignInActivity.class));
        });

        myStudentBtn.setOnClickListener(view -> {
            String url = "https://student.sd-lj.si/login";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });
    }
}