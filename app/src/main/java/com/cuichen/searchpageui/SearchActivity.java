package com.cuichen.searchpageui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cuichen.searchpageui.ui.SearchHotAdapter;
import com.cuichen.searchpageui.utils.SearchHistoryUtils;
import com.cuichen.searchpageui.view.FoldFlowLayout;
import com.cuichen.searchpageui.view.flow_layout.FlowLayout;
import com.cuichen.searchpageui.view.flow_layout.TagAdapter;
import com.cuichen.searchpageui.view.flow_layout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private List<String> datas  = Arrays.asList("CCB","ICBC","夏日美食券夏日美食券","夏日盲盒","ABS","XXOO","玩车");
    private FoldFlowLayout flowRecord;
    private TagFlowLayout flowHot;
    private RecyclerView rvHot;
    private EditText etSearch;
    private ImageView ivDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        etSearch = findViewById(R.id.et_search);
        ivDelete = findViewById(R.id.iv_delete_text);
        flowRecord = findViewById(R.id.flow_record);
        flowHot = findViewById(R.id.flow_hot);
        rvHot = findViewById(R.id.rv_hot_topic);
        findViewById(R.id.tvClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initSearch();
        initFlow();
        initRecy();
    }

    private void initSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ivDelete.setVisibility(TextUtils.isEmpty(charSequence) ? View.INVISIBLE : View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        etSearch.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideSoftKeyboard();
                String search = textView.getText().toString();
                goSearch(search);
                SearchHistoryUtils.saveSearchHistory(search, SearchActivity.this);
                notifyHistoryData();
                return true;
            }
            return false;
        });
    }

    private void notifyHistoryData() {
        List<String> searchHistory = SearchHistoryUtils.getSearchHistory(SearchActivity.this);
        if (searchHistory != null && searchHistory.size() > 0) {
            initZFlowLayout(searchHistory);
        }
    }

    private void goSearch(String search){
        Intent intent = new Intent(SearchActivity.this , SearchResultActivity.class);
        intent.putExtra(SearchResultActivity.SEARCHRESULTTAG , search);
        startActivity(intent);
    }

    private void initRecy() {
        rvHot.setNestedScrollingEnabled(false);
        rvHot.setHasFixedSize(true);
        rvHot.setLayoutManager(new LinearLayoutManager(this));
        rvHot.setAdapter(new RecyclerView.Adapter<Vh>() {
            @NonNull
            @Override
            public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_search_topic, parent, false);
                return new Vh(view);
            }

            @Override
            public void onBindViewHolder(@NonNull Vh holder, int position) {
               holder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       goSearch("热门话题");
                   }
               });
            }

            @Override
            public int getItemCount() {
                return 10;
            }
        });
    }

    class Vh extends RecyclerView.ViewHolder{

        public Vh(@NonNull View itemView) {
            super(itemView);
        }
    }


    private void initFlow() {
        TagAdapter hotAdapter = new SearchHotAdapter(datas);
        flowHot.setAdapter(hotAdapter);
        flowHot.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                goSearch(datas.get(position));
                return false;
            }
        });
        notifyHistoryData();
    }

    private List<View> mViewList = new ArrayList<>();
    private LayoutInflater inflater ;

    private void initZFlowLayout(List<String> searchHistory) {
        inflater = LayoutInflater.from(this);
        mViewList.clear();
        for (int i = 0; i < searchHistory.size(); i++) {
            View view = inflater.inflate(R.layout.item_search_flow, flowRecord, false);
            TextView textView = view.findViewById(R.id.tv);
            textView.setText(searchHistory.get(i));
            mViewList.add(view);
        }
        flowRecord.setChildren(mViewList);

        flowRecord.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                flowRecord.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int lineCount = flowRecord.getLineCount();
                int twoLineViewCount = flowRecord.getTwoLineViewCount();
                if (lineCount > 2) {
                    Log.i("TAG", "onGlobalLayout: 初始化展开");
                    foldFlow(searchHistory, twoLineViewCount);
                }
            }
        });

        flowRecord.setOnTagClickListener((view, position) -> {
            goSearch(searchHistory.get(position));
        });
    }

    //展开
    private void unfoldFlow(List<String> searchHistory, final int twoLineViewCount) {
        mViewList.clear();
        for (int i = 0; i < searchHistory.size(); i++) {
            View view = inflater.inflate(R.layout.item_search_flow, flowRecord, false);
            TextView textView = view.findViewById(R.id.tv);
            textView.setText(searchHistory.get(i));
            mViewList.add(view);
        }
        View view = inflater.inflate(R.layout.item_search_flow, flowRecord, false);
        TextView textView = view.findViewById(R.id.tv);
        textView.setText("↑");
        textView.setOnClickListener(v -> foldFlow(searchHistory, twoLineViewCount));
        mViewList.add(view);
        flowRecord.setChildren(mViewList);
    }

    //折叠
    private void foldFlow(List<String> searchHistory, final int twoLineViewCount) {
        mViewList.clear();
        for (int i = 0; i < twoLineViewCount; i++) {
            View view = inflater.inflate(R.layout.item_search_flow, flowRecord, false);
            TextView textView = view.findViewById(R.id.tv);
            textView.setText(searchHistory.get(i));
            mViewList.add(view);
        }
        View view = inflater.inflate(R.layout.item_search_flow_more, flowRecord, false);
        view.setOnClickListener(v -> unfoldFlow(searchHistory, twoLineViewCount));
        view.setTag("展开");
        mViewList.add(view);
        flowRecord.setChildren(mViewList);
        flowRecord.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                flowRecord.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int lineCount = flowRecord.getLineCount();
                int twoLineViewCount = flowRecord.getTwoLineViewCount();
                if (lineCount > 2) {
                    Log.i("TAG", "onGlobalLayout: 折叠再次展开");
                    foldFlow(searchHistory, twoLineViewCount - 1);
                }
            }
        });
    }

    private void hideSoftKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                View currentFocus = getCurrentFocus();
                if (currentFocus != null) {
                    imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0); //强制隐藏键盘
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}