package com.example.fabflixmobile;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter {
    //to reference the Activity
    private final Activity context;
    private final String[] ids;
    private final String[] titles;
    private final String[] years;
    private final String[] directors;
    private final String[] listGenres;
    private final String[] listStars;
    private final String[] ratings;

    public CustomListAdapter(Activity context, String[] ids, String[] titles, String[] years,
                             String[] directors, String[] listGenres, String[] listStars,
                             String[] ratings){

        super(context, R.layout.listview_row , ids);

        this.context=context;
        this.ids = ids;
        this.titles = titles;
        this.years = years;
        this.directors = directors;
        this.listGenres = listGenres;
        this.listStars = listStars;
        this.ratings = ratings;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView titleTV = (TextView) rowView.findViewById(R.id.etTitle);
        TextView yearTV = (TextView) rowView.findViewById(R.id.etYear);
        TextView directorTV = (TextView) rowView.findViewById(R.id.etDirector);
        TextView listGenresTV = (TextView) rowView.findViewById(R.id.etGenres);
        TextView listStarsTV = (TextView) rowView.findViewById(R.id.etStars);
        TextView ratingTV = (TextView) rowView.findViewById(R.id.etRating);

        //this code sets the values of the objects to values from the arrays
        Log.d("========", "===========");
        Log.d("position", String.valueOf(position));
        Log.d("id", ids[position]);
        Log.d("title", titles[position]);
//            Log.d("year", years[position]);
//            Log.d("director", directors[position]);
//            Log.d("listGenres", listGenres[position]);
//            Log.d("listStars", listStars[position]);
//            Log.d("rating", ratings[position]);

        titleTV.setText(titles[position]);
        yearTV.setText(years[position]);
        directorTV.setText(directors[position]);
        listGenresTV.setText(listGenres[position]);
        listStarsTV.setText(listStars[position]);
        ratingTV.setText(ratings[position]);


        return rowView;

    };
}
