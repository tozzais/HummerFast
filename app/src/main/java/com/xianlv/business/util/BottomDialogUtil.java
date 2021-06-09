package com.xianlv.business.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.xianlv.business.R;
import com.xianlv.business.bean.ShopDepartment;


public class BottomDialogUtil {


	private static Dialog cityDialog;

	public static  void showSelectDialog(Context context) {
		View view = View.inflate(context, R.layout.pop_bottom_switch, null);
		cityDialog = DialogUtils.getBottomDialog(context,view);

	}


	public interface onSelectListener {
		void onFinish(ShopDepartment bean);
	}

}
