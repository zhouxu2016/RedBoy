package bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/12 0012.
 */
public class PanicBean {
    public String name;
    public int marketprice;
    public long outoftime;
    public String pic;
    public int price;
    @Override
    public String toString() {
        return "newProductBean{" +
                "marketprice=" + marketprice +
                ", name='" + name + '\'' +
                ", outoftime=" + outoftime +
                ", pic='" + pic + '\'' +
                ", price=" + price +
                '}';
    }
}
