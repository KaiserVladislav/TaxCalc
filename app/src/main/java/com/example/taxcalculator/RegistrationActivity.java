package com.example.taxcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taxcalculator.Server.RetrofitService;
import com.example.taxcalculator.Server.User;
import com.example.taxcalculator.Server.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                TextView ErrorText = findViewById(R.id.ErrorText);
                String prevErrorText = ErrorText.getText().toString();
                ErrorText.setText(prevErrorText+msg.obj);
            }
        };
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
        EditText Username = findViewById(R.id.UsernameReg);
        EditText Email = findViewById(R.id.EmailReg);
        EditText Password = findViewById(R.id.PasswordReg);
        Button registration = findViewById(R.id.Registration);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameValue = Username.getText().toString().trim();
                String emailValue = Email.getText().toString().trim();
                String passwordValue = Password.getText().toString().trim();



                if(checkValues(usernameValue,emailValue, passwordValue)){
                    checkEmailUniqueness(usernameValue, emailValue, passwordValue); //check email, username => registrate
                    Log.d("DEBUG", "onClick: alright! ");
                }
            }
        });
    }
    private void registrationLogic(String username, String email, String password){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        CheckBox rememberCheckBox = findViewById(R.id.RegRemember);

        if(rememberCheckBox.isChecked()){
            editor.putString("remember", "true");
            editor.putString("email", email);
            editor.putString("username", "");
        }
        else{
            editor.putString("remember", "false");
        }

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        userApi.save(newUser).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "FAIL!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean checkValues(String username, String email, String password) {
        boolean valuesAreCorrect = true;
        TextView ErrorText = findViewById(R.id.ErrorText);
        ((TextView) findViewById(R.id.ErrorText)).setText("");

        if(username.isEmpty() || email.isEmpty() || password.isEmpty()){
            ErrorText.setText("ЗАПОЛНИТЕ ВСЕ ПОЛЯ");
            return false;
        }
        if(!email.contains("@")){
            String prevErrorText = ErrorText.getText().toString();
            ((TextView)findViewById(R.id.ErrorText)).setText(prevErrorText+"Почта должна содержать @\n");
            valuesAreCorrect = false;
        }
        if(username.contains("@")){
            String prevErrorText = ErrorText.getText().toString();
            ErrorText.setText(prevErrorText+"Имя пользователя не должно содержать @\n");
            valuesAreCorrect = false;
        }
        return valuesAreCorrect;
    }
    private void checkEmailUniqueness(String username, String email, String password){
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        userApi.countUsersByEmail(email).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body()!=null && response.body()==0){
                    Log.d("DEBUG", "onResponseEmailUniqueness: " + email +" "+response.body() );
                    checkUsernameUniqueness(username, email, password);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Почта занята", Toast.LENGTH_SHORT).show();
                    Message msg = new Message();
                    msg.obj = "Пользователь с такой почтой уже существует";
                    handler.sendMessage(msg);
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("ERROR", "onFailureEmailUniqueness: ", t);
            }
        });
    }
    private void checkUsernameUniqueness(String username, String email, String password){
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
        userApi.countUsersByUsername(username).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body()!= null && response.body()==0){
                    registrationLogic(username, email, password);
                    Intent intent = new Intent(RegistrationActivity.this, Home.class);
                    Toast.makeText(RegistrationActivity.this, "Успешная регистрация", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else {
                    Message msg = new Message();
                    msg.obj = "Пользователь с таким именем уже существует";
                    handler.sendMessage(msg);
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("ERROR", "onFailureUsernameUniqueness: ", t);
            }
        });
    }
}