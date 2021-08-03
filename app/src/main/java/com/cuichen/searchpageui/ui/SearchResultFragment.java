package com.cuichen.searchpageui.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cuichen.searchpageui.R;

public class SearchResultFragment extends Fragment {
    public static SearchResultFragment newInstance(String label) {
        Bundle args = new Bundle();
        args.putString("label", label);
        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        String label = getArguments().getString("label");
        RecyclerView rv = getView().findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv.setAdapter(new SearchHotTopicAdapter(label));
    }

}