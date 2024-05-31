package com.example.taxcalculator;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Locale;

public class InfoDialogFragment extends DialogFragment {

    private TextView infoTextView;
    private ImageButton closeButton;

    public static InfoDialogFragment newInstance() {
        return new InfoDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_info, container, false);

        infoTextView = view.findViewById(R.id.infoTextView);
        closeButton = view.findViewById(R.id.closeButton);
        String text_ru="Версия приложения: 1.0\n" +
                "Реализовано:\n-НДС,\n-НДФЛ,\n-налог на облигации,\n-налог на авто,\n-налог на лотерейные выигрыши\n" +
                "Разработчики: \n-Шендрик ВС,\n-Воронков ИА. \n" +
                "Веб-разработчик: Шендрик ВС.\n" +
                "Бэкэнд-разработчик: Воронков ИА.";

        String text_us="Application version: 1.0\n" +
                "Implemented:\n-NDS,\n-NDFL,\n-obligation tax,\n-car tax,\n-lottery gains tax\n" +
                "Developers: \n-Shendrik VS,\n-Voronkov IA. \n" +
                "Web-designer: Shendrik VS.\n" +
                "Backend-developer: Voronkov IA.";

        Locale currentLocale = Locale.getDefault();
        String currentLanguage = currentLocale.getLanguage();
        if(currentLanguage.equals("ru")){
            infoTextView.setText(text_ru);
        }else{
            infoTextView.setText(text_us);
        }


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Home home_activity = (Home) getActivity();
                if (home_activity != null) {
                    ImageButton infoButton = home_activity.findViewById(R.id.infoIB);
                    Animation rotate_CC = AnimationUtils.loadAnimation(home_activity.getApplicationContext(), R.anim.rotate_info_counter_clockwise);
                    rotate_CC.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            infoButton.setHovered(false);
                            dismiss();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    infoButton.startAnimation(rotate_CC);
                    v.startAnimation(rotate_CC);
                }
            }
        });

        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        return view;
    }

}
