package activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.lidroid.xutils.ViewUtils;

/**
 *
 */
public abstract class LyBaseActivity extends FragmentActivity implements View.OnClickListener{

    public Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx=this;
        initView();
        initData();
        initListener();
    }

    public  void initView() {
        ViewUtils.inject(this);
    }

    public abstract void initData();

    public abstract void initListener();
}
