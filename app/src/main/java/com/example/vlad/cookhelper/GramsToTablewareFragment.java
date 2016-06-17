package com.example.vlad.cookhelper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vlad.cookhelper.data.Product;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link GramsToTablewareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GramsToTablewareFragment extends Fragment {
    private Product mProduct;
    private int mRequest;

    private TextView mRequestTextView;
    private TextView mRequestProductTextView;
    private TextView mGlassTextView;
    private TextView mFglassTextView;
    private TextView mTeaSpTextView;
    private TextView mTableSpTextView;

    public GramsToTablewareFragment() {
        // Required empty public constructor
    }

    public static GramsToTablewareFragment newInstance() {
        GramsToTablewareFragment fragment = new GramsToTablewareFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mRequest = arguments.getInt(GramsToTablewareActivity.AMOUNT);
            mProduct = arguments.getParcelable(GramsToTablewareActivity.CURRENT_PRODUCT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.gramstableware_fragment, container, false);
        mRequestTextView = (TextView) rootview.findViewById(R.id.request_textView);
        mRequestTextView.setText(mRequest + "");

        mRequestProductTextView = (TextView) rootview.findViewById(R.id.request_product_textView);
        mRequestProductTextView.setText(mProduct.getName());

        mGlassTextView = (TextView) rootview.findViewById(R.id.tablewareresult_glass_textView);
        mFglassTextView = (TextView) rootview.findViewById(R.id.tablewareresult_fglass_textView);
        mTeaSpTextView = (TextView) rootview.findViewById(R.id.tablewareresult_teaspoon_textView);
        mTableSpTextView = (TextView) rootview.findViewById(R.id.tablewareresult_tablespoon_textView);

        doCalculations();

        return rootview;
    }

    public void doCalculations() {
        double value;
        if (mProduct.getGlass() != -1) {
            value = Math.round((mRequest * 1.0 / mProduct.getGlass()) * 100) * 1.0 / 100;
            mGlassTextView.setText(value + "");
        }
        if (mProduct.getFaceted_glass() != -1) {
            value = Math.round((mRequest * 1.0 / mProduct.getFaceted_glass()) * 100) * 1.0 / 100;
            mFglassTextView.setText(value + "");
        }
        if (mProduct.getTablespoon() != -1) {
            value = Math.round((mRequest * 1.0 / mProduct.getTablespoon()) * 100) * 1.0 / 100;
            mTableSpTextView.setText(value + "");
        }
        if (mProduct.getTeaspoon() != -1) {
            value = Math.round((mRequest * 1.0 / mProduct.getTeaspoon()) * 100) * 1.0 / 100;
            mTeaSpTextView.setText(value + "");
        }
    }

}
