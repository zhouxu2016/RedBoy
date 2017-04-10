package activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zhouxu.redboy.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by HL on 2016/11/15.
 */
public class shopCarNull extends Activity implements View.OnClickListener{

    @ViewInject(R.id.btn_shopping)
    Button btn_shopping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppingcar_fragment_empty);

        ViewUtils.inject(this);
        btn_shopping.setOnClickListener(this);
    }

    
    
    @Override
    //添加点击事件,点击随便逛逛回到首页
    public void onClick(View view) {
        
        
        
        
    }

    
    
    
    
    
    
}
