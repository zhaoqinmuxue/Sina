package org.aoli.weibo.delegates.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.aoli.weibo.R;
import org.aoli.weibo.delegates.BaseDelegate;
import org.aoli.weibo.delegates.main.hot.HotDelegate;
import org.aoli.weibo.delegates.main.index.IndexDelegate;
import org.aoli.weibo.delegates.main.message.MessageDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainDelegate extends BaseDelegate {
    @BindView(R.id.main_tb)
    Toolbar mToolBar;
    @BindView(R.id.main_drawer)
    DrawerLayout mDrawerLayout;
    @OnClick(R.id.main_bt_menu)
    void onClickMenu(){
        mDrawerLayout.openDrawer(GravityCompat.START);
    }
    @BindView(R.id.main_bt_search)
    ImageButton mBtSearch;
    @BindView(R.id.main_bt_title)
    Button mBtTitle;
    @BindView(R.id.main_bot)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.main_nav)
    NavigationView mNavigationView;
    @BindView(R.id.main_vp)
    ViewPager mViewPager;

    private final List<BaseDelegate> DELEGATES = new ArrayList<>();
    private final List<String> TITLES = new ArrayList<>();
    private int mCurrentPage = -1;

    @Override
    protected Object setLayout() {
        return R.layout.delegate_main;
    }

    @Override
    protected void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        init();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setStatusBarColor(Color.WHITE);
    }

    private void init(){
        TITLES.add("全部微博");
        TITLES.add("消息");
        TITLES.add("热门");
        DELEGATES.add(new IndexDelegate());
        DELEGATES.add(new MessageDelegate());
        DELEGATES.add(new HotDelegate());
        mViewPager.setOffscreenPageLimit(DELEGATES.size()-1);
        mViewPager.setAdapter(new MainFragmentPagerAdapter(getFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) {
                mBottomNavigationView.setSelectedItemId(mBottomNavigationView.getMenu().getItem(position).getItemId());
                mBtTitle.setText(TITLES.get(position));
                mCurrentPage = position;
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bot_main:
                        mViewPager.setCurrentItem(0);
                        mBtTitle.setText(TITLES.get(0));
                        mCurrentPage = 0;
                        break;
                    case R.id.bot_message:
                        mViewPager.setCurrentItem(1);
                        mBtTitle.setText(TITLES.get(1));
                        mCurrentPage = 1;
                        break;
                    case R.id.bot_hot:
                        mViewPager.setCurrentItem(2);
                        mBtTitle.setText(TITLES.get(2));
                        mCurrentPage = 2;
                        break;
                }
                return true;
            }
        });
    }

    class MainFragmentPagerAdapter extends FragmentPagerAdapter{
        MainFragmentPagerAdapter(FragmentManager fm){
            super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return DELEGATES.get(position);
        }

        @Override
        public int getCount() {
            return DELEGATES.size();
        }
    }
}
