package com.example.fabflixmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    private EditText search;
    private List<String> ids;
    private List<String> titles;
    private List<String> years;
    private List<String> directors;
    private List<String> genres;
    private List<String> stars;
    private List<String> ratings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        search = (EditText) findViewById(R.id.etSearchBar);

    }

    public void processMovies(JSONArray results) throws JSONException {
        ids = new ArrayList<String>();
        titles = new ArrayList<String>();
        years = new ArrayList<String>();
        directors = new ArrayList<String>();
        genres = new ArrayList<String>();
        stars = new ArrayList<String>();
        ratings = new ArrayList<String>();

        for (int i = 0; i < results.length(); i++) {
            ids.add(results.getJSONObject(i).getString("movie_id"));
            titles.add(results.getJSONObject(i).getString("title"));
            years.add(results.getJSONObject(i).getString("year"));
            directors.add(results.getJSONObject(i).getString("director"));
            genres.add(results.getJSONObject(i).getString("listGenres"));
            stars.add(results.getJSONObject(i).getString("listStarsName"));
            ratings.add(results.getJSONObject(i).getString("rating"));
        }

        String [] idsArr = new String[ids.size()];
        idsArr = ids.toArray(idsArr);

        String [] titlesArr = new String[titles.size()];
        titlesArr = titles.toArray(titlesArr);

        String [] yearsArr = new String[years.size()];
        yearsArr = years.toArray(yearsArr);

        String [] directorsArr = new String[directors.size()];
        directorsArr = directors.toArray(directorsArr);

        String [] genresArr = new String[genres.size()];
        genresArr = genres.toArray(genresArr);

        String [] starsArr = new String[stars.size()];
        starsArr = stars.toArray(starsArr);

        String [] ratingsArr = new String[ratings.size()];
        ratingsArr = ratings.toArray(ratingsArr);

//        Log.d("ids", ids.toString());
//        Log.d("titles", titles.toString());
//        Log.d("years", years.toString());
//        Log.d("directors", directors.toString());
//        Log.d("genres", genres.toString());
//        Log.d("stars", stars.toString());
//        Log.d("ratings", ratings.toString());

        CustomListAdapter whatever = new CustomListAdapter(SecondActivity.this, idsArr,
                titlesArr, yearsArr, directorsArr, genresArr, starsArr, ratingsArr);

        ListView listView;
        listView  = (ListView) findViewById(R.id.listviewID);
        listView.setAdapter(whatever);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);

                intent.putExtra("movieId", ids.get(position));
                startActivity(intent);
            }
        });
    }


    public void searchResult(View view) throws JSONException{

        final RequestQueue queue = NetworkManager.sharedManager(this).queue;

        String url = "https://10.0.2.2:8443/fabflix/api/searchBy?title=" + search.getText().toString();

        final JsonArrayRequest jsonRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("secondActivity-response", response.toString());

                        if (response.toString().length() > 0) {
                            try {
                                processMovies(response);
                            } catch (JSONException e) {
                                Log.d("json-error-secondAct", e.toString());
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("secondActivity-error", error.toString());
                    }
                });
        // prevent time-out error
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // sending request to tomcat server
        queue.add(jsonRequest);
    }

}
