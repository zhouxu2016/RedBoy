package bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/11/12 0012.
 */
@Table(name = "history")
public class SearchHistory {

    @Column(column = "_id")
    public int id;
    @Column(column = "name")
    public String name;

    public SearchHistory() {
    }

    public SearchHistory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
