package com.example.vlad.cookhelper;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TablewareToGramsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TablewareToGramsFragment extends Fragment {
    private int tableware_size;
    private double mCount;
    private String mTableware;
    private String mProduct_name;

    private TextView mTablewareTextView;
    private TextView mGramsTextView;
    private TextView mProductTextView;

    public TablewareToGramsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment TablewareToGramsFragment.
     */
    public static TablewareToGramsFragment newInstance() {
        TablewareToGramsFragment fragment = new TablewareToGramsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mCount = arguments.getDouble(TablewareToGramsActivity.COUNT);
            tableware_size = arguments.getInt(TablewareToGramsActivity.TABLEWARE_SIZE);
            mTableware = arguments.getString(TablewareToGramsActivity.TABLEWARE);
            mProduct_name = arguments.getString(TablewareToGramsActivity.PRODUCT_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview =  inflater.inflate(R.layout.tablewaregrams_fragment, container, false);

        mTablewareTextView = (TextView) rootview.findViewById(R.id.tableware_size_textView);
        mTablewareTextView.setText(mCount + " " + mTableware + " эквивалентно:");

        mGramsTextView = (TextView) rootview.findViewById(R.id.grams_textview);
        double grams = mCount * tableware_size;
        mGramsTextView.setText(grams + " грамм");

        mProductTextView = (TextView) rootview.findViewById(R.id.tableware_product_textView);
        mProductTextView.setText(mProduct_name);

        return rootview;
    }
}
