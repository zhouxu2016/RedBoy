package bean;

import java.util.List;

/**
 * Created by 李云龙 on 2016/11/15.
 */
public class AddressListRes {
    public String  response;
    public List<AddressList> addresslist;

    public AddressListRes(){

    }

    public AddressListRes(String response, List<AddressList> addresslist) {
        this.response = response;
        this.addresslist = addresslist;
    }

    @Override
    public String toString() {
        return "AddressListRes{" +
                "response='" + response + '\'' +
                ", addresslist=" + addresslist +
                '}';
    }

    public class AddressList {
        public int id;
        public String addressDetail;
        public String areaId;
        public String cityId;
        public String isdefault;
        public String name;
        public String phoneNumber;
        public String provinceId;
        public String zipCode;

        public AddressList(){


        }

        public AddressList(int id, String addressDetail, String areaId, String cityId, String isdefault, String name, String phoneNumber, String provinceId, String zipCode) {
            this.id = id;
            this.addressDetail = addressDetail;
            this.areaId = areaId;
            this.cityId = cityId;
            this.isdefault = isdefault;
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.provinceId = provinceId;
            this.zipCode = zipCode;
        }

        @Override
        public String toString() {
            return "AddressList{" +
                    "id=" + id +
                    ", addressDetail='" + addressDetail + '\'' +
                    ", areaId='" + areaId + '\'' +
                    ", cityId=" + cityId +
                    ", isdefault='" + isdefault + '\'' +
                    ", name='" + name + '\'' +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    ", provinceId='" + provinceId + '\'' +
                    ", zipCode='" + zipCode + '\'' +
                    '}';
        }
    }
}
