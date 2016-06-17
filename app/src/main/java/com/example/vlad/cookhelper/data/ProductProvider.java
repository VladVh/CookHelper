package com.example.vlad.cookhelper.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * This class does the main job when contacting with the db.
 */
public class ProductProvider extends ContentProvider {
    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DatabaseOpenHelper mOpenHelper;
    private static final SQLiteQueryBuilder sProductsQueryBuilder;

    static {
        sProductsQueryBuilder = new SQLiteQueryBuilder();
        sProductsQueryBuilder.setTables(ProductContract.ProductEntry.TABLE_NAME);
    }

    private static final String sMovieFavoritesIdSelection =
            ProductContract.ProductEntry.TABLE_NAME +
                    "." + ProductContract.ProductEntry._ID + " = ? ";

    static final int PRODUCTS = 100;
    static final int PRODUCT_BY_ID = 101;

    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseOpenHelper(getContext());
        return false;
    }

    private Cursor getProductByID(Uri uri, String[] projection) {
        String product_id = ProductContract.ProductEntry.getMovieIDFromUri(uri);

        return sProductsQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                sMovieFavoritesIdSelection,
                new String[] {product_id},
                null,
                null,
                null
        );
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;

        switch (sUriMatcher.match(uri)) {
            case PRODUCTS: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        ProductContract.ProductEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case PRODUCT_BY_ID: {
                cursor = getProductByID(uri, projection);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case PRODUCTS: {
                return ProductContract.ProductEntry.CONTENT_TYPE;
            }
            case PRODUCT_BY_ID: {
                return ProductContract.ProductEntry.CONTENT_ITEM_TYPE;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case PRODUCTS: {
                long _id = db.insert(ProductContract.ProductEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = ProductContract.ProductEntry.buildProductUriWithId(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case PRODUCT_BY_ID: {
                long _id = db.insert(ProductContract.ProductEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = ProductContract.ProductEntry.buildProductUriWithId(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if ( null == selection ) selection = "1";
        switch (match) {
            case PRODUCTS: {
                rowsDeleted = db.delete(
                        ProductContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case PRODUCT_BY_ID: {
                rowsDeleted = db.delete(
                        ProductContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Operation update wasn't declared by the developer) : " + uri);
    }

    private static UriMatcher buildUriMatcher() {
        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ProductContract.CONTENT_AUTHORITY;
        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, ProductContract.PATH_COOKING, PRODUCTS);
        matcher.addURI(authority, ProductContract.PATH_COOKING + "/*", PRODUCT_BY_ID);

        return matcher;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCTS: {
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(ProductContract.ProductEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }

            default:
                return super.bulkInsert(uri, values);
        }
    }

}
