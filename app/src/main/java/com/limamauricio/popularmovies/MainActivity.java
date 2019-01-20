package com.limamauricio.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    private static final String API_KEY = "API_KEY";
    private static int totalPages;
    private static int sort = 1;
    private Call<MoviesRequestResponse> call;
    private List<Movie> movieList;
    private MovieAdapter movieAdapter;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.movieRecyclerView)
    RecyclerView movieRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        checkNetworkConnection(getApplicationContext());
    }


    private void initLayout(){

        GridLayoutManager manager = new GridLayoutManager(this,2);
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
        Proxy proxy = ProxyFactory.getInstance().create(Proxy.class);

        if (isConnectedToInternet(getApplicationContext())){
            switch(sort){
                case 1:
                    call = proxy.getPopularMovies(page, API_KEY);
                    break;
                case 2:
                    call = proxy.getTopMovies(page, API_KEY);
                    break;
            }


            call.enqueue(new Callback<MoviesRequestResponse>() {
                @Override
                public void onResponse(@NonNull Call<MoviesRequestResponse> call, @NonNull Response<MoviesRequestResponse> response) {

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
                public void onFailure(@NonNull Call<MoviesRequestResponse> call, @NonNull Throwable t) {
                    Toast.makeText(MainActivity.this, getString(R.string.failed_to_get_movies), Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            Toast.makeText(MainActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

    }

    private void checkNetworkConnection(Context context){

        if(!isConnectedToInternet(context)){
            Toast.makeText(MainActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }else {
            initLayout();
        }

    }

    private boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = cm.getActiveNetworkInfo();
        if (i == null)
            return false;
        if (!i.isConnected())
            return false;
        if (!i.isAvailable())
            return false;
        return true;
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
                sort = 1;
                break;
            case R.id.top_rated:
                sort = 2;
                break;
        }
        loadPage(1);
        return super.onOptionsItemSelected(item);

    }
}
