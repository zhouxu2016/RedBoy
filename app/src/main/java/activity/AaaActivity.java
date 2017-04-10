package activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.zhouxu.redboy.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_aaa)
public class AaaActivity extends Activity {
    @ViewInject(R.id.listview)
    private ListView listView;
    List<String> slist=new ArrayList<>();
    String[] array = {"a", "b", "c"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        slist.add("a");
        slist.add("a");
        slist.add("a");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_text, slist);
        listView.setAdapter(adapter);
    }
}
