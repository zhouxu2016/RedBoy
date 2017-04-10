package activity;

import android.view.View;
import android.widget.Button;

import com.example.zhouxu.redboy.R;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

@ContentView(R.layout.activity_gift)
public class GiftActivity extends MyBaseActivity {

    @ViewInject(R.id.btn_back)
    private Button btn_back;


    @OnClick(R.id.btn_back)
    public void click(View view) {

        finish();

    }

}
