package fragment;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/11/10 0010.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), getlayoutId(), null);
        initView(view);
        initListener();
        initData();

        return view;
    }

    public abstract int getlayoutId();

    public abstract void initView(View view);

    public abstract void initData();

    public abstract void initListener();
}
