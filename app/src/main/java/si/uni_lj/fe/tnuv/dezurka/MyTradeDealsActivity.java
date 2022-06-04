package si.uni_lj.fe.tnuv.dezurka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyTradeDealsActivity extends AppCompatActivity {

    private ListView tradeDealsList;

    private ProgressBar progressBar;

    private ConstraintLayout btnCancel;
    private ConstraintLayout btnConfirm;
    private TextView tvCancel;
    private TextView tvConfirm;
    private TextView tvDialog;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<DateTrade> arrayOfTradeDeals = new ArrayList<DateTrade>();

    public static class DateTrade {
        public String date;
        public String time;
        public String person;
        public String home;
        public DocumentReference ref;
        public DocumentReference owner;
        public String dateOffer;
        public String timeOffer;
        public String personOffer;
        public String homeOffer;
        public DocumentReference refOffer;
        public DocumentReference ownerOffer;

        public DateTrade(String date, String time, String person, String home, DocumentReference ref, DocumentReference owner, String dateOffer, String timeOffer, String personOffer, String homeOffer, DocumentReference refOffer, DocumentReference ownerOffer) {
            this.date = date;
            this.time = time;
            this.person = person;
            this.home = home;
            this.owner = owner;
            this.ref = ref;
            this.dateOffer = dateOffer;
            this.timeOffer = timeOffer;
            this.personOffer = personOffer;
            this.homeOffer = homeOffer;
            this.ownerOffer = ownerOffer;
            this.refOffer = refOffer;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trade_deals);

        setupHamburgerMenu(this);
        setupToolbar("Aktivne ponudbe", this);

        progressBar = findViewById(R.id.progress_bar);
        tradeDealsList = findViewById(R.id.available_trade_deals_list);
        showTradeDeals();

        tradeDealsList.setOnItemClickListener(((adapterView, view, i, l) -> {
                confirmDialog(i);
        }));
    }

    private void confirmDialog(int selectedIndex) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_confirm, null);

        builder.setView(v)
                .setTitle(Html.fromHtml("<font color='#FFFFFF'>Ste prepričani da želite sprejeti ponudbo?</font>"));

        AlertDialog dialog = builder.create();
        dialog.show();

        DateTrade selectedDate = arrayOfTradeDeals.get(selectedIndex);

        tvDialog = v.findViewById(R.id.text_confirm);
        tvDialog.setText("Menjava termina " + selectedDate.date + " za " + selectedDate.dateOffer);

        btnCancel = v.findViewById(R.id.btn_cancel);
        btnConfirm = v.findViewById(R.id.btn_confirm);
        tvCancel = btnCancel.findViewById(R.id.text);
        tvConfirm = btnConfirm.findViewById(R.id.text);
        tvCancel.setText("Prekliči");
        tvConfirm.setText("Potrdi");

        btnConfirm.setOnClickListener(view -> {
            DocumentReference owner = selectedDate.owner;
            DocumentReference ownerOffer = selectedDate.ownerOffer;
            owner.get().addOnSuccessListener(ownerSnapshot -> {
                ArrayList ownerOffers = (ArrayList) ownerSnapshot.get("offers");
                Map ownerOfferMap = new HashMap();
                ownerOfferMap.put("offered_date", selectedDate.refOffer);
                ownerOfferMap.put("your_date", selectedDate.ref);
                ownerOffers.remove(ownerOfferMap);
                owner.update("offers", ownerOffers);
                ArrayList ownerDates = (ArrayList) ownerSnapshot.get("owned_dates");
                ownerDates.remove(selectedDate.ref);
                ownerDates.add(selectedDate.refOffer);
                owner.update("owned_dates", ownerDates);
                selectedDate.ref.update("is_tradable", false);
            });
            ownerOffer.get().addOnSuccessListener(ownerOfferSnapshot -> {
                ArrayList ownerOfferOffers = (ArrayList) ownerOfferSnapshot.get("offers");
                Map ownerOfferOfferMap = new HashMap();
                ownerOfferOfferMap.put("your_date", selectedDate.refOffer);
                ownerOfferOfferMap.put("offered_date", selectedDate.ref);
                ownerOfferOffers.remove(ownerOfferOfferMap);
                ownerOffer.update("offers", ownerOfferOffers);
                ArrayList ownerOfferDates = (ArrayList) ownerOfferSnapshot.get("owned_dates");
                ownerOfferDates.remove(selectedDate.refOffer);
                ownerOfferDates.add(selectedDate.ref);
                ownerOffer.update("owned_dates", ownerOfferDates);
                selectedDate.refOffer.update("is_tradable", false);
            });

            dialog.cancel();
            Intent taskComplete = new Intent(this, TaskCompleteActivity.class);
            taskComplete.putExtra("first_text", "Menjava uspešna");
            taskComplete.putExtra("second_text", "Termin je sedaj tvoj.");
            taskComplete.putExtra("third_text", "DOMOV");
            taskComplete.putExtra("fourth_text", "TERMINI");
            startActivity(taskComplete);
        });

        btnCancel.setOnClickListener(view -> {
            dialog.cancel();
        });
    }

    public static class TradeDealsAdapter extends ArrayAdapter<DateTrade> {
        public TradeDealsAdapter(Context context, ArrayList<DateTrade> dates) {
            super(context, 0, dates);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DateTrade date = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_my_trade_deals, parent, false);
            }

            TextView tvDescription = convertView.findViewById(R.id.trade_deal_description);
            tvDescription.setText("Sprejmi ponudbo");

            TextView tvDate = convertView.findViewById(R.id.date_offer);
            TextView tvTime = convertView.findViewById(R.id.time_offer);
            TextView tvHome = convertView.findViewById(R.id.location_offer);
            TextView tvOwner = convertView.findViewById(R.id.owner_offer);
            tvDate.setText(date.date);
            tvTime.setText(date.time);
            tvHome.setText(date.home);
            tvOwner.setText(date.person);

            TextView tvDateMy = convertView.findViewById(R.id.date);
            TextView tvTimeMy = convertView.findViewById(R.id.time);
            TextView tvHomeMy = convertView.findViewById(R.id.location);
            TextView tvOwnerMy = convertView.findViewById(R.id.owner);
            tvDateMy.setText(date.dateOffer);
            tvTimeMy.setText(date.timeOffer);
            tvHomeMy.setText(date.homeOffer);
            tvOwnerMy.setText(date.personOffer);

            return convertView;
        }
    }

    private void showTradeDeals() {
        TradeDealsAdapter adapter = new TradeDealsAdapter(this, arrayOfTradeDeals);

        DocumentReference currentUserData = db.collection("users").document(mAuth.getCurrentUser().getUid());

        currentUserData.get().addOnSuccessListener(documentSnapshot -> {
            Map data = documentSnapshot.getData();
            if (data == null) return;
            ArrayList<Map> offers = (ArrayList<Map>) data.get("offers");
            for (Map offer : offers) {
                DocumentReference offeredDate = (DocumentReference) offer.get("offered_date");
                DocumentReference yourDate = (DocumentReference) offer.get("your_date");

                offeredDate.get()
                        .addOnSuccessListener(offeredDateSnapshot -> {
                            Map offeredDateMap = offeredDateSnapshot.getData();
                            yourDate.get()
                                    .addOnSuccessListener(yourDateSnapshot -> {
                                        Map yourDateMap = yourDateSnapshot.getData();
                                        DocumentReference yourDateOwner = (DocumentReference) yourDateMap.get("owner");
                                        yourDateOwner.get()
                                                .addOnSuccessListener(yourDateOwnerSnapshot -> {
                                                    String yourOwnerName = (String) yourDateOwnerSnapshot.get("full_name");
                                                    DocumentReference offeredDateOwner = (DocumentReference) offeredDateMap.get("owner");
                                                    offeredDateOwner.get()
                                                            .addOnSuccessListener(offeredDateOwnerSnapshot -> {
                                                                String offeredOwnerName = (String) offeredDateOwnerSnapshot.get("full_name");
                                                                adapter.add(new DateTrade(
                                                                        (String) yourDateMap.get("date"),
                                                                        (String) yourDateMap.get("time"),
                                                                        yourOwnerName,
                                                                        (String) yourDateMap.get("dorm") + ",\n" + (String) yourDateMap.get("campus"),
                                                                        yourDate,
                                                                        yourDateOwner,
                                                                        (String) offeredDateMap.get("date"),
                                                                        (String) offeredDateMap.get("time"),
                                                                        offeredOwnerName,
                                                                        (String) offeredDateMap.get("dorm") + ",\n" + (String) offeredDateMap.get("campus"),
                                                                        offeredDate,
                                                                        offeredDateOwner
                                                                ));
                                                                progressBar.setVisibility(View.INVISIBLE);
                                                            });
                                                });
                                    });
                        });
            }
        });

        tradeDealsList.setAdapter(adapter);
    }

}