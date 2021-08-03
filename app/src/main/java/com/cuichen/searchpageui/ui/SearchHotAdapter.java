package com.cuichen.searchpageui.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.cuichen.searchpageui.R;
import com.cuichen.searchpageui.SearchActivity;
import com.cuichen.searchpageui.SearchResultActivity;
import com.cuichen.searchpageui.view.flow_layout.FlowLayout;
import com.cuichen.searchpageui.view.flow_layout.TagAdapter;

import java.util.List;

public class SearchHotAdapter extends TagAdapter<String> {
    public SearchHotAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    public View getView(FlowLayout parent, int position, String s) {
        View view = View.inflate(parent.getContext() , R.layout.item_search_flow , null);
        TextView tv = view.findViewById(R.id.tv);
        tv.setText(s);
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                parent.getContext().startActivity(new Intent(parent.getContext() , SearchResultActivity.class));
//            }
//        });
        return view;
    }
}
