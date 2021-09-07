package com.xianlv.business.util.datapick;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;

import com.xianlv.business.R;
import com.xianlv.business.weight.MyRecycleView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DataPickUtil {


	private static DataPickUtil cityUtils;

	public static DataPickUtil getInstance() {
		if (cityUtils == null) {
			synchronized (DataPickUtil.class) {
				if (cityUtils == null) {
					cityUtils = new DataPickUtil();
				}
			}
		}
		return cityUtils;
	}

	private TextView mBtnConfirm;
	private Context context;
	private Dialog cityDialog;

	private List<DataPickItem> currentMonthDay = new ArrayList<>();


	private ArrayList<DataPickItem> days = new ArrayList<>();

	private  int year,month;
	private DataRecycleAdapter mAdapter;

	public void showDataPickDialog(final Context context,
								   final onSelectListener listener) {
		days.clear();
		this.context = context;
		Calendar cale = Calendar.getInstance();
		year = cale.get(Calendar.YEAR);
		month = cale.get(Calendar.MONTH) + 1;
		int day = cale.get(Calendar.DATE);
		getMonthData();

		View view = View.inflate(context, R.layout.pop_bottom_datapick, null);

		TextView tv_data = view.findViewById(R.id.tv_current_data);
		TextView tv_cancel = view.findViewById(R.id.tv_cancel);
		TextView tv_sure = view.findViewById(R.id.tv_sure);

		MyRecycleView gl_data = view.findViewById(R.id.viewpager);
		gl_data.setLayoutManager(new GridLayoutManager(context,7));
		if (mAdapter == null){
			mAdapter = new DataRecycleAdapter(listener);
		}
		gl_data.setAdapter(mAdapter);
		mAdapter.setNewData(currentMonthDay);
		tv_sure.setOnClickListener(v -> {
			if (month<12){
				month = month+1;
			}else {
				year++;
				month=1;
			}
			tv_data.setText(year+"年"+month+"月");
			getMonthData();
			mAdapter.notifyDataSetChanged();
		});
		tv_cancel.setOnClickListener(v -> {
			if (month>1){
				month = month-1;
			}else {
				year--;
				month=12;
			}
			tv_data.setText(year+"年"+month+"月");
			getMonthData();
			mAdapter.notifyDataSetChanged();
		});
		tv_data.setText(year+"年"+month+"月");



		cityDialog = new Dialog(context,R.style.transparentFrameWindowStyle);
		cityDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		cityDialog.setContentView(view);
		Window window = cityDialog.getWindow();
		window.setWindowAnimations(R.style.PopupAnimation);

		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

		cityDialog.getWindow().setAttributes(wl);
//		cityDialog.onWindowAttributesChanged(wl);

		cityDialog.setCanceledOnTouchOutside(true);
		cityDialog.show();
	}

	public void getMonthData(){
		currentMonthDay.clear();
		Calendar cale = Calendar.getInstance();
		int curryear = cale.get(Calendar.YEAR);
		int currmonth = cale.get(Calendar.MONTH) + 1;
		int day = cale.get(Calendar.DATE);
		DataPickUtil instance = DataPickUtil.getInstance();
		int daysOfMonth = instance.getDaysOfMonth(year, month);
		int firstDayInWeek = instance.getFirstDayInWeek(year, month - 1);
		for (int i = 0; i < (firstDayInWeek - 1); i++) {
			currentMonthDay.add(new DataPickItem("", false, false));
		}
		for (int i = 1; i <= daysOfMonth; i++) {
			if (curryear<year){
				currentMonthDay.add(new DataPickItem(i + "", true, false));
			}else if (curryear>year){
				currentMonthDay.add(new DataPickItem(i + "", false, false));
			}else {
				if (currmonth<month){
					currentMonthDay.add(new DataPickItem(i + "", true, false));
				}else if (currmonth>month){
					currentMonthDay.add(new DataPickItem(i + "", false, false));
				}else {
					if (i >day) {
						currentMonthDay.add(new DataPickItem(i + "", true, false));
					} else if (i <day) {
						currentMonthDay.add(new DataPickItem(i + "", false, false));
					}else {
						currentMonthDay.add(new DataPickItem(i + "", true, true));
					}
				}
			}

		}
	}


	/**
	 * 得到之前月还有的天数
	 * @param year
	 * @param month
	 * @return
	 */
	private int[] getFrontMothDay(int year,int month){
		int currentDays = getDaysOfMonth(year, month);
		int advanceDays ;
		if (month == 1){
			advanceDays = getDaysOfMonth(year - 1, 12);
		}else {
			advanceDays = getDaysOfMonth(year, month - 1);
		}
		int firstDayInWeek = getFirstDayInWeek(year, month-1);
		System.out.println(firstDayInWeek);

		int[] array = new int[firstDayInWeek - 1];
		for (int i = array.length-1;i>=0;i--){
			array[i] = advanceDays - i;
			System.out.println(array[i]);
		}
		return array;
	}

	/**
	 * 得到下一个月要显示多少天
	 * @param year
	 * @param month
	 * @return
	 */
	private int[] getLastDay(int year,int month){
		int currentDays = getDaysOfMonth(year, month);
//        int advanceDays ;
//        if (month == 1){
//            advanceDays = getDaysOfMonth(year - 1, 12);
//        }else {
//            advanceDays = getDaysOfMonth(year, month - 1);
//        }
		int firstDayInWeek = getFirstDayInWeek(year, month-1);
		int last = 35 - currentDays - (firstDayInWeek - 1);
		if (last < 0 ){
			return null;
		}
		int[] array = new int[last];
		for (int i = 0;i<last;i++){
			array[i] = i+1;
			System.out.println(array[i]);
		}
		return array;
	}

	/**
	 * 得到某年某月第一天是星期几
	 * @param year
	 * @param month  从0 开始
	 * @return
	 */
	public int getFirstDayInWeek(int year,int month) {
		Calendar cld = Calendar.getInstance();
		cld.set(year,month,1);
		int firstDay = cld.get(Calendar.DAY_OF_WEEK);
		if (firstDay == 1){
			firstDay = 7;
		}else {
			firstDay = firstDay - 1;
		}
		return firstDay;
	}

	/**
	 * 计算 这个月一共多少天
	 * @param year
	 * @param month
	 * @return
	 */
	public int getDaysOfMonth(int year, int month) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(year + "-" + month + "-01"));
			return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		} catch (Exception e) {
			return 0;
		}

	}




	public interface onSelectListener {
		 void onFinish(int date,int position);
	}

}
