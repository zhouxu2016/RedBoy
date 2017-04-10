package bean;

/**
 * Created by Administrator on 2016/11/12 0012.
 */
public class SearchResultProduct {


    public int id;
    public String name;
    public String pic;
    public int price;
    public int marketprice;
    public int sales;
    public int commentcount;
    public int productid;

    public SearchResultProduct(int id, String name, String pic, int price, int marketprice, int sales, int commentcount, int productid) {
        this.id = id;
        this.name = name;
        this.pic = pic;
        this.price = price;
        this.marketprice = marketprice;
        this.sales = sales;
        this.commentcount = commentcount;
        this.productid = productid;
    }

    public SearchResultProduct() {
    }

    @Override
    public String toString() {
        return "SearchResultProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                ", price=" + price +
                ", marketprice=" + marketprice +
                ", sales=" + sales +
                ", commentcount=" + commentcount +
                ", productid=" + productid +
                '}';
    }
}
