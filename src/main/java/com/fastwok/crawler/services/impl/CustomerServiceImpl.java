package com.fastwok.crawler.services.impl;

import com.fastwok.crawler.entities.Customer;
import com.fastwok.crawler.entities.CustomerFastwork;
import com.fastwok.crawler.repository.CustomerFastworkRepository;
import com.fastwok.crawler.repository.CustomerRepository;
import com.fastwok.crawler.services.isservice.CustomerService;
import com.fastwok.crawler.services.isservice.MailSenderService;
import com.fastwok.crawler.util.CustomerUtil;
import com.fastwok.crawler.util.StringUtil;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CustomerServiceImpl {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerFastworkRepository customerFastworkRepository;

    @Autowired
    private MailSenderService mailSenderService;

    public void getCustomers() throws UnirestException, MessagingException {

        Date date = new Date();
        long timeMilli = date.getTime();
        HttpResponse<JsonNode> response = getListCustomerByTime();
        JSONObject res = new JSONObject(response.getBody());
        JSONObject jsonObject = res.getJSONObject("object");
        int total = jsonObject.getInt("total");
        List<Customer> customers = new ArrayList<Customer>();
        for (int n = 0; n < total; n++) {
            String customer_id = jsonObject.getJSONArray("result").getJSONObject(n).get("_id").toString();
            CustomerFastwork check = customerFastworkRepository.getByIdFastwork(customer_id);
            if (check != null) continue;
            HttpResponse<String> customer_detail = getCustomerDetail(customer_id);
            JSONObject customer = new JSONObject(customer_detail.getBody()).getJSONArray("result").getJSONObject(0);
            Customer updateCustomer = getCustomerUpdate(customer, customer_id);

            if (updateCustomer == null) continue;
            customerFastworkRepository.save(CustomerUtil.convertCustomerObject(updateCustomer.getCode(), customer_id));
            customers.add(updateCustomer);
        }
        if (customers.size() > 0) {
            String html = CustomerUtil.convertCustomerToHTML(customers);
            mailSenderService.sendMail(html);
        }
    }

    private HttpResponse<String> getCustomerDetail(String customer_id)
            throws UnirestException {
        Date date = new Date();
        long timeMilli = date.getTime();
        return Unirest.get("https://crm.fastwork.vn:6012/crmcustomers/" + customer_id + "/detail/5efef3dd5a51cf1c10fab0e4")
                .queryString("type", "preview")
                .header("Accept", "*/*")
                .header("Authorization", "Basic dGhhbmd2dUBodGF1dG86N2Q1NzQ0YTI1NjM1MDE2Zjk4MzEyNjE1YWQyZWQzMzI=")
                .header("x-fw", String.valueOf(timeMilli))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36")
                .header("Accept-Language", "en-US,en;q=0.9,vi;q=0.8")
                .header("Referer", "https://app.fastwork.vn/crmcustomers")
                .header("Origin", "https://app.fastwork.vn")
                .header("Host", "crm.fastwork.vn:6012")
                .header("Connection", "keep-alive")
                .header("Accept-Encoding", "gzip, deflate, br")
                .asString();
    }

    private HttpResponse<JsonNode> getListCustomerByTime()
            throws UnirestException {
        Date date = new Date();
        long timeMilli = date.getTime();
        log.info(String.valueOf(timeMilli));
        return Unirest.post(" https://crm.fastwork.vn:6012/crmcustomers/list/5efef3dd5a51cf1c10fab0e4?next=50&skip=0&orderBy=createdDate&desc=-1")
                .header("Accept", "*/*")
                .header("Authorization", "Basic dGhhbmd2dUBodGF1dG86N2Q1NzQ0YTI1NjM1MDE2Zjk4MzEyNjE1YWQyZWQzMzI=")
                .header("x-fw", String.valueOf(timeMilli))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36")
                .header("Accept-Language", "en-US,en;q=0.9,vi;q=0.8")
                .header("Referer", "https://app.fastwork.vn/crmcustomers")
                .header("Origin", "https://app.fastwork.vn")
                .header("Host", "crm.fastwork.vn:6012")
                .header("Connection", "keep-alive")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Content-Type", "application/json")
                .header("Sec-Fetch-Dest", "empty")
                .header("Sec-Fetch-Mode", "cors")
                .header("Sec-Fetch-Site", "same-site")
                .body("{\"filter\":\"all\",\"from_date\":" + String.valueOf(timeMilli - 28000000) + ",\"to_date\":" + String.valueOf(timeMilli) + ",\"objfind\":{}}")
                .asJson();
    }

    private HttpResponse<JsonNode> updateCustomer(JSONObject data, String customer_id)
            throws UnirestException {
        Date date = new Date();
        long timeMilli = date.getTime();
        return Unirest.put(" https://crm.fastwork.vn:6012/crmcustomers/" + customer_id + "/info/5efef3dd5a51cf1c10fab0e4")
                .header("Accept", "*/*")
                .header("Authorization", "Basic dGhhbmd2dUBodGF1dG86N2Q1NzQ0YTI1NjM1MDE2Zjk4MzEyNjE1YWQyZWQzMzI=")
                .header("x-fw", String.valueOf(timeMilli))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36")
                .header("Accept-Language", "en-US,en;q=0.9,vi;q=0.8")
                .header("Referer", "https://app.fastwork.vn/crmcustomers")
                .header("Origin", "https://app.fastwork.vn")
                .header("Host", "crm.fastwork.vn:6012")
                .header("Connection", "keep-alive")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Content-Type", "application/json")
                .header("Sec-Fetch-Dest", "empty")
                .header("Sec-Fetch-Mode", "cors")
                .header("Sec-Fetch-Site", "same-site")
                .body(data.toString())
                .asJson();
    }


    private Customer getCustomerUpdate(JSONObject customer, String customer_id) {
        JSONObject data = customer.getJSONObject("data");
        JSONObject ma_khach_hang = data.getJSONObject("ma_khach_hang");
        try {
            Object fax = data.getJSONObject("fax").get("viewData");
            Object parent = data.getJSONObject("nguon_khach_hang").get("viewData");
            String address = StringUtil.convetAddress(data.getJSONObject("dia_chi").get("viewData").toString());
            if (fax.equals("")||parent.equals("")||address.equals("")){
                log.info(ma_khach_hang.toString());
                return null;
            }

            String newCode = StringUtil.formatCode(fax + "_" + address + "_"+parent);

            int size = customerRepository.getByIdCode(newCode).size();
            if (size > 0) {
                newCode = newCode + "_" + size;
            }
            JSONObject updateData = new JSONObject();
            updateData.put("long", customer.get("long"));
            updateData.put("lat", customer.get("lat"));
            updateData.put("status", customer.getJSONObject("status"));
            updateData.put("trang_thai", customer.getJSONObject("trang_thai"));
            if (customer.has("groupAssignTo")) {
                updateData.put("groupAssignTo", customer.getJSONObject("groupAssignTo"));
            }
            ma_khach_hang.put("viewData", newCode);
            data.put("ma_khach_hang", ma_khach_hang);
            updateData.put("data", data);
            log.info(updateData.toString());
            updateCustomer(updateData, customer_id);
            return CustomerUtil.convertJsonToCustomer(data, customer.getJSONArray("memberAssignTo").getJSONObject(0).get("name").toString());
        } catch (Exception ex) {
            log.info(ma_khach_hang.toString());
        }
        return null;
    }

}
