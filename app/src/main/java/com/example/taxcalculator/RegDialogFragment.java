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
        SharedPreferences authorized = getActivity().getSharedPreferences("Authorization", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = authorized.edit();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(remembered.equals("true")) {
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

        if(remembered.equals("true")){
            Registration.setText("Sign out");
            Registration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.putString("authorized", "false");
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
                Log.d("REGDIALOG", "onClick: ");
            }
        });

        // Set dialog size
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        return view;
    }
}
