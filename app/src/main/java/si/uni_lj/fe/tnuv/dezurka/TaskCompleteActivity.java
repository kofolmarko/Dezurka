package si.uni_lj.fe.tnuv.dezurka;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class TaskCompleteActivity extends AppCompatActivity {

    private TextView firstText;
    private TextView secondText;
    private Button firstBtn;
    private Button secondBtn;
    private TextView firstBtnText;
    private TextView secondBtnText;

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

        setText();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        // set onclick listeners accordingly
    }

    private void setText() {
        // set text accordingly
    }
}