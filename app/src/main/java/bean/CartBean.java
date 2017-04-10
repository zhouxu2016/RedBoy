package bean;

import java.util.List;

/**
 * Created by think on 2016/11/15.
 */
public class CartBean {
    public String response;
    public int totalCount;
    public int totalPoint;
    public float totalPrice;
    public Cart cart;
    public class Cart {
    public List<CartItemList> cartItemList;
      public  class CartItemList{
           public int  prodNum;
           public Product  product;
           public  class Product{
                public int brandId;
                public int cid;
                public int commentcount;
                public String href;
                public int id;
                public boolean isgift;
                public float marketprice;
                public String name;
                public int number;
                public String pic;
                public float price;
                public int sales;
                public float score;
            }
        }
    }
}
