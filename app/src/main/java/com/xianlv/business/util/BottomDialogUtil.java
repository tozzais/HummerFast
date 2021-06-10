package com.xianlv.business.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.xianlv.business.MainActivity;
import com.xianlv.business.R;
import com.xianlv.business.bean.ShopDepartment;
import com.xianlv.business.global.GlobalParam;
import com.xianlv.business.ui.activity.LoginActivity;
import com.xianlv.business.ui.activity.SelectHouseActivity;


public class BottomDialogUtil {


	private static Dialog cityDialog;

	public static  void showSelectDialog(Context context) {
		View view = View.inflate(context, R.layout.pop_bottom_switch, null);
		cityDialog = DialogUtils.getBottomDialog(context,view);

		view.findViewById(R.id.tv_switch).setOnClickListener(v -> {
			SelectHouseActivity.launch(context);
			cityDialog.dismiss();
		});
		view.findViewById(R.id.tv_sure).setOnClickListener(v -> {
			cityDialog.dismiss();
		});
		view.findViewById(R.id.tv_logout).setOnClickListener(v -> {
			cityDialog.dismiss();
			GlobalParam.exitLogin();
            LoginActivity.launch(context);
			((MainActivity)context).finish();

		});

	}


	public interface onSelectListener {
		void onFinish(ShopDepartment bean);
	}

}
