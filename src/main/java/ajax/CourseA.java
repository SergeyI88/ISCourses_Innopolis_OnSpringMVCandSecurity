package ajax;

import db.pojo.TaskCourse;
import java.util.*;

public class CourseA {
    String name;
    String desc;
    TaskA[] taskA;
    List<TaskCourse> tasksC;

    public List<TaskCourse> getTasksC() {
        return tasksC;
    }

    public void setTasksC(List<TaskCourse> tasksC) {
        this.tasksC = tasksC;
    }

    boolean result;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public CourseA() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public TaskA[] getTaskA() {
        return taskA;
    }

    public void setTaskA(TaskA[] taskA) {
        this.taskA = taskA;
    }
}
