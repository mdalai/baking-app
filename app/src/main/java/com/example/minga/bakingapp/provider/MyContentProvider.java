package com.example.minga.bakingapp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.minga.bakingapp.database.AppDatabase;
import com.example.minga.bakingapp.database.RecipeDao;

/**
 * Created by minga on 7/20/2018.
 */

public class MyContentProvider extends ContentProvider {
    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.minga.bakingapp";

    // The base content URI = "content://" + <authority>
    //public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    /** The URI for the [Recipes] table. */
    public static final Uri URI_RECIPES = Uri.parse(
            "content://" + AUTHORITY + "/recipes");

    // Define final integer constants for the directory of plants and a single item.
    /** The match code for some items in the [Recipes] table. */
    private static final int CODE_RECIPES_DIR = 100;
    /** The match code for an item in the [Recipes]  table. */
    private static final int CODE_RECIPES_ITEM =101;

    /** The URI matcher. */
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        MATCHER.addURI(AUTHORITY, "recipes", CODE_RECIPES_DIR);
        MATCHER.addURI(AUTHORITY, "recipes/*", CODE_RECIPES_ITEM);
    }


    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        final int code = MATCHER.match(uri);
        if (code == CODE_RECIPES_DIR || code == CODE_RECIPES_ITEM) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            RecipeDao recipeDao = AppDatabase.getInstance(context).recipeDao ();
            final Cursor cursor;
            if (code == CODE_RECIPES_DIR) {
                cursor = recipeDao.selectAll();
            } else {
                cursor = recipeDao.selectById(( int ) ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_RECIPES_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + ".recipes";
            case CODE_RECIPES_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + ".recipes";
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
