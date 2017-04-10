package bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

// javabean  --       java 特殊的java类-----    x    getX  setX   ---  内省操作
// 对应的表名字

@Table(name = "help")
public class Help {

//	{"id":1,"title":"如何派送"}
	// 不允许自增长
	@NoAutoIncrement
	@Column(column="id")
	public long id;

	@Column(column="title")
	public String title;

	public Help(long id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

	//  一定要有空参构造方法


	public Help() {

	}

	@Override
	public String toString() {

		return title;
	}
}
