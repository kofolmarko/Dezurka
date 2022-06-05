package si.uni_lj.fe.tnuv.dezurka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class TaskCompleteActivity extends AppCompatActivity {

    private TextView firstText;
    private TextView secondText;
    private ConstraintLayout firstBtn;
    private ConstraintLayout secondBtn;
    private TextView firstBtnText;
    private TextView secondBtnText;

    private String firstTextExtra;
    private String secondTextExtra;
    private String thirdTextExtra;
    private String fourthTextExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_complete);

        firstText = findViewById(R.id.first_text);
        secondText = findViewById(R.id.second_text);
        firstBtn = findViewById(R.id.first_btn);
        secondBtn = findViewById(R.id.second_btn);
        firstBtnText = firstBtn.findViewById(R.id.text);
        secondBtnText = secondBtn.findViewById(R.id.text);

        Intent intent = getIntent();
        firstTextExtra = intent.getStringExtra("first_text");
        secondTextExtra = intent.getStringExtra("second_text");
        thirdTextExtra = intent.getStringExtra("third_text");
        fourthTextExtra = intent.getStringExtra("fourth_text");

        setText();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        firstBtn.setOnClickListener(view -> {
            if (thirdTextExtra.equals("DOMOV")) {
                startActivity(new Intent(this, DashboardActivity.class));
            }
            this.finish();
        });

        secondBtn.setOnClickListener(view -> {
            if (fourthTextExtra.equals("TERMINI")) {
                startActivity(new Intent(this, MyDatesActivity.class));
            } else if (fourthTextExtra.equals("MENJAVE")) {
                startActivity(new Intent(this, TradesActivity.class));
            }
            this.finish();
        });
    }

    private void setText() {
        firstText.setText(firstTextExtra);
        secondText.setText(secondTextExtra);
        firstBtnText.setText(thirdTextExtra);
        secondBtnText.setText(fourthTextExtra);
    }
}