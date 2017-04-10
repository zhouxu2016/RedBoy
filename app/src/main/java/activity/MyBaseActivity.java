package activity;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.lidroid.xutils.ViewUtils;

public class MyBaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        init();
        initListener();

    }

    protected void initView() {

        ViewUtils.inject(this);
    }

    protected void init() {

    }


    protected  void initListener() {

    }

}
