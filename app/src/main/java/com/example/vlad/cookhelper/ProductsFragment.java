package com.example.vlad.cookhelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vlad.cookhelper.data.Product;
import com.example.vlad.cookhelper.data.ProductContract;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vlad on 10.06.2016.
 */
public class ProductsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final int LOADER_ID = 1;
    private Spinner mProductNames;
    private Spinner mTablewareNames;

    private TextView mGlassTextView;
    private TextView mFglassTextView;
    private TextView mTeaSpTextView;
    private TextView mTableSpTextView;

    private Button mCalcButton;
    private Button mTranslateButton;
    private EditText mInputGrams;
    private EditText mInputTableware;

    private Map<String, Product> productsMap = new HashMap<>();

    private static final String[] PRODUCTS_COLUMNS = {
            ProductContract.ProductEntry.TABLE_NAME + "." + ProductContract.ProductEntry._ID,
            ProductContract.ProductEntry.COLUMN_NAME,
            ProductContract.ProductEntry.COLUMN_FACETED_GLASS,
            ProductContract.ProductEntry.COLUMN_GLASS,
            ProductContract.ProductEntry.COLUMN_TABLESPOON,
            ProductContract.ProductEntry.COLUMN_TEASPOON
    };
    private static final int COL_PRODUCT_ID = 0;
    private static final int COL_PRODUCT_NAME = 1;
    private static final int COL_PRODUCT_FACETED_GLASS = 2;
    private static final int COL_PRODUCT_GLASS = 3;
    private static final int COL_PRODUCT_TABLESPOON = 4;
    private static final int COL_PRODUCT_TEASPOON = 5;

    public ProductsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootview = inflater.inflate(R.layout.products_fragment, container, false);
        mGlassTextView = (TextView) rootview.findViewById(R.id.glass_textView);
        mFglassTextView = (TextView) rootview.findViewById(R.id.fglass_textView);
        mTeaSpTextView = (TextView) rootview.findViewById(R.id.teaspoon_textView);
        mTableSpTextView = (TextView) rootview.findViewById(R.id.tablespoon_textView);


        mTranslateButton = (Button) rootview.findViewById(R.id.calculate_button);
        mTranslateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int request;
                try {
                    request = Integer.valueOf(String.valueOf(mInputGrams.getText()));
                } catch (NumberFormatException e){
                    Toast.makeText(getContext(), "Введите число", Toast.LENGTH_SHORT).show();
                    return;
                }
                Product p = getSelectedProduct();

                Intent intent = new Intent(getContext(), GramsToTablewareActivity.class);
                intent.putExtra(GramsToTablewareActivity.AMOUNT, request);
                intent.putExtra(GramsToTablewareActivity.CURRENT_PRODUCT, p);
                getContext().startActivity(intent);
            }
        });
        mInputGrams = (EditText) rootview.findViewById(R.id.inputGrams);
        mInputGrams.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        mInputTableware = (EditText) rootview.findViewById(R.id.inputTablewareCount);
        mInputTableware.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        mProductNames = (Spinner) rootview.findViewById(R.id.spinner);
        mProductNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Product p = getSelectedProduct();

                if (p != null) {
                    int value = p.getGlass();
                    if (value != -1) {
                        mGlassTextView.setText(value + "");
                    } else {
                        mGlassTextView.setText("");
                    }

                    value = p.getFaceted_glass();
                    if (value != -1) {
                        mFglassTextView.setText(p.getFaceted_glass() + "");
                    } else {
                        mFglassTextView.setText("");
                    }

                    value = p.getTablespoon();
                    if (value != -1) {
                        mTableSpTextView.setText(p.getTablespoon() + "");
                    } else {
                        mTableSpTextView.setText("");
                    }

                    value = p.getTeaspoon();
                    if (value != -1) {
                        mTeaSpTextView.setText(p.getTeaspoon() + "");
                    } else {
                        mTeaSpTextView.setText("");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mTablewareNames = (Spinner) rootview.findViewById(R.id.tablewareSpinner);
        String[] data = { "стаканов", "граненых стаканов", "столовых ложек", "чайных ложек"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_layout,  data);
        adapter.setDropDownViewResource(R.layout.spinner_layout_item);
        mTablewareNames.setAdapter(adapter);

        mTranslateButton = (Button) rootview.findViewById(R.id.transfer_button);
        mTranslateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double count;
                try {
                    count = Double.valueOf(String.valueOf(mInputTableware.getText()));
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Введите число", Toast.LENGTH_SHORT).show();
                    return;
                }
                int tablewareSize = -1;
                String tableware = "";
                Product p = getSelectedProduct();

                View spinner = mTablewareNames.getSelectedView();
                TextView textView = (TextView) spinner.findViewById(R.id.spinnerTarget);
                String name = textView.getText().toString();
                switch (name) {
                    case "стаканов":
                        tablewareSize = p.getGlass();
                        tableware = "стаканов";
                        break;
                    case "граненых стаканов":
                        tablewareSize = p.getFaceted_glass();
                        tableware = "граненых стаканов";
                        break;
                    case "столовых ложек":
                        tablewareSize = p.getTablespoon();
                        tableware = "столовых ложек";
                        break;
                    case "чайных ложек":
                        tablewareSize = p.getTeaspoon();
                        tableware = "чайных ложек";
                        break;
                }

                Intent intent = new Intent(getContext(), TablewareToGramsActivity.class);
                intent.putExtra(TablewareToGramsActivity.COUNT, count);
                intent.putExtra(TablewareToGramsActivity.TABLEWARE_SIZE, tablewareSize);
                intent.putExtra(TablewareToGramsActivity.TABLEWARE, tableware);
                intent.putExtra(TablewareToGramsActivity.PRODUCT_NAME, p.getName());
                getContext().startActivity(intent);
            }
        });

        return rootview;
    }


    private Product getSelectedProduct() {
        View spinner = mProductNames.getSelectedView();
        TextView textView = (TextView) spinner.findViewById(R.id.spinnerTarget);
        String name = textView.getText().toString();
        Product p = productsMap.get(name);

        return p;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri productsUri = ProductContract.ProductEntry.buildProductUri();

        return new CursorLoader(getActivity(),
                productsUri,
                PRODUCTS_COLUMNS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
//        String[] columns = new String[] { PRODUCTS_COLUMNS[COL_PRODUCT_NAME]};
//        int[] to = new int[] { R.id.spinnerTarget };
//
//        SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(getContext(),
//                R.layout.spinner_layout, cursor, columns, to);
//        mAdapter.setDropDownViewResource(R.id.spinnerTarget);
//        mProductNames.setAdapter(mAdapter);

        Product p;
        cursor.moveToNext();
        do {
            p = new Product(cursor.getString(COL_PRODUCT_NAME));
            String value;

            value = cursor.getString(COL_PRODUCT_FACETED_GLASS);
            if (value != null) {
                p.setFaceted_glass(Integer.parseInt(value));
            } else {
                p.setFaceted_glass(-1);
            }

            value = cursor.getString(COL_PRODUCT_GLASS);
            if (value != null) {
                p.setGlass(Integer.parseInt(value));
            } else {
                p.setGlass(-1);
            }

            value = cursor.getString(COL_PRODUCT_TABLESPOON);
            if (value != null) {
                p.setTablespoon(Integer.parseInt(value));
            } else {
                p.setTablespoon(-1);
            }

            value = cursor.getString(COL_PRODUCT_TEASPOON);
            if (value != null) {
                p.setTeaspoon(Integer.parseInt(value));
            } else {
                p.setTeaspoon(-1);
            }

            productsMap.put(p.getName(), p);
        } while (cursor.moveToNext());

        String [] data = new String[productsMap.size()];
        int i = 0;
        for (String key : productsMap.keySet()) {
            data[i++] = key;
        }
        Arrays.sort(data);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_layout, data);
        adapter.setDropDownViewResource(R.layout.spinner_layout_item);
        mProductNames.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
