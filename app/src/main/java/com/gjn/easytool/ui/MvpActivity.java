package com.gjn.easytool.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gjn.easytool.R;
import com.gjn.easytool.easymvp.BindPresenter;
import com.gjn.easytool.easymvp.BindPresenters;
import com.gjn.easytool.easymvp.base.BaseMvpActivity;
import com.gjn.easytool.mvp.TestMvp.TestMvpContract;
import com.gjn.easytool.mvp.TestMvp.TestMvpPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gjn
 * @time 2019/5/23 15:32
 */

@BindPresenters({TestMvpPresenter.class})
public class MvpActivity extends BaseMvpActivity implements TestMvpContract.view{

    @BindPresenter
    TestMvpPresenter testMvpPresenter;

    ViewPager vp;
    List<MvpFragment> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvp;
    }

    @Override
    protected void initView() {
//        testMvpPresenter.onClick();

        list = new ArrayList<>();
        list.add(new MvpFragment());
        list.add(new MvpFragment());
        list.add(new MvpFragment());
        list.add(new MvpFragment());

        vp = findViewById(R.id.vp);

        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
    }

    @Override
    protected void initData() {
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testMvpPresenter.onClick();
            }
        });
    }

    @Override
    public void success() {
        showToast("点击成功");
    }
}
