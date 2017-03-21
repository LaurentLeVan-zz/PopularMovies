package fr.laurent_levan.popularmovies.data;

import android.renderscript.Element;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by Laurent on 19/03/2017.
 */

public interface MovieColumns {

    @DataType(DataType.Type.INTEGER) @PrimaryKey
    @AutoIncrement
    public static final String _ID = "_id";
    @DataType(DataType.Type.TEXT) @NotNull
    public static final String MOVIE_TITLE = "movie_title";
    @DataType(DataType.Type.INTEGER) @NotNull
    public static final String MOVIE_ID = "movie_id";


}
