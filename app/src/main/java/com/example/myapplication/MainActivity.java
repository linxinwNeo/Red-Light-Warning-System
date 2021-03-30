package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;


import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
public class MainActivity extends AppCompatActivity {
    public Prediction light_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mulclight_data = new Prediction();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        if (sharedPreferences.getString("username", "null") != "null") {
            String username = sharedPreferences.getString("username", "null");
            String password = sharedPreferences.getString("password", "null");
            String[] arguments = {"-username", username, "-password", password};
            String session_code = null;
            try {
                session_code = TestTest.main(arguments);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            assert session_code != null;
            if (session_code.equals("")) {
                Log.e("Mytag", "Login Failed");
                login(sharedPreferences);
            } else {
                //create an object to another activity
                Intent startIntent = new Intent(getApplicationContext(), GeoReferenced_web_request.class);

                //passing sessioncode into another activity
                startIntent.putExtra("session_code", session_code);
                //running the new activity
                startActivity(startIntent);

            }
        }
        else{
            login(sharedPreferences);
        }


        }
        private void login(SharedPreferences sharedPreferences){
            setContentView(R.layout.activity_main);
            Button Btn_Login = (Button) findViewById(R.id.toLogin);
            Btn_Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText editText_Username = (EditText) findViewById(R.id.inputName);
                    EditText editText_Password = (EditText) findViewById(R.id.inputName);

                    String username = editText_Username.getText().toString();
                    String password = editText_Password.getText().toString();
                    String[] arguments = {"-username", username, "-password", password};
                    String session_code = null;
                    try {
                        session_code = TestTest.main(arguments);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    assert session_code != null;
                    if (session_code.equals("")) {
                        Log.e("Mytag", "Login Failed");
                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", username);
                        editor.putString("password",password);
                        editor.commit();

                        //create an object to another activity
                        Intent startIntent = new Intent(getApplicationContext(), GeoReferenced_web_request.class);

                        //passing sessioncode into another activity
                        startIntent.putExtra("session_code", session_code);
                        //running the new activity
                        startActivity(startIntent);

                    }
                }
            });
        }
    }