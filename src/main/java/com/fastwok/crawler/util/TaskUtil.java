package com.fastwok.crawler.util;

import com.fastwok.crawler.entities.Task;
import org.json.JSONObject;

public class TaskUtil {
    public static Task convertToTask(JSONObject jsonObject) {
        Task task = new Task();
        if (jsonObject.has("_id")) {
            task.setId(jsonObject.getString("_id"));
        }
        if (jsonObject.has("title")) {
            task.setTitle(jsonObject.getString("title"));
        }
        if (jsonObject.has("description")) {
            task.setDescription(jsonObject.getString("description"));
        }
        if (jsonObject.has("cretedBy")) {
            if (jsonObject.getJSONObject("cretedBy").has("email")) {
                task.setCreateBy(jsonObject.getJSONObject("cretedBy").getString("email"));
            }
        }
        if (jsonObject.has("cretedBy")) {
            if (jsonObject.getJSONObject("cretedBy").has("email")) {
                task.setCreateBy(jsonObject.getJSONObject("cretedBy").getString("email"));
            }
        }
        if (jsonObject.has("project")) {
            if (jsonObject.getJSONObject("project").has("name")) {
                task.setProject(jsonObject.getJSONObject("project").getString("name"));
            }
        }
        if (jsonObject.has("workitem")) {
            if (jsonObject.getJSONObject("workitem").has("name")) {
                task.setScenario(jsonObject.getJSONObject("workitem").getString("name"));
            }
        }
        if (jsonObject.has("start_date")) {
                task.setStartDate(jsonObject.get("start_date").toString());
        }
        if (jsonObject.has("end_date")) {
            task.setEnd_date(jsonObject.get("end_date").toString());
        }
        if (jsonObject.has("status")) {
            task.setStatus(jsonObject.get("status").toString());
        }
        if (jsonObject.has("proccess")) {
            task.setProccess(jsonObject.get("proccess").toString());
        }
        if (jsonObject.has("overdue")) {
            if (jsonObject.getBoolean("overdue")){
                task.setOverdue(0);
            }else task.setOverdue(1);
        }
        if (jsonObject.has("ngay_hoan_thanh")) {
            task.setCompleteDate(jsonObject.get("ngay_hoan_thanh").toString());
        }
        if (jsonObject.has("evaluate")) {
            if (jsonObject.getJSONObject("evaluate").has("score")) {
                task.setScore(jsonObject.getJSONObject("evaluate").getInt("score"));
            }
        }
        if (jsonObject.has("parentJob")) {
                task.setParentId(jsonObject.getString("parentJob"));
        }
        return task;
    }
}
