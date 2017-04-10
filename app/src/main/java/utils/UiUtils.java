package utils;


import android.content.Context;
import android.view.View;

import activity.BaseApplication;

public class UiUtils {

	public static Context getContext(){
		return BaseApplication.getInstance();
	}

	public static View inflate(int id) {
		return View.inflate(getContext(), id, null);
	}
}
