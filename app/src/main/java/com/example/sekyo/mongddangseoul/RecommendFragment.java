package com.example.sekyo.mongddangseoul;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sekyo on 2017-08-12.
 */

public class RecommendFragment extends Fragment {
    View view;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news_feed, container, false);

        return view;
    }
}
