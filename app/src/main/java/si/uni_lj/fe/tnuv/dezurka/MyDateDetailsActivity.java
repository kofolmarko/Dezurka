package si.uni_lj.fe.tnuv.dezurka;

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

import java.util.ArrayList;

public class MyDateDetailsActivity extends AppCompatActivity {

    ArrayList<MyDatesActivity.Date> arrayOfTrades = new ArrayList<MyDatesActivity.Date>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView dateText = findViewById(R.id.text_date);
        TextView timeText = findViewById(R.id.text_time);
        TextView personText = findViewById(R.id.text_person);
        TextView houseText = findViewById(R.id.text_house);
        ConstraintLayout trade = findViewById(R.id.btn_trade);
        TextView tradeBtnText = trade.findViewById(R.id.text);

        Intent intent = getIntent();
        String date = intent.getStringExtra(MyDatesActivity.DETAILSDATE);
        String time = intent.getStringExtra(MyDatesActivity.DETAILSTIME);
        String person = intent.getStringExtra(MyDatesActivity.DETAILSPERSON);
        String home = intent.getStringExtra(MyDatesActivity.DETAILSHOME);

        dateText.setText(date);
        timeText.setText(time);
        personText.setText(person);
        houseText.setText(home);
        tradeBtnText.setText("Menjaj / oddaj");

        trade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment tradeDialog = new TradeDialog();
                tradeDialog.show(getSupportFragmentManager(), "Menjava termina");
            }
        });
    }

    public static class TradeDialog extends DialogFragment {
        ArrayList<MyDatesActivity.Date> arrayOfTrades = new ArrayList<MyDatesActivity.Date>();

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            TradeAdapter adapter = new TradeAdapter(getActivity(), arrayOfTrades);
            MyDatesActivity.Date newDate4 = new MyDatesActivity.Date("26.12.2022", "12h - 18h", "Jurij Sokol", "Dom 10, Rožna Dolina");
            MyDatesActivity.Date newDate5 = new MyDatesActivity.Date("26.12.2022", "12h - 18h", "Jurij Sokol", "Dom 5, Rožna Dolina");
            MyDatesActivity.Date newDate6 = new MyDatesActivity.Date("26.12.2022", "12h - 18h", "Jurij Sokol", "Dom 7, Rožna Dolina");
            MyDatesActivity.Date newDate7 = new MyDatesActivity.Date("26.12.2022", "12h - 18h", "Jurij Sokol", "Dom 7, Rožna Dolina");

            adapter.add(newDate4);
            adapter.add(newDate5);
            adapter.add(newDate6);
            adapter.add(newDate7);

            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .setTitle(Html.fromHtml("<font color='#FFFFFF'>Objavi ponudbo za</font>"))
                    .setNegativeButton("Prekliči", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });

            return builder.create();
        }
    }

    public static class TradeAdapter extends ArrayAdapter<MyDatesActivity.Date> {
        public TradeAdapter(Context context, ArrayList<MyDatesActivity.Date> dates) {
            super(context, 0, dates);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyDatesActivity.Date date = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_trade, parent, false);
            }

            TextView tvDate = convertView.findViewById(R.id.trade_date);
            TextView tvTime = convertView.findViewById(R.id.trade_time);
            TextView tvHome = convertView.findViewById(R.id.trade_home);

            tvDate.setText(date.date);
            tvTime.setText(date.time);
            tvHome.setText(date.home);

            return convertView;
        }
    }

}