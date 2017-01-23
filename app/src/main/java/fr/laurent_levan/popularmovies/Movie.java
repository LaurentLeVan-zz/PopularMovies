package fr.laurent_levan.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Laurent on 22/01/2017.
 * Class that represent a movie
 */

public class Movie implements Parcelable {
    private Integer movieId;
    private String originalTitle;
    private String posterSrc;
    private String plotSynopsis;
    private Float userRating;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterSrc() {
        return posterSrc;
    }

    public void setPosterSrc(String posterSrc) {
        this.posterSrc = posterSrc;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public Float getUserRating() {
        return userRating;
    }

    public void setUserRating(Float userRating) {
        this.userRating = userRating;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    private Date releaseDate;


    /**
     * Constructor for a movie
     * @param movieId the movie id form the movie db
     * @param originalTitle the original title of the movie
     * @param posterSrc to get the poster image url of the movie
     * @param plotSynopsis the synopsis about the movie
     * @param userRating average rating of the movie
     * @param releaseDate the release date
     */
    public Movie(Integer movieId, String originalTitle, String posterSrc, String plotSynopsis, Float userRating, Date releaseDate) {
        this.originalTitle = originalTitle;
        this.posterSrc = posterSrc;
        this.plotSynopsis = plotSynopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.movieId = movieId;
    }


    protected Movie(Parcel in) {
        originalTitle = in.readString();
        posterSrc = in.readString();
        plotSynopsis = in.readString();
        userRating = in.readFloat();
        releaseDate = new Date(in.readLong());
        movieId = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeString(originalTitle);
        dest.writeString(posterSrc);
        dest.writeString(plotSynopsis);
        dest.writeFloat(userRating);
        dest.writeDouble(releaseDate.getTime());
    }
}
