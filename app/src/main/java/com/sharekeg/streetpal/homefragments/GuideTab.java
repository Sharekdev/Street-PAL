package com.sharekeg.streetpal.homefragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.sharekeg.streetpal.Home.HomeActivity;
import com.sharekeg.streetpal.R;

import java.util.Timer;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.zip.Inflater;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class GuideTab extends Fragment {
    private ViewPager mviewpager;
    private viewPagerAdapter adapter;
    private View guideTabView;
    private static int currentPage=0;
    private static int numPages=4;


    private OnFragmentInteractionListener mListener;

    public GuideTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        guideTabView=inflater.inflate(R.layout.fragment_guide_tab,container,false);

        mviewpager=(ViewPager)guideTabView.findViewById(R.id.vpPager);
        adapter=new viewPagerAdapter(getContext());
        mviewpager.setAdapter(adapter);
//        TextView instruct= (TextView) guideTabView.findViewById(R.id.tvinsts);
//        String text = instruct.getText().toString();
//        int i1=text.indexOf("0");
//        int i2=text.indexOf("7");
//
//        final String phone_no=text.substring(i1 , i2);
//
//        instruct.setMovementMethod(LinkMovementMethod.getInstance());
//        instruct.setText(text, TextView.BufferType.SPANNABLE);
//        Spannable mySpannable = (Spannable)instruct.getText();
//        ClickableSpan myClickableSpan = new ClickableSpan() {
//            @Override
//            public void onClick(View widget) {
//                Intent intent = new Intent(Intent.ACTION_CALL);
//                intent.setData(Uri.parse("tel:"+phone_no));
//                startActivity(intent); }
//        };
//        mySpannable.setSpan(myClickableSpan, i1, i2 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

//        String Nazra=getActivity().getResources().getString(R.string.Nazra);
//        String


//        CircleIndicator indicator = (CircleIndicator) mviewpager.findViewById(R.id.indicator);
//        indicator.setViewPager(mviewpager);
//        adapter.registerDataSetObserver(indicator.getDataSetObserver());
        // Inflate the layout for this fragment
        return guideTabView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
