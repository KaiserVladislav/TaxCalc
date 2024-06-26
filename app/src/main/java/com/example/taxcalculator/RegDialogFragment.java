package com.example.taxcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;



public class RegDialogFragment extends DialogFragment {

    private Button Registration;
    private Button History;
    private Button Login;

    public static RegDialogFragment newInstance() {
        return new RegDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_reg, container, false);

        // Initialize views
        Registration = view.findViewById(R.id.registration);
        History = view.findViewById(R.id.history);
        Login = view.findViewById(R.id.login);

        SharedPreferences preferences = getActivity().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
        String remembered = preferences.getString("remember", "");
        SharedPreferences.Editor preferancesEditor = preferences.edit();

        SharedPreferences authorization = getActivity().getSharedPreferences("Authorization", Context.MODE_PRIVATE);
        String auth = authorization.getString("authorized", "false");
        SharedPreferences.Editor authrozitaionEditor = authorization.edit();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(remembered.equals("true") || auth.equals("true")) {
                    Toast.makeText(getActivity(), "Вы уже авторизованы", Toast.LENGTH_SHORT).show();
                    dismiss(); // Close the dialog
                }
                else{
                    dismiss();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        Log.d("AUTH", "AUTH: "+auth);

        if(auth.equals("true") || remembered.equals("true")){
            if(Home.CURRENT_LANGUAGE.equals("en"))
                Registration.setText("Sign out");
            else
                Registration.setText("Выйти");
            Registration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    authrozitaionEditor.putString("authorized", "false");
                    preferancesEditor.putString("remember", "false");
                    preferancesEditor.putString("email", "");
                    preferancesEditor.putString("username", "");
                    Toast.makeText(getActivity(), "Вы успешно вышли из аккаунта", Toast.LENGTH_SHORT);
                    dismiss();
                }
            });
        }
        else{
            Registration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), RegistrationActivity.class);
                    startActivity(intent);
                    dismiss(); // Close the dialog
                }
            });
        }


        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
                dismiss();
                Log.d("REGDIALOG", "onClick: ");
            }
        });

        // Set dialog size
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        return view;
    }
}
