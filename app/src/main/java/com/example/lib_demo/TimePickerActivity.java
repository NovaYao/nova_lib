package com.example.lib_demo;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import net.nova123.lib.ui.timepicker.NumericWheelAdapter;
import net.nova123.lib.ui.timepicker.OnWheelChangedListener;
import net.nova123.lib.ui.timepicker.WheelView;
import net.nova123.lib.utils.ViewUtils;
import android.os.Bundle;

public class TimePickerActivity extends BaseActivity {

	public static final int DAY_SELECTED = 11;
	public static final int WEEK_SELECTED = 12;
	public static final int MONTH_SELECTED = 13;
	public static final int YEAR_SELECTED = 14;

	public static final int DAY_SELECT_STYLE = 1;
	public static final int ALL_SELECT_STYLE = 2;

	private static int START_YEAR = 2014, END_YEAR = 2020;

	private int textSize = 18;

	private Calendar mCalendar = Calendar.getInstance();

	private WheelView wv_year;
	private WheelView wv_month;
	private WheelView wv_day;
	private WheelView wv_week;
	private int style;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_picker);

		textSize = ViewUtils.dipTopx(this, textSize);

		pickerInit();
	}

	// -----------------------------------------------------------------------------------------------------
	private void pickerInit() {
		initWheelDate(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DATE), mCalendar.get(Calendar.WEEK_OF_YEAR));
	}

	private void initWheelDate(int year, int month, int day, int week) {
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		wv_year = (WheelView) findViewById(R.id.year);
		wv_month = (WheelView) findViewById(R.id.month);
		wv_day = (WheelView) findViewById(R.id.day);
		wv_week = (WheelView) findViewById(R.id.week);

		wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));
		wv_year.setCyclic(false);
		wv_year.setCurrentItem(year - START_YEAR);

		wv_month.setAdapter(new NumericWheelAdapter(1, 12));
		wv_month.setCyclic(false);
		wv_month.setCurrentItem(month);

		wv_week.setAdapter(new NumericWheelAdapter(1, 52));
		wv_week.setCyclic(false);
		wv_week.setCurrentItem(week - 1);

		wv_day.setCyclic(true);
		if (list_big.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 30));
		} else {
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				wv_day.setAdapter(new NumericWheelAdapter(1, 29));
			else
				wv_day.setAdapter(new NumericWheelAdapter(1, 28));
		}
		wv_day.setCurrentItem(day - 1);

		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + START_YEAR;
				if (list_big.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0) || year_num % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
				mCalendar.set(Calendar.YEAR, year_num);
			}
		};

		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				int day = mCalendar.get(Calendar.DATE);
				if (list_big.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
					if (day > 30) {
						day = 30;
						mCalendar.set(Calendar.DATE, 30);
						wv_day.setCurrentItem(day - 1);
					}
				} else {
					if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year.getCurrentItem() + START_YEAR) % 100 != 0)
							|| (wv_year.getCurrentItem() + START_YEAR) % 400 == 0) {
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
						if (day > 29) {
							day = 29;
							mCalendar.set(Calendar.DATE, 29);
							wv_day.setCurrentItem(day - 1);
						}
					} else {
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
						if (day > 28) {
							day = 28;
							mCalendar.set(Calendar.DATE, 28);
							wv_day.setCurrentItem(day - 1);
						}
					}
				}
				month_num = month_num - 1;
				mCalendar.set(Calendar.MONTH, month_num);
			}
		};

		OnWheelChangedListener wheelListener_date = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int date_num = newValue + 1;
				mCalendar.set(Calendar.DATE, date_num);
			}
		};

		OnWheelChangedListener wheelListener_week = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int date_num = newValue + 1;
				mCalendar.set(Calendar.WEEK_OF_YEAR, date_num);
			}
		};

		wv_year.addChangingListener(wheelListener_year);
		wv_month.addChangingListener(wheelListener_month);
		wv_day.addChangingListener(wheelListener_date);
		wv_week.addChangingListener(wheelListener_week);

		// int textSize = (screenheight / 100) * 4;
		wv_day.TEXT_SIZE = textSize;
		wv_month.TEXT_SIZE = textSize;
		wv_year.TEXT_SIZE = textSize;
		wv_week.TEXT_SIZE = textSize;
	}
}
