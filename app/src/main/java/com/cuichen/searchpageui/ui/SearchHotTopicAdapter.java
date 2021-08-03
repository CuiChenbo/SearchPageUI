package com.cuichen.searchpageui.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cuichen.searchpageui.R;

public class SearchHotTopicAdapter extends RecyclerView.Adapter<SearchHotTopicAdapter.Vh> {

    String data;
    public SearchHotTopicAdapter(String data){
        this.data = data;
    }

    @NonNull
    @Override
    public SearchHotTopicAdapter.Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchHotTopicAdapter.Vh(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHotTopicAdapter.Vh holder, int position) {
        holder.tv.setText("很明显我就是"+data+position);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class Vh extends RecyclerView.ViewHolder{

        private TextView tv;
        public Vh(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
