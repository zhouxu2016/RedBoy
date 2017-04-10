package bean;

/**
 * 商品信息
 *
 * @author mo
 */
public class Product {
    private int id;
    private String name;
    private float price;
    private float marketprice;
    private String pic;
    private int commentcount;
    private boolean isgift;

    private int sales;
    private int cid;
    //热门商品过期时间
    private long outoftime;
    // 商品网页链接
    private String href;
    //商品归属品牌id
    private int brandId;
    // 商品id
    private String productId;
    // 图片上提示信息
    private String alt;

    //库存
    private int number;
    // 打分
    private float score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getMarketprice() {
        return marketprice;
    }

    public void setMarketprice(float marketprice) {
        this.marketprice = marketprice;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(int commentcount) {
        this.commentcount = commentcount;
    }

    public boolean isgift() {
        return isgift;
    }

    public void setIsgift(boolean isgift) {
        this.isgift = isgift;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public long getOutoftime() {
        return outoftime;
    }

    public void setOutoftime(long outoftime) {
        this.outoftime = outoftime;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", pic=" + pic + ", price=" + price + ", marketprice="
                + marketprice + ", commentcount=" + commentcount + ", isgift=" + isgift + "]";
    }

}
