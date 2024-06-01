package com.example.taxcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.taxcalculator.taxes.another_activity;
import com.example.taxcalculator.taxes.car_tax_activity;
import com.example.taxcalculator.taxes.lottery_activity;
import com.example.taxcalculator.taxes.ndfl_activity;
import com.example.taxcalculator.taxes.nds_activity;
import com.example.taxcalculator.taxes.obligations_activity;

import java.util.Locale;

public class Home extends AppCompatActivity  {

    private boolean isRussiaAtFront=true;
    private ImageButton russiaIB, usIB, menuIB, NDFL_IB, NDS_IB, Lottery_IB, Car_IB, Obligations_IB, Other_IB, info_IB;
    private TextView NDFL_tv, NDS_tv, Lottery_tv, Car_tv, Obligations_tv, Other_tv;


    private void resetButtonStates() {
        NDFL_IB.setHovered(false);
        NDS_IB.setHovered(false);
        Lottery_IB.setHovered(false);
        Obligations_IB.setHovered(false);
        Other_IB.setHovered(false);
        Car_IB.setHovered(false);

        NDFL_tv.setTextColor(getResources().getColor(R.color.cadet_gray));
        NDS_tv.setTextColor(getResources().getColor(R.color.cadet_gray));
        Car_tv.setTextColor(getResources().getColor(R.color.cadet_gray));
        Obligations_tv.setTextColor(getResources().getColor(R.color.cadet_gray));
        Lottery_tv.setTextColor(getResources().getColor(R.color.cadet_gray));
        Other_tv.setTextColor(getResources().getColor(R.color.cadet_gray));

    }

