package com.xianlv.business.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xianlv.business.R;
import com.xianlv.business.bean.ShopDepartment;
import com.xianlv.business.weight.wheel.WheelView;
import com.xianlv.business.weight.wheel.adapters.AbstractWheelTextAdapter;

import java.util.List;


public class DepartmentDialogUtil  {


	private static Dialog cityDialog;

	public static  void showSelectDialog(Context context, List<ShopDepartment> data, onSelectListener listener) {
		View view = View.inflate(context, R.layout.pop_bottom_department, null);
		cityDialog = DialogUtils.getBottomDialog(context,view);
		WheelView mViewProvince = view.findViewById(R.id.wv_country);
		TextView mBtnConfirm = view.findViewById(R.id.tv_sure);
		mBtnConfirm.setOnClickListener(v->{
			listener.onFinish(data.get(mViewProvince.getCurrentItem()));
			cityDialog.dismiss();
			cityDialog = null;
		});
		WheelAdapter provinceAdapter = new WheelAdapter(context, data);
		mViewProvince.setViewAdapter(provinceAdapter);
		mViewProvince.setVisibleItems(7);

	}

	static  class WheelAdapter extends AbstractWheelTextAdapter {
		List<ShopDepartment> list;

		public WheelAdapter(Context context, List<ShopDepartment> list) {
			super(context);
			this.list = list;
		}

		@Override
		public int getItemsCount() {
			return list == null ? 0 : list.size();
		}


		@Override
		protected CharSequence getItemText(int index) {
			return list.get(index).department;
		}

	}

	public interface onSelectListener {
		void onFinish(ShopDepartment bean);
	}

}
