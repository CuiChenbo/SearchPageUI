package com.cuichen.searchpageui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.cuichen.searchpageui.ui.SearchResultFragment;
import com.cuichen.searchpageui.utils.SearchHistoryUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    public static final String SEARCHRESULTTAG = "SearchResultTag";
    private List<String> tabs = Arrays.asList("内容", "用户");
    private TabLayout tabLayout;
    private ViewPager viewpage;
    private EditText etSearch;
    private ImageView ivDelete;
    private String searchContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        etSearch = findViewById(R.id.et_search);
        ivDelete = findViewById(R.id.iv_delete_text);
        tabLayout = findViewById(R.id.tab_layout);
        viewpage = findViewById(R.id.viewpage);

        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initSearch();
        initTabViewPage();
    }

    private void initSearch() {
        searchContent = getIntent().getStringExtra(SEARCHRESULTTAG);
        etSearch.setText(searchContent);

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
                SearchHistoryUtils.saveSearchHistory(textView.getText().toString(), SearchResultActivity.this);
                return true;
            }
            return false;
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


    private List<SearchResultFragment> tabFragmentList = new ArrayList<>();

    private void initTabViewPage() {
        viewpage.setOffscreenPageLimit(tabs.size());
        //添加tab
        for (int i = 0; i < tabs.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabs.get(i)));
            tabFragmentList.add(SearchResultFragment.newInstance(tabs.get(i)+searchContent));
        }

        viewpage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return tabFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return tabFragmentList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabs.get(position);
            }
        });

        //设置TabLayout和ViewPager联动
        tabLayout.setupWithViewPager(viewpage, false);
    }
}