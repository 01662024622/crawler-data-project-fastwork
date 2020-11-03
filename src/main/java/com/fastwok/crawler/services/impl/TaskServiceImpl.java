package com.fastwok.crawler.services.impl;

import com.fastwok.crawler.entities.Task;
import com.fastwok.crawler.entities.TaskUser;
import com.fastwok.crawler.entities.User;
import com.fastwok.crawler.repository.SubTaskRepository;
import com.fastwok.crawler.repository.TaskRepository;
import com.fastwok.crawler.repository.TaskUserRepository;
import com.fastwok.crawler.repository.UserRepository;
import com.fastwok.crawler.services.isservice.TaskService;
import com.fastwok.crawler.util.TaskUtil;
import com.fastwok.crawler.util.SubTaskUtil;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mysql.cj.xdevapi.JsonArray;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    SubTaskRepository subTaskRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskUserRepository taskUserRepository;

    @Override
    public void getData(String string) throws UnirestException {
        HttpResponse<JsonNode> response = getListTask(string);
        JSONObject res = new JSONObject(response.getBody());
        JSONArray jsonArray = res.getJSONObject("object").getJSONArray("result");
        int total = jsonArray.length();
        for (int n = 0; n < total; n++) {
            String id = jsonArray.getJSONObject(n).get("_id").toString();
            JSONObject jsonValues = new JSONObject(getDataDetail(id).getBody()).getJSONObject("result");
            taskRepository.save(TaskUtil.convertToTask(jsonValues));
            subTaskRepository.saveAll(SubTaskUtil.convertSubTask(jsonValues, id));
            if (jsonValues.has("assignTo")) {
                if (jsonValues.getJSONArray("assignTo").length() > 0) {
                    List<User> users = new ArrayList<>();
                    List<TaskUser> taskUsers = new ArrayList<>();
                    for (int y = 0; y < jsonValues.getJSONArray("assignTo").length(); y++) {
                        JSONObject jsonObject = jsonValues.getJSONArray("assignTo").getJSONObject(0);
                        if (userRepository.findUserByEmail(jsonObject.getString("email")) < 0) {
                            User user = new User();
                            user.setEmail(jsonObject.getString("email"));
                            user.setName(jsonObject.getString("name"));
                            users.add(user);
                        }
                        TaskUser taskUser = new TaskUser();
                        taskUser.setTaskId(id);
                        taskUser.setUserEmail(jsonObject.getString("email"));
                        taskUsers.add(taskUser);
                    }
                    if (taskUsers.size() > 0) {
                        taskUserRepository.saveAll(taskUsers);
                    }
                    if (users.size() > 0) {
                        userRepository.saveAll(users);
                    }
                }
            }
        }
    }

    private HttpResponse<JsonNode> getListTask(String string)
            throws UnirestException {
        Date date = new Date();
        long timeMilli = date.getTime();
        log.info(String.valueOf(timeMilli));
        return Unirest.post("https://work.fastwork.vn:6014/Project/Tasks2/5f27e45f2def30281487e3c6?orgid=5efef3dd5a51cf1c10fab0e4&fromDate=1575162000000&toDate=1609462800000&orderbyField=cretedDate&orderbyType=desc"+string)
                .header("Accept", "*/*")
                .header("Authorization", "Basic dGhhbmd2dUBodGF1dG86N2Q1NzQ0YTI1NjM1MDE2Zjk4MzEyNjE1YWQyZWQzMzI=")
                .header("x-fw", String.valueOf(timeMilli))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36")
                .header("Accept-Language", "en-US,en;q=0.9,vi;q=0.8")
                .header("Referer", "https://app.fastwork.vn/")
                .header("Origin", "https://app.fastwork.vn")
                .header("Host", "crm.fastwork.vn:6014")
                .header("Connection", "keep-alive")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Content-Type", "application/json")
                .header("Sec-Fetch-Dest", "empty")
                .header("Sec-Fetch-Mode", "cors")
                .header("Sec-Fetch-Site", "same-site")
                .body("{}")
                .asJson();
    }

    private HttpResponse<String> getDataDetail(String taskId) throws UnirestException {
        Date date = new Date();
        long timeMilli = date.getTime();
        return Unirest.get("https://work.fastwork.vn:6014/Job/" + taskId + "/detail/5efef3dd5a51cf1c10fab0e4")
                .queryString("type", "preview")
                .header("Accept", "*/*")
                .header("Authorization", "Basic dGhhbmd2dUBodGF1dG86N2Q1NzQ0YTI1NjM1MDE2Zjk4MzEyNjE1YWQyZWQzMzI=")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36")
                .header("Accept-Language", "en-US,en;q=0.9,vi;q=0.8")
                .header("Referer", "https://app.fastwork.vn/")
                .header("Origin", "https://app.fastwork.vn")
                .header("Host", "work.fastwork.vn:6014")
                .header("Connection", "keep-alive")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("x-fw", String.valueOf(timeMilli))
                .header("Sec-Fetch-Dest", "empty")
                .header("Sec-Fetch-Mode", "cors")
                .header("Sec-Fetch-Site", "same-site")
                .asString();
    }
}
