package bean;

import java.util.ArrayList;
import java.util.List;

/**
 * {
 "brandId": 0,
 "cid": 0,
 "commentcount": 28222,
 "href": "http://item.jd.com/10653241626.html",
 "id": 1,
 "isgift": false,
 "marketprice": 323,
 "name": "太平鸟女装2016冬装新品拼爱心大衣A3AA54338 浅驼 M",
 "number": 0,
 "outoftime": 1479239369160,
 "pic": "http://img10.360buyimg.com/n6/jfs/t3001/50/2080592442/157131/93e2f3cb/57d10f4cN3634b211.jpg",
 "price": 284,
 "sales": 28433,
 "score": 0
 },
 */
public class newProductBean {
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
