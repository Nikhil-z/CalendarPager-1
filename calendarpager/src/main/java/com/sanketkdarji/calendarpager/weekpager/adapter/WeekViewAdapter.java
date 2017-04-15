package com.sanketkdarji.calendarpager.weekpager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sanketkdarji.calendarpager.R;
import com.sanketkdarji.calendarpager.model.CalendarDay;
import com.sanketkdarji.calendarpager.util.DayUtils;
import com.sanketkdarji.calendarpager.weekpager.view.WeekDayViewPager;
import com.sanketkdarji.calendarpager.weekpager.view.WeekView;

import java.util.ArrayList;

public class WeekViewAdapter extends RecyclerView.Adapter<WeekViewAdapter.WeekViewHolder> implements
    WeekView.OnDayClickListener {

  private Context mContext;
  private CalendarDay mStartDay;
  private CalendarDay mEndDay;
  private CalendarDay mFirstShowDay;
  private WeekDayViewPager mWeekDayViewPager;
  private int mSelectPosition;
  private ArrayList<CalendarDay> mAbleCalendarDays;

  /**
   * week view value
   */
  private int mTextNormalColor;
  private int mTextSelectColor;
  private int mTextUnableColor;
  private int indicatorColor;

  public WeekViewAdapter(Context context, WeekDayViewPager viewPager) {
    mContext = context;
    mWeekDayViewPager = viewPager;
    mAbleCalendarDays = new ArrayList<>();
    updateColor();
  }

  private void updateColor() {
    indicatorColor = mContext.getResources().getColor(R.color.color_18ffff);
    mTextSelectColor = mContext.getResources().getColor(android.R.color.white);
    mTextNormalColor = mContext.getResources().getColor(R.color.text_color_normal);
    mTextUnableColor = mContext.getResources().getColor(R.color.text_color_light);
  }

  public void setData(CalendarDay startDay, CalendarDay endDay, ArrayList<CalendarDay> calendarDayArrayList) {
    mStartDay = startDay;
    mEndDay = endDay;
    mFirstShowDay = DayUtils.calculateFirstShowDay(mStartDay);
    if (calendarDayArrayList != null) {
      mAbleCalendarDays.clear();
      mAbleCalendarDays.addAll(calendarDayArrayList);
    }
    notifyDataSetChanged();
  }

  public CalendarDay getFirstShowDay() {
    return mFirstShowDay;
  }

  @Override
  public int getItemCount() {
    if (mStartDay == null || mEndDay == null) {
      return 0;
    }
    int weekCount = DayUtils.calculateWeekCount(mStartDay, mEndDay);
    return weekCount;
  }

  @Override
  public WeekViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    WeekView weekView = new WeekView(mContext, mTextNormalColor, mTextSelectColor, mTextUnableColor, indicatorColor);
    int width = mContext.getResources().getDisplayMetrics().widthPixels;
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,
        ViewGroup.LayoutParams.MATCH_PARENT);
    weekView.setLayoutParams(params);
    WeekViewHolder viewHolder = new WeekViewHolder(weekView, mFirstShowDay, mWeekDayViewPager,
            mAbleCalendarDays, this);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(final WeekViewHolder viewHolder, final int position) {
    viewHolder.bind(position, mSelectPosition);
  }

  @Override public void onDayClick(WeekView simpleMonthView, CalendarDay calendarDay,
      int position) {
    mSelectPosition = position;
    notifyDataSetChanged();
  }

  public static class WeekViewHolder extends RecyclerView.ViewHolder {

    private WeekView mWeekView;


    public WeekViewHolder(View view,CalendarDay firstShowDay, WeekDayViewPager viewPager, ArrayList<CalendarDay> mAbleCalendarDays, WeekViewAdapter weekViewAdapter) {
      super(view);
      mWeekView = (WeekView)view;
      mWeekView.setDays(firstShowDay);
      mWeekView.setOnDayClickListener(viewPager);
      mWeekView.setOnAdapterDayClickListener(weekViewAdapter);
      mWeekView.setTextSize(view.getContext().getResources().getDimension(R.dimen.si_default_text_size));
      mWeekView.setAbleDates(mAbleCalendarDays);
    }

    public void bind(int position, int selectPosition) {
      mWeekView.setPosition(position);
      mWeekView.setSelectPosition(selectPosition);
    }

  }

  public void setTextNormalColor(int mTextNormalColor) {
    this.mTextNormalColor = mTextNormalColor;
  }

  public void setTextSelectColor(int mTextSelectColor) {
    this.mTextSelectColor = mTextSelectColor;
  }

  public void setTextUnableColor(int mTextUnableColor) {
    this.mTextUnableColor = mTextUnableColor;
  }

  public void setIndicatorColor(int indicatorColor) {
    this.indicatorColor = indicatorColor;
  }
}
