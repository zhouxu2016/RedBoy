package bean;


public class Favorite {

    /*"commentcount": 22453,
        "id": 1020,
        "isgift": true,
        "marketprice": 79,
        "name": "你妹牌饼干 ",
        "pic": "/images/16.jpg",
        "price": 78

        id,NAME,price,marketprice,pic,commentcount,isgift
*/

    public int id;
    public String name;
    public float price;
    public float marketprice;
    public String pic;
    public int commentcount;
    public boolean isgift;


    public Favorite(int id, String name, float price,
                    float marketprice, String pic, int commentcount, boolean isgift) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.marketprice = marketprice;
        this.pic = pic;
        this.commentcount = commentcount;
        this.isgift = isgift;
    }

    public Favorite() {
    }


    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", marketprice=" + marketprice +
                ", pic='" + pic + '\'' +
                ", commentcount=" + commentcount +
                ", isgift=" + isgift +
                '}';
    }

}
