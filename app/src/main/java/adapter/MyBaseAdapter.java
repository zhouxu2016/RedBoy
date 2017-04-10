package adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by yuki on 11/10 0010.
 */

public class MyBaseAdapter extends BaseAdapter {
    private int count;
    public  MyBaseAdapter(int itemCount){
        this.count=itemCount;
    }
    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
