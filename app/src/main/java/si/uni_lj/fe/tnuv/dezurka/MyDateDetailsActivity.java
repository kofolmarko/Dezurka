package si.uni_lj.fe.tnuv.dezurka;

import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyDateDetailsActivity extends AppCompatActivity {

    private ConstraintLayout cancelBtn;
    private ConstraintLayout confirmBtn;
    private TextView tvCancel;
    private TextView tvConfirm;
    private TextView confirmDate;
    private boolean isTradable;
    private DocumentReference ref;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setupHamburgerMenu(this);
        setupToolbar(getResources().getString(R.string.my_dates_btn), this);

        db = FirebaseFirestore.getInstance();

        TextView dateText = findViewById(R.id.text_date);
        TextView timeText = findViewById(R.id.text_time);
        TextView personText = findViewById(R.id.text_person);
        TextView houseText = findViewById(R.id.text_house);
        ConstraintLayout trade = findViewById(R.id.btn_trade);
        TextView tradeBtnText = trade.findViewById(R.id.text);

        Intent intent = getIntent();
        String refPath = intent.getStringExtra(MyDatesActivity.DATEREFERENCE);
        String date = intent.getStringExtra(MyDatesActivity.DETAILSDATE);
        String time = intent.getStringExtra(MyDatesActivity.DETAILSTIME);
        String person = intent.getStringExtra(MyDatesActivity.DETAILSPERSON);
        String home = intent.getStringExtra(MyDatesActivity.DETAILSHOME);

        dateText.setText(date);
        timeText.setText(time);
        personText.setText(person);
        houseText.setText(home);
        ref = db.document(refPath);

        ref.get().addOnSuccessListener(documentSnapshot -> {
            isTradable = (boolean) documentSnapshot.get("is_tradable");
            if(isTradable) {
                tradeBtnText.setText("Odstrani objavo");
            } else {
                tradeBtnText.setText("Menjaj / oddaj");
            }
        });

        trade.setOnClickListener(view -> openDialog(date, ref));
    }

    private void openDialog(String date, DocumentReference ref) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_confirm, null);

        builder.setView(v)
                .setTitle(Html.fromHtml("<font color='#FFFFFF'>Ste prepričani, da želite menjati termin?</font>"));

        cancelBtn = v.findViewById(R.id.btn_cancel);
        confirmBtn = v.findViewById(R.id.btn_confirm);
        confirmDate = v.findViewById(R.id.text_confirm);
        tvCancel = cancelBtn.findViewById(R.id.text);
        tvConfirm = confirmBtn.findViewById(R.id.text);
        tvCancel.setText("Prekliči");
        tvConfirm.setText("Potrdi");
        confirmDate.setText(date);

        AlertDialog dialog = builder.create();
        dialog.show();

        confirmBtn.setOnClickListener(view -> {
            ref.update("is_tradable", !isTradable);
            Intent taskComplete = new Intent(this, TaskCompleteActivity.class);

            if (isTradable) {
                taskComplete.putExtra("first_text", "Objava uspešno odstranjena");
                taskComplete.putExtra("second_text", "Tvoj termin ni več na voljo za menjavo.");
            } else {
                taskComplete.putExtra("first_text", getString(R.string.trade_offer_success));
                taskComplete.putExtra("second_text", getString(R.string.trade_offer_success_2));
            }

            taskComplete.putExtra("third_text", "DOMOV");
            taskComplete.putExtra("fourth_text", "TERMINI");
            startActivity(taskComplete);
            dialog.cancel();
            this.finish();
        });

        cancelBtn.setOnClickListener(view -> {
            dialog.cancel();
        });
    }
}