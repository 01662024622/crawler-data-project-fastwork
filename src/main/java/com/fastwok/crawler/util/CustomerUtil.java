package com.fastwok.crawler.util;

import com.fastwok.crawler.entities.Customer;
import com.fastwok.crawler.entities.CustomerFastwork;
import org.json.JSONObject;

import java.util.List;

public class CustomerUtil {
    public static String convertCustomerToHTML(List<Customer> customers){
        String html="";
        for (Customer customer : customers){
            html=html+"<tr>" +
                    "<td style=\"border: 1px solid black\">" +customer.getCode() +"</td>" +
                    "<td style=\"border: 1px solid black\">" +customer.getName_main() +"</td>" +
                    "<td style=\"border: 1px solid black\">" +customer.getName_business() +"</td>" +
                    "<td style=\"border: 1px solid black\">" +customer.getAddress() +"</td>" +
                    "<td style=\"border: 1px solid black\">" +customer.getMain_group() +"</td>" +
                    "<td style=\"border: 1px solid black\">" +customer.getCategorize_customer() +"</td>" +
                    "<td style=\"border: 1px solid black\">" +customer.getLevel_customer() +"</td>" +
                    "<td style=\"border: 1px solid black\">" +customer.getSupplies_phone_1() +"</td>" +
                    "<td style=\"border: 1px solid black\">" +customer.getUser_code() +"</td>" +
                    "</tr>";
        }

        return "<table style=\"border: 1px solid black; border-collapse: collapse;\"><thead><tr>" +
                "<th style=\"border: 1px solid black\">Mã</th>" +
                "<th style=\"border: 1px solid black\">Tên làm mã</th>" +
                "<th style=\"border: 1px solid black\">Tên in trên hóa đơn</th>" +
                "<th style=\"border: 1px solid black\">Địa chỉ</th>" +
                "<th style=\"border: 1px solid black\">Nhóm mẹ</th>" +
                "<th style=\"border: 1px solid black\">Phân loại</th>" +
                "<th style=\"border: 1px solid black\">Phân hàng</th>" +
                "<th style=\"border: 1px solid black\">Số điện thoại</th>" +
                "<th style=\"border: 1px solid black\">Phụ trách dầu</th>" +
                "</tr></thead>" +
                "<tbody>"+html +
                "</tbody>" +
                "</table>";
    }

    public static Customer convertJsonToCustomer(JSONObject data, String user) {
        Customer customer = new Customer();
        customer.setCode(data.getJSONObject("ma_khach_hang").get("viewData").toString());
        customer.setName_business(data.getJSONObject("khach_hang").get("viewData").toString());
        customer.setName_main(data.getJSONObject("fax").get("viewData").toString());
        customer.setAddress(data.getJSONObject("dia_chi").get("viewData").toString());
        customer.setMain_group(data.getJSONObject("nguon_khach_hang").get("viewData").toString());
        customer.setCategorize_customer(data.getJSONObject("loai_khach_hang").get("viewData").toString());
        customer.setLevel_customer(data.getJSONObject("kenh_khach_hang").get("viewData").toString());
        customer.setSupplies_phone_1(data.getJSONObject("sdt").get("viewData").toString());
        customer.setUser_code(user);
        return customer;
    }
    public static CustomerFastwork convertCustomerObject(String code,String customer_id){
        CustomerFastwork customerFastwork = new CustomerFastwork();
        customerFastwork.setCustomer(code);
        customerFastwork.setFw_id(customer_id);
        return customerFastwork;
    }
}
