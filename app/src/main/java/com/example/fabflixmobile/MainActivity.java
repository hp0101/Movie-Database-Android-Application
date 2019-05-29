package com.example.fabflixmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private TextView errorMessage;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);
        errorMessage = (TextView) findViewById(R.id.etError);
    }

    public void connectToTomcat(View view) {

        final RequestQueue queue = NetworkManager.sharedManager(this).queue;

        final StringRequest afterLoginRequest = new StringRequest(Request.Method.GET, "https://10.0.2.2:8443/fabflix/api/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("username.response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("username.error", error.toString());
                    }
                }
        );

        final StringRequest loginRequest = new StringRequest(Request.Method.POST, "https://10.0.2.2:8443/fabflix/api/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
//                        queue.add(afterLoginRequest);
                        if (response.contains("fail")) {
                            errorMessage.setText("Incorrect username or password.");
                        }
                        else
                        {
                            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("login.error", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                // Post request form data
                String usernameStr = username.getText().toString();
                String passwordStr = password.getText().toString();

                Log.d("username", usernameStr);
                Log.d("password", passwordStr);

                final Map<String, String> params = new HashMap<String, String>();
                params.put("username", usernameStr);
                params.put("password", passwordStr);

                return params;
            }
        };

        queue.add(loginRequest);
    }
}
