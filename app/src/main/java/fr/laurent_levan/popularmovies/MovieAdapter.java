package fr.laurent_levan.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fr.laurent_levan.popularmovies.data.Movie;

/**
 * Created by Laurent on 22/01/2017.
 * adapter for a movie into an imageView
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private static final String imageBaseUrl = "https://image.tmdb.org/t/p/w185";

    /**
     * List of movies
     */
    private ArrayList<Movie> mMoviesData;

    /**
     * on-click handler
     */
    private final MovieAdapterOnClickHandler mClickHandler;

    /**
     * An interface to receives onClick messages.
     */
    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movieChosen);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movies_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        Context context = holder.mMovieImageView.getContext();
        String imgUrl = imageBaseUrl + mMoviesData.get(position).getPosterSrc();
        Picasso.with(context).load(imgUrl).into(holder.mMovieImageView);
    }

    @Override
    public int getItemCount() {
        if (null == mMoviesData) return 0;
        return mMoviesData.size();
    }


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final ImageView mMovieImageView;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            mMovieImageView = (ImageView) itemView.findViewById(R.id.iv_movie_data);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movieChosen = mMoviesData.get(adapterPosition);
            mClickHandler.onClick(movieChosen);
        }
    }

    public void setMoviesData(ArrayList<Movie> moviesData) {
        mMoviesData = moviesData;
        notifyDataSetChanged();
    }

}
