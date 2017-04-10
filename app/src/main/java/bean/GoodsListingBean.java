package bean;

/**
 * Created by Administrator on 2016/11/13 0013.
 */
public class GoodsListingBean {
    public String name;
    public String pic;
    public int price;
    public int marketprice;
    public long outoftime;

    @Override
    public String toString() {
        return "GoodsListingBean{" +
                "name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                ", price=" + price +
                ", marketprice=" + marketprice +
                ", outoftime=" + outoftime +
                '}';
    }
}
