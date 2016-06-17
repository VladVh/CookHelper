package com.example.vlad.cookhelper.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * This is a helper class for use with the database. It provides base Uris
 * and has an inner class with Product table columns names.
 */
public class ProductContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.example.vlad.cookhelper";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_COOKING = "cookhelper";


    /* Inner class that defines the table contents of the movie table */
    public static final class ProductEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COOKING).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COOKING;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COOKING;

        public static final String TABLE_NAME = "cookhelper";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_GLASS = "glass";
        public static final String COLUMN_FACETED_GLASS = "faceted_glass";
        public static final String COLUMN_TABLESPOON = "tablespoon";
        public static final String COLUMN_TEASPOON = "teaspoon";

        public static String getMovieIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static Uri buildProductUriWithId(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildProductUri() {
            return CONTENT_URI;
        }
    }
}
