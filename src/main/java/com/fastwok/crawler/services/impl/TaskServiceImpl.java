package com.fastwok.crawler.services.impl;

import com.fastwok.crawler.entities.Customer;
import com.fastwok.crawler.entities.CustomerFastwork;
import com.fastwok.crawler.entities.Task;
import com.fastwok.crawler.services.isservice.TaskService;
import com.fastwok.crawler.util.CustomerUtil;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mysql.cj.xdevapi.JsonArray;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    @Override
    public void getData() throws UnirestException {
        HttpResponse<JsonNode> response = getListTask();
        JSONObject res = new JSONObject(response.getBody());
        JSONArray jsonArray = res.getJSONObject("object").getJSONArray("result");
        int total = jsonArray.length();
        List<Task> Task = new ArrayList<Task>();
        JSONObject jsonValues = new JSONObject(getDataDetail(jsonArray.getJSONObject(0).get("_id").toString()).getBody());
        log.info(jsonValues.toString());
        //        for (int n = 0; n < total; n++) {
//            String customer_id = jsonArray.getJSONObject(n).get("_id").toString();
//            HttpResponse<String> customer_detail = getCustomerDetail(customer_id);
//            JSONObject customer = new JSONObject(customer_detail.getBody()).getJSONArray("result").getJSONObject(0);
//            Customer updateCustomer = getCustomerUpdate(customer, customer_id);
//
//            if (updateCustomer == null) continue;
//            customerFastworkRepository.save(CustomerUtil.convertCustomerObject(updateCustomer.getCode(), customer_id));
//            customers.add(updateCustomer);
//        }
    }

    private HttpResponse<JsonNode> getListTask()
            throws UnirestException {
        Date date = new Date();
        long timeMilli = date.getTime();
        log.info(String.valueOf(timeMilli));
        return Unirest.post("https://work.fastwork.vn:6014/Project/Tasks2/5f27e45f2def30281487e3c6?orgid=5efef3dd5a51cf1c10fab0e4&fromDate=1577811600000&toDate=1601398799999")
                .header("Accept", "*/*")
                .header("Authorization", "Basic dGhhbmd2dUBodGF1dG86N2Q1NzQ0YTI1NjM1MDE2Zjk4MzEyNjE1YWQyZWQzMzI=")
                .header("x-fw", String.valueOf(timeMilli))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36")
                .header("Accept-Language", "en-US,en;q=0.9,vi;q=0.8")
                .header("Referer", "https://app.fastwork.vn/projects")
                .header("Origin", "https://app.fastwork.vn")
                .header("Host", "crm.fastwork.vn:6014")
                .header("Connection", "keep-alive")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Content-Type", "application/json")
                .header("Sec-Fetch-Dest", "empty")
                .header("Sec-Fetch-Mode", "cors")
                .header("Sec-Fetch-Site", "same-site")
                .body("{\"orderbyField\":\"start_date\",\"orderbyType\":\"asc\"}")
                .asJson();
    }
    private HttpResponse<String> getDataDetail(String taskId) throws UnirestException {
        return Unirest.get("https://work.fastwork.vn:6014/Job/"+taskId+"/detail/5efef3dd5a51cf1c10fab0e4")
                .queryString("type", "preview")
                .header("Accept", "*/*")
                .header("Authorization", "Basic dGhhbmd2dUBodGF1dG86N2Q1NzQ0YTI1NjM1MDE2Zjk4MzEyNjE1YWQyZWQzMzI=")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36")
                .header("Accept-Language", "en-US,en;q=0.9,vi;q=0.8")
                .header("Referer", "https://app.fastwork.vn/projects")
                .header("Origin", "https://app.fastwork.vn")
                .header("Host", "crm.fastwork.vn:6014")
                .header("Connection", "keep-alive")
                .header("Accept-Encoding", "gzip, deflate, br")
                .asString();
    }
}
