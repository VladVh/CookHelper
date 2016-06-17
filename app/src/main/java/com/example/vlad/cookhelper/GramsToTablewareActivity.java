package com.example.vlad.cookhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by Vlad on 13.06.2016.
 */
public class GramsToTablewareActivity extends AppCompatActivity {
    public static final String AMOUNT = "data";
    public static final String CURRENT_PRODUCT = "product";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gramstableware_activity);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putInt(AMOUNT, getIntent().getIntExtra(AMOUNT, -1));
            arguments.putParcelable(CURRENT_PRODUCT, getIntent().getParcelableExtra(CURRENT_PRODUCT));

            GramsToTablewareFragment fragment = GramsToTablewareFragment.newInstance();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.tableware_results_container, fragment).commit();
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
