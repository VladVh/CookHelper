package com.example.vlad.cookhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class TablewareToGramsActivity extends AppCompatActivity {
    public static final String COUNT = "count";
    public static final String TABLEWARE = "tableware";
    public static final String TABLEWARE_SIZE = "tableware_size";
    public static final String PRODUCT_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablewaregrams_activity);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putDouble(COUNT, getIntent().getDoubleExtra(COUNT, -1));
            arguments.putInt(TABLEWARE_SIZE, getIntent().getIntExtra(TABLEWARE_SIZE, -1));
            arguments.putString(TABLEWARE, getIntent().getStringExtra(TABLEWARE));
            arguments.putString(PRODUCT_NAME, getIntent().getStringExtra(PRODUCT_NAME));

            TablewareToGramsFragment fragment = TablewareToGramsFragment.newInstance();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.grams_results_container, fragment).commit();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home) {
            Intent intent = NavUtils.getParentActivityIntent(this);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
