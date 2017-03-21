package fr.laurent_levan.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

import fr.laurent_levan.popularmovies.data.Movie;
import fr.laurent_levan.popularmovies.data.Trailer;
import fr.laurent_levan.popularmovies.utilities.NetworkUtils;
import fr.laurent_levan.popularmovies.utilities.TheMovieDBJsonUtils;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    private String mOrderByOption;
    private Spinner mSpinner;

    private final static String OPTION_SELECTED = "selected";

    private int optionSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //initialize the orderByOption to avoid Error after
        mOrderByOption = getResources().getStringArray(R.array.sort_by_value)[optionSelected];

        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            optionSelected = savedInstanceState.getInt(OPTION_SELECTED);
        }
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        GridLayoutManager layoutManager
            = new GridLayoutManager(this,2);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadMoviesData();
    }

    private void loadMoviesData() {
        showMoviesDataView();

        new FetchMoviesTask().execute(mOrderByOption);
    }

    private void showMoviesDataView(){
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(VISIBLE);
    }

    private void showErrorMessage(){
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(VISIBLE);
    }

    @Override
    public void onClick(Movie movieChosen) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("myMovieObj",movieChosen);
        startActivity(intentToStartDetailActivity);
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            if(params.length == 0) {
                return null;
            }

            String sortBy = params[0];
            URL moviesRequestUrl = NetworkUtils.buildGetMoviesUrl(sortBy);

            try {
                String jsonMoviesResponse = NetworkUtils.getResponseFromHttpUrl(moviesRequestUrl);

                ArrayList<Movie> moviesList = TheMovieDBJsonUtils
                        .getBasicMovieDataFromJson(MainActivity.this, jsonMoviesResponse);

                return moviesList;
            }catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null) {
                showMoviesDataView();
                mMovieAdapter.setMoviesData(movies);
            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_sort);
        mSpinner = (Spinner) MenuItemCompat.getActionView(item);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sort_by_display, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(optionSelected);

        AdapterView.OnItemSelectedListener sortedByOptionSelectedListener = new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> spinner, View container, int position, long id) {
                String[] orderByArray = getResources().getStringArray(R.array.sort_by_value);
                mOrderByOption = orderByArray[position];
                optionSelected = position;
                loadMoviesData();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        mSpinner.setOnItemSelectedListener(sortedByOptionSelectedListener);

        mSpinner.getOnItemSelectedListener();
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(OPTION_SELECTED,optionSelected);
        super.onSaveInstanceState(outState);

    }
}
