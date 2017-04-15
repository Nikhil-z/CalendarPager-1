Forked from [https://github.com/ToDou/CalendarPager](https://github.com/ToDou/CalendarPager)

# CalendarPager
This is one horizontal calendar with viewPager.

The header is create by recyclerview. Every item draw based on week by week.You can slide the week recyclerview to next week or pref. I write it because I have used in my work.You just should input your first and last day. Then the calendar will be create one by one.

Maybe not perfect. I will thanks for your suggestion.

Screeshot
====

* WeekRecyclerView

![](https://bitbucket.org/etymon-android/calendarpager/downloads/screenshot.gif)

Installation
====
First add [calendarpager.aar](https://bitbucket.org/etymon-android/calendarpager/downloads/calendarpager-0.1.0.zip) file to libs folder and ensure you have set libs as flatDir in build.gradle.

```groovy
allprojects {
   repositories {
      jcenter()
      flatDir {
        dirs 'libs'
      }
   }
}
```


```groovy
dependencies {
    compile(name:'calendarpager', ext:'aar')
}
```

Just Do
====

###WeekRecyclerView

First you should add the layout WeekRecyclerView and WeekDayViewPager. @layout/view_week_label and text_day_label can add by yourself.
```xml
  <include layout="@layout/view_week_label"/>
  
    <com.test.tudou.library.WeekPager.view.WeekRecyclerView
        android:id="@+id/header_recycler_view"
        android:layout_width="match_parent"
        android:layout_height="48dp"
        android:clipToPadding="false"
        android:scrollbars="none"/>

    <TextView
        android:id="@+id/text_day_label"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="@dimen/default_padding"
        tools:text="sjidg"
        android:layout_gravity="center_horizontal"
        />

    <com.test.tudou.library.WeekPager.view.WeekDayViewPager
      android:id="@+id/view_pager"
      android:layout_width="match_parent"
      android:layout_height="0dp"
      android:layout_weight="1"
      android:background="#DDDDDD"/>
    
```
Then to init the adapter and viewpager. Also you can add the setDayScrollListener to change the text of text_day_label textView to show the day.
```java
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
    
```
And you should create one adapter to extends WeekPagerAdapter. Return one Fragment by createFragmentPager(int position). 

Like This:
```java
  public class SimplePagerAdapter extends WeekPagerAdapter {

    public SimplePagerAdapter(FragmentManager fm) {
      super(fm);
    }
    
    @Override protected Fragment createFragmentPager(int position) {
      return SimpleFragment.newInstance(mDays.get(position));
    }
  }
```

Last you can load the data.
```java
  private void setUpData() {
    ArrayList<CalendarDay> reachAbleDays = new ArrayList<>();
    reachAbleDays.add(new CalendarDay(2015, 5, 1));
    reachAbleDays.add(new CalendarDay(2015, 5, 4));
    reachAbleDays.add(new CalendarDay(2015, 5, 6));
    reachAbleDays.add(new CalendarDay(2015, 5, 20));
    mWeekViewAdapter.setData(reachAbleDays.get(0), reachAbleDays.get(reachAbleDays.size() - 1), null);
    mPagerAdapter.setData(reachAbleDays.get(0), reachAbleDays.get(reachAbleDays.size() - 1));
    mViewPagerContent.setCurrentPosition(DayUtils.calculateDayPosition(mWeekViewAdapter.getFirstShowDay(), new CalendarDay(2015, 5, 6)));
  }
    
```
You can use 
```java
    mViewPagerContent.setCurrentPosition(position);
```
to change the current pager

If you want one color to distinguish some days. You can add reachAbleDays. And set the color by setTextUnableColor
```java
    ...
    mWeekViewAdapter.setData(reachAbleDays.get(0), reachAbleDays.get(reachAbleDays.size() - 1), reachAbleDays);
    ...
    
```