    @Override
    protected void onResume() {
        super.onResume();
        resetButtonStates();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        Animation jump =  AnimationUtils.loadAnimation(this,R.anim.jump);

        russiaIB=findViewById(R.id.russia_flag_IB);
        usIB=findViewById(R.id.us_flag_IB);
        menuIB=findViewById(R.id.hamburgerIB);
        NDFL_IB=findViewById(R.id.IMGB1);
        NDS_IB=findViewById(R.id.IMGB2);
        Lottery_IB=findViewById(R.id.IMGB3);
        Car_IB=findViewById(R.id.IMGB4);
        Obligations_IB=findViewById(R.id.IMGB5);
        Other_IB=findViewById(R.id.IMGB6);
        info_IB=findViewById(R.id.infoIB);

        NDFL_tv=findViewById(R.id.TV_NDFL);
        NDS_tv=findViewById(R.id.TV_NDS);
        Car_tv=findViewById(R.id.TV_car);
        Obligations_tv=findViewById(R.id.TV_obligation);
        Lottery_tv=findViewById(R.id.TV_lottery);
        Other_tv=findViewById(R.id.TV_another);



        /* TODO: LOCALISATION
        if(position_changed_once){
            swapPositions(russiaIB,usIB);
            usIB.bringToFront();
        }*/
        // Check the locale and swap the positions if the locale is English
        Configuration config = getResources().getConfiguration();
        Locale currentLocale = config.getLocales().get(0);
        if (currentLocale.getLanguage().equals("en")) {
            swapPositions(russiaIB, usIB);
            usIB.bringToFront();
            isRussiaAtFront = false;
        }


            Car_IB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation jump = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.jump);
                jump.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(getApplicationContext(), car_tax_activity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                });
                Car_IB.startAnimation(jump);
                Car_tv.setTextColor(getResources().getColor(R.color.golden));
                Car_IB.setHovered(true);
            }
        });



        Lottery_IB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation jump = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.jump);
                jump.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(getApplicationContext(), lottery_activity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                });
                Lottery_IB.startAnimation(jump);
                Lottery_tv.setTextColor(getResources().getColor(R.color.golden));
                Lottery_IB.setHovered(true);
            }
        });

        NDFL_IB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation jump = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.jump);
                jump.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(getApplicationContext(), ndfl_activity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                });
                NDFL_IB.startAnimation(jump);
                NDFL_tv.setTextColor(getResources().getColor(R.color.golden));

                NDFL_IB.setHovered(true);
            }
        });
        NDS_IB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation jump = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.jump);
                jump.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(getApplicationContext(), nds_activity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                });
                NDS_IB.startAnimation(jump);
                NDS_tv.setTextColor(getResources().getColor(R.color.golden));
                NDS_IB.setHovered(true);
            }
        });
        Obligations_IB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation jump = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.jump);
                jump.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(getApplicationContext(), obligations_activity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                });
                Obligations_IB.startAnimation(jump);
                Obligations_tv.setTextColor(getResources().getColor(R.color.golden));
                Obligations_IB.setHovered(true);
            }
        });
        Other_IB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation jump = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.jump);
                jump.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(getApplicationContext(), another_activity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                });
                Other_IB.startAnimation(jump);
                Other_tv.setTextColor(getResources().getColor(R.color.golden));
                Other_IB.setHovered(true);
            }
        });



        View.OnClickListener switchFlagsListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFlags();
            }
        };

        russiaIB.setOnClickListener(switchFlagsListener);
        usIB.setOnClickListener(switchFlagsListener);

        info_IB.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                Log.d("onHover COMPLETED", "onHover COMPLETED");
                return false;
            }
        });
        info_IB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClick COMPLETED", "onClick COMPLETED");
                showInfoDialog();
                Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_info);
                rotate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                info_IB.setHovered(true);
                v.startAnimation(rotate);
            }
        });


        menuIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClick REG", "onClick COMPLETED");
                showRegDialog();
            }
        });

    }

    private void showInfoDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        InfoDialogFragment dialogFragment = InfoDialogFragment.newInstance();
        dialogFragment.show(fragmentManager, "InfoDialog");
    }

    private void showRegDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        RegDialogFragment dialogFragment = RegDialogFragment.newInstance();
        dialogFragment.show(fragmentManager, "RegDialog");
    }



    private void switchFlags() {
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise);
        Animation rotateCounterClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_counter_clockwise);

        //Animation translateLeft = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        //Animation translateRight = AnimationUtils.loadAnimation(this, R.anim.translate_right);
        //Animation translateUp= AnimationUtils.loadAnimation(this, R.anim.up);
        //Animation translateDown= AnimationUtils.loadAnimation(this, R.anim.down);
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        AnimationSet animationSetRussia = new AnimationSet(true);
        animationSetRussia.addAnimation(rotate);
        animationSetRussia.addAnimation(fadeOut);

        AnimationSet animationSetUS = new AnimationSet(true);
        animationSetUS.addAnimation(rotate);
        animationSetUS.addAnimation(fadeOut);

        russiaIB.startAnimation(animationSetRussia);
        usIB.startAnimation(animationSetUS);

        animationSetRussia.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                swapPositions(russiaIB, usIB);
                AnimationSet fadeInSet = new AnimationSet(true);
                fadeInSet.addAnimation(rotateCounterClockwise);
                fadeInSet.addAnimation(fadeIn);

                russiaIB.startAnimation(fadeInSet);
                usIB.startAnimation(fadeInSet);

                if (isRussiaAtFront) {

                    usIB.bringToFront();


                } else {
                    russiaIB.bringToFront();

                }

                isRussiaAtFront = !isRussiaAtFront;
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
    }

    private void swapPositions(ImageButton first, ImageButton second) {
        FrameLayout.LayoutParams firstParams = (FrameLayout.LayoutParams) first.getLayoutParams();
        FrameLayout.LayoutParams secondParams = (FrameLayout.LayoutParams) second.getLayoutParams();

        FrameLayout.LayoutParams tempParams = new FrameLayout.LayoutParams(firstParams);
        first.setLayoutParams(secondParams);
        second.setLayoutParams(tempParams);
    }
}