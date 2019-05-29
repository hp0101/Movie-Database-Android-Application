package com.example.fabflixmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

public class ThirdActivity extends AppCompatActivity {

    private String movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        movieId = getIntent().getStringExtra("movieId");
        Log.d("hi", movieId);
        retrieveSingleMovie();
    }
    public void processSingleMovie(JSONArray results) throws JSONException {
        TextView titleTV = (TextView) findViewById(R.id.sTitle);
        TextView yearTV = (TextView) findViewById(R.id.sYear);
        TextView directorTV = (TextView) findViewById(R.id.sDirector);
        TextView listGenresTV = (TextView) findViewById(R.id.sListGenres);
        TextView listStarsTV = (TextView) findViewById(R.id.sListStars);

        titleTV.setText(results.getJSONObject(0).getString("title"));
        yearTV.setText(results.getJSONObject(0).getString("year"));
        directorTV.setText(results.getJSONObject(0).getString("director"));
        listGenresTV.setText(results.getJSONObject(0).getString("listGenres"));
        listStarsTV.setText(results.getJSONObject(0).getString("listStars"));
    }

    public void retrieveSingleMovie() {
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;

        String url = "https://10.0.2.2:8443/fabflix/api/single-movie?id=" + movieId;

        final JsonArrayRequest jsonRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("thirdActivity-response", response.toString());
                        if (response.toString().length() > 0)
                            try {
                                processSingleMovie(response);
                            } catch (JSONException e) {
                                Log.d("json-error-thirdAct", e.toString());
                            }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("thirdActivity-error", error.toString());
                    }
                });

        // sending request to tomcat server
        queue.add(jsonRequest);
    }
}
