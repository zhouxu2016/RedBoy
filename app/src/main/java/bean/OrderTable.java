package bean;


import java.util.List;

/**
 * 订单列表的javaBean
 */
public class OrderTable {


    /*   "flag": "1",
            "orderId": "412423145",
            "paymentType": "支付宝",
            "price": "1234.23",
            "status": "4",
            "time": "2011/10/10"*/


    public String response;

    public List<OrderInfor> orderList;

    public class OrderInfor {

        public int flag;
        public long orderId;
        public String paymentType;
        public float price;
        public long status;
        public String time;




    }
}
