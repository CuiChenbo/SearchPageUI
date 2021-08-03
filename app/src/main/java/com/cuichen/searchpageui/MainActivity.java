package com.cuichen.searchpageui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cuichen.searchpageui.ui.SearchResultFragment;
import com.cuichen.searchpageui.view.CompilePopupWindow;
import com.cuichen.searchpageui.view.HomeAddPopupWindow;
import com.cuichen.searchpageui.view.UpDownSlideEventViewPage;
import com.cuichen.searchpageui.view.UpRollView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> tabs = Arrays.asList("推荐","热门","精选","活动");
    private TabLayout tabLayout;
    private UpDownSlideEventViewPage viewpage;
    private ImageView ivAdd;
    private RelativeLayout layout_search;
    private UpRollView upView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        upView = findViewById(R.id.up_view);
        tabLayout = findViewById(R.id.tab_layout);
        viewpage = findViewById(R.id.viewpage);
        layout_search = findViewById(R.id.layout_search);
        ivAdd = findViewById(R.id.iv_add);
        initTabViewPage();
        initList();
        initUpRollView();
    }

    private void initUpRollView() {
        List<View> views = new ArrayList<>();
        for (int i = 0; i < tabs.size(); i++) { //一次遍历两条数据
            View v = View.inflate(this,R.layout.item_uproll_search,null);
            TextView tv1 = v.findViewById(R.id.tv);
            tv1.setText("搜索热词： "+tabs.get(i));
            views.add(v);
        }
        upView.setViews(views);
        upView.setOnItemClickListener(new UpRollView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                startActivity(new Intent(MainActivity.this , SearchActivity.class));
            }
        });
    }

    private void initList() {
        layout_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , SearchActivity.class));
            }
        });
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPopWindow(ivAdd);
            }
        });
        viewpage.setUpDownSlideListEvent(new UpDownSlideEventViewPage.UpDownSlideListEvent() {
            @Override
            public void onUp() {
                layout_search.setVisibility(View.GONE);
            }

            @Override
            public void onDown() {
                layout_search.setVisibility(View.VISIBLE);
            }
        });
    }

    private List<SearchResultFragment> tabFragmentList = new ArrayList<>();
    private void initTabViewPage() {
        viewpage.setOffscreenPageLimit(tabs.size());
        //添加tab
        for (int i = 0; i < tabs.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabs.get(i)));
            tabFragmentList.add(SearchResultFragment.newInstance(tabs.get(i)));
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
        tabLayout.setupWithViewPager(viewpage,false);
    }


    private void initPopWindow(View view) {
       new HomeAddPopupWindow(this , view).show();
    }

}