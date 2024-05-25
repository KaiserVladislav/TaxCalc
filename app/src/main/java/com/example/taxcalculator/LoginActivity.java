package com.example.taxcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taxcalculator.Server.RetrofitService;
import com.example.taxcalculator.Server.User;
import com.example.taxcalculator.Server.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeComponents();


        ImageButton backToSSA=findViewById(R.id.back_to_SSA);
        backToSSA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation left = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left);
                left.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                });
                backToSSA.setHovered(true);
                backToSSA.startAnimation(left);
            }
        });
    }

    private void initializeComponents() {
        SharedPreferences preferences = getSharedPreferences("checkbox", Context.MODE_PRIVATE);

        EditText username = findViewById(R.id.UsernameLog);
        EditText password = findViewById(R.id.PasswordLog);

        String usernameValue = username.getText().toString().trim();//without spaces
        String passwordValue = password.getText().toString().trim();

        Button loginButton = findViewById(R.id.Login);
        CheckBox rememberCheckBox = findViewById(R.id.LoginRemember);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!rememberCheckBox.isChecked()) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    if (usernameValue.contains("@")) {
                        editor.putString("email", usernameValue);
                        editor.putString("username", "");
                    } else {
                        editor.putString("username", usernameValue);
                        editor.putString("email", "");
                    }
                    editor.apply();
                    Log.d("DEBUG", "false");
                }
                loginLogic(usernameValue, passwordValue);
            }
        });

        rememberCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences.Editor editor = preferences.edit();
                if (isChecked) {
                    editor.putString("remember", "true");
                    if (usernameValue.contains("@")) {
                        editor.putString("email", usernameValue);
                        editor.putString("username", "");
                    } else {
                        editor.putString("username", usernameValue);
                        editor.putString("email", "");
                    }
                    editor.apply();
                    Log.d("DEBUG", "onCheckedChanged: " + isChecked);
                }
            }
        });
    }

    private void loginLogic(String username, String password) {
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
        if (username.contains("@")) {
            Log.d("Success", username);
            userApi.getUserByEmail(username).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Log.d("Success", "Email response " + response.body());
                    if (password.equals(response.body().getPassword())) {
                        //Navigation.findNavController(view).navigate(R.id.action_rootFragment_to_menuFragment);
                        Toast.makeText(getApplicationContext(), "Вы успешно авторизированы", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Password is wrong", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Email is wrong!", Toast.LENGTH_SHORT).show();
                    Log.d("Error", "EMAIL", t);
                }
            });
        } else {
            userApi.getUserByUsername(username).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Toast.makeText(getApplicationContext(), "Вы успешно авторизированы", Toast.LENGTH_LONG).show();
                    Log.d("Success", "Username response " + response.body());
                    if (password.equals(response.body().getPassword())) {
                        //Navigation.findNavController(view).navigate(R.id.action_rootFragment_to_menuFragment);
                        Toast.makeText(getApplicationContext(), "Вы успешно авторизированы", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Password is wrong", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Username is wrong!", Toast.LENGTH_SHORT).show();
                    Log.d("Error", "USERNAME", t);
                }
            });
        }
    }
}