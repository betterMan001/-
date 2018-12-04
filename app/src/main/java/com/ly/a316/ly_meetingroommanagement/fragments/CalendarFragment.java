package com.ly.a316.ly_meetingroommanagement.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ly.a316.ly_meetingroommanagement.R;


public class CalendarFragment extends Fragment {
    View view;//整个视图

    public CalendarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fr_calendar, container, false);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
