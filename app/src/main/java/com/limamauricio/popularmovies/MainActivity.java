package com.limamauricio.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.limamauricio.popularmovies.model.Movie;
import com.limamauricio.popularmovies.model.MoviesRequestResponse;
import com.limamauricio.popularmovies.proxy.Proxy;
import com.limamauricio.popularmovies.proxy.ProxyFactory;
import com.limamauricio.popularmovies.ui.MovieAdapter;
import com.limamauricio.popularmovies.ui.MovieDetailsActivity;
import com.limamauricio.popularmovies.utils.EndlessRecyclerViewScrollListener;
import com.limamauricio.popularmovies.utils.MovieOnClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "API_KEY";
    private static int totalPages;
    private static int sortMode = 1;
    private Call<MoviesRequestResponse> call;
    private List<Movie> movieList;
    private MovieAdapter movieAdapter;

    @BindView(R.id.movieRecyclerView)
    RecyclerView movieRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        checkNetwork(getApplicationContext());
    }


    private void initLayout(){

        GridLayoutManager manager = null;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            manager = new GridLayoutManager(this,4);
        }else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            manager = new GridLayoutManager(this,2);
        }

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                return 1;
            }
        });

        movieRecyclerView.setLayoutManager(manager);

        loadPage(1);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if ((page + 1) <= totalPages) {
                    loadPage(page + 1);
                }
            }
        };

        movieRecyclerView.addOnScrollListener(scrollListener);

    }

    private void loadPage(final int page) {
        Proxy proxy = ProxyFactory.getInstace().create(Proxy.class);

        switch(sortMode){
            case 1:
                call = proxy.getPopularMovies(page, API_KEY);
                break;
            case 2:
                call = proxy.getTopMovies(page, API_KEY);
                break;
        }


        call.enqueue(new Callback<MoviesRequestResponse>() {
            @Override
            public void onResponse(Call<MoviesRequestResponse> call, Response<MoviesRequestResponse> response) {

                if(page == 1) {
                    movieList = response.body().getMovieList();
                    totalPages = response.body().getTotalPages();

                    movieAdapter = new MovieAdapter(movieList, new MovieOnClickListener() {
                        @Override
                        public void onMovieClick(Movie movie) {
                            Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("movie", movie);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    movieRecyclerView.setAdapter(movieAdapter);
                } else {
                    List<Movie> movies = response.body().getMovieList();
                    for(Movie movie : movies){
                        movieList.add(movie);
                        movieAdapter.notifyItemInserted(movieList.size() - 1);
                    }
                }

            }

            @Override
            public void onFailure(Call<MoviesRequestResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to get movies. Please, try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkNetwork(Context context){

        if(!isConnectedToInternet(context)){
            Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
        }else {
            initLayout();
        }

    }

    private boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
                sortMode = 1;
                break;
            case R.id.top_rated:
                sortMode = 2;
                break;
        }
        loadPage(1);
        return super.onOptionsItemSelected(item);

    }
}
