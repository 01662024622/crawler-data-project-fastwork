package com.fastwok.crawler.util;

import com.fastwok.crawler.entities.SubTask;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SubTaskUtil {
    public static List<SubTask> convertSubTask(JSONObject jsonValues, String taskId) {
        List<SubTask> subTasks = new ArrayList<>();
        if (jsonValues.has("checklists")) {
            if (jsonValues.getJSONArray("checklists").length() > 0) {
                if (jsonValues.getJSONArray("checklists").getJSONObject(0).has("checkItems")) {
                    if (jsonValues.getJSONArray("checklists").getJSONObject(0).getJSONArray("checkItems").length() > 0) {
                        JSONArray jsonArr = jsonValues.getJSONArray("checklists").getJSONObject(0).getJSONArray("checkItems");
                        for (int x = 0; x < jsonArr.length(); x++) {
                            SubTask subTask = new SubTask();
                            subTask.setTaskId(taskId);
                            subTask.setId(jsonArr.getJSONObject(x).getString("id"));
                            subTask.setTitle(jsonArr.getJSONObject(x).getString("title"));
                            if (jsonArr.getJSONObject(x).has("completedDate")) {
                                subTask.setCompleteDate(jsonArr.getJSONObject(x).get("completedDate").toString());
                            }
                            if (jsonArr.getJSONObject(x).has("completedBy")) {
                                if (jsonArr.getJSONObject(x).getJSONObject("completedBy").has("email")) {
                                    subTask.setCompleteBy(jsonArr.getJSONObject(x).getJSONObject("completedBy").get("email").toString());
                                }
                            }
                            subTasks.add(subTask);
                        }
                    }
                }
            }
        }
        return subTasks;
    }
}
