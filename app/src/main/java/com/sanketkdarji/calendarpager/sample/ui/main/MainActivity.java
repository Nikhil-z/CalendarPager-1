package com.sanketkdarji.calendarpager.sample.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sanketkdarji.calendarpager.model.CalendarDay;
import com.sanketkdarji.calendarpager.sample.R;
import com.sanketkdarji.calendarpager.sample.ui.base.BaseActivity;
import com.sanketkdarji.calendarpager.util.DayUtils;
import com.sanketkdarji.calendarpager.weekpager.adapter.WeekPagerAdapter;
import com.sanketkdarji.calendarpager.weekpager.adapter.WeekViewAdapter;
import com.sanketkdarji.calendarpager.weekpager.view.WeekDayViewPager;
import com.sanketkdarji.calendarpager.weekpager.view.WeekRecyclerView;

import java.util.Calendar;

public class MainActivity extends BaseActivity implements WeekDayViewPager.DayScrollListener {

    private WeekDayViewPager mViewPagerContent;
    private WeekRecyclerView mWeekRecyclerView;
    private TextView mTextView;

    private SimplePagerAdapter mPagerAdapter;
    private WeekViewAdapter mWeekViewAdapter;
    private static final int OFFSCREEN_PAGE_LIMIT = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mViewPagerContent = (WeekDayViewPager) findViewById(R.id.view_pager);
        mWeekRecyclerView = (WeekRecyclerView) findViewById(R.id.header_recycler_view);
        mTextView = (TextView) findViewById(R.id.text_day_label);

        setUpPager();
        setUpData();
    }

    private void setUpPager() {
        mPagerAdapter = new SimplePagerAdapter(getSupportFragmentManager());
        mViewPagerContent.setOffscreenPageLimit(OFFSCREEN_PAGE_LIMIT);
        mViewPagerContent.setAdapter(mPagerAdapter);
        mViewPagerContent.setWeekRecyclerView(mWeekRecyclerView);
        mViewPagerContent.setDayScrollListener(this);
        mWeekViewAdapter = new WeekViewAdapter(this, mViewPagerContent);
        mWeekViewAdapter.setTextNormalColor(getResources().getColor(android.R.color.darker_gray));
        mWeekRecyclerView.setAdapter(mWeekViewAdapter);
    }

    private void setUpData() {
        CalendarDay startDay = new CalendarDay(2017, 1, 1);
        CalendarDay endDay = new CalendarDay(2017, 12, 31);

        mWeekViewAdapter.setData(startDay, endDay, null);
        mPagerAdapter.setData(startDay, endDay);
        mViewPagerContent.setCurrentPosition(
                DayUtils.calculateDayPosition(mWeekViewAdapter.getFirstShowDay(), new CalendarDay(
                        Calendar.getInstance())));
    }

    @Override
    public void onDayPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mTextView.setText(DayUtils.formatEnglishTime(mPagerAdapter.getDays().get(position).getTime()));
    }

    @Override
    public void onDayPageSelected(int position) {

    }

    @Override
    public void onDayPageScrollStateChanged(int state) {

    }

    public class SimplePagerAdapter extends WeekPagerAdapter {

        public SimplePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override protected Fragment createFragmentPager(int position) {
            return SimpleFragment.newInstance(mDays.get(position));
        }
    }

    public static class SimpleFragment extends Fragment {

        TextView mText;

        private CalendarDay mCalendarDay;

        public static SimpleFragment newInstance(CalendarDay calendarDay) {
            SimpleFragment simpleFragment = new SimpleFragment();
            simpleFragment.mCalendarDay = calendarDay;
            return simpleFragment;
        }

        @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.simple_fragment, container, false);
        }

        @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            mText = (TextView) view.findViewById(R.id.text);
            mText.setText("This is at: " + DayUtils.formatEnglishTime(mCalendarDay.getTime()));
        }
    }
}
