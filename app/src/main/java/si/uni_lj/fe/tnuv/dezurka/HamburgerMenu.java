package si.uni_lj.fe.tnuv.dezurka;

import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class HamburgerMenu {
    static void setupHamburgerMenu(AppCompatActivity view) {
        ConstraintLayout hamburgerBtn = view.findViewById(R.id.hamburger_menu_btn);
        ConstraintLayout hamburgerMenuActive = view.findViewById(R.id.hamburger_menu_overlay);
        ConstraintLayout hamburgerMenuBgBlur = view.findViewById(R.id.hamburger_menu_background_blur_element);

        hamburgerBtn.setOnClickListener(view1 -> {
            ImageView hamburgerIcon = hamburgerBtn.findViewById(R.id.hamburger_menu_btn_icon);
            if (hamburgerMenuActive.getVisibility() == View.GONE) {
                hamburgerMenuActive.setVisibility(View.VISIBLE);
                hamburgerMenuBgBlur.setVisibility(View.VISIBLE);
                hamburgerIcon.setImageResource(R.drawable.hamburger_close_icon);
            } else {
                hamburgerMenuActive.setVisibility(View.GONE);
                hamburgerMenuBgBlur.setVisibility(View.GONE);
                hamburgerIcon.setImageResource(R.drawable.hamburger_menu_btn_icon);
            }
        });

        ConstraintLayout myDatesHamburgerBtn = hamburgerMenuActive.findViewById(R.id.hamburger_menu_btn_my_dates);
        ConstraintLayout availableDatesHamburgerBtn = hamburgerMenuActive.findViewById(R.id.hamburger_menu_btn_available_dates);
        ConstraintLayout tradesHamburgerBtn = hamburgerMenuActive.findViewById(R.id.hamburger_menu_btn_trades);
        ConstraintLayout settingsHamburgerBtn = hamburgerMenuActive.findViewById(R.id.hamburger_menu_btn_settings);

        ImageView myDatesHamburgerIcon = myDatesHamburgerBtn.findViewById(R.id.hamburger_menu_btn_icon);
        ImageView availableDatesHamburgerIcon = availableDatesHamburgerBtn.findViewById(R.id.hamburger_menu_btn_icon);
        ImageView tradesHamburgerIcon = tradesHamburgerBtn.findViewById(R.id.hamburger_menu_btn_icon);
        ImageView settingsHamburgerIcon = settingsHamburgerBtn.findViewById(R.id.hamburger_menu_btn_icon);

        myDatesHamburgerIcon.setImageResource(R.drawable.hamburger_mydates_icon);
        availableDatesHamburgerIcon.setImageResource(R.drawable.hamburger_availabledates_icon);
        tradesHamburgerIcon.setImageResource(R.drawable.hamburger_trades_icon);
        settingsHamburgerIcon.setImageResource(R.drawable.hamburger_settings_icon);
    }
}
