package services;

import ajax.CourseA;
import ajax.TaskA;
import db.dao.TempCourseDaoImpl;
import db.dao.exceptions.TempDAOException;
import db.pojo.Course;
import db.pojo.TaskCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceForWorkTempTable {

    @Autowired
    TempCourseDaoImpl tempCourseDao;

    public CourseA addCourse(CourseA courseA) {
        TaskA[] tasks = courseA.getTaskA();
        int id_course = 0;
        try {
            id_course = tempCourseDao.addCourse(courseA.getName(), courseA.getDesc(), courseA.getTaskA().length);
        } catch (TempDAOException e) {
            e.printStackTrace();
        }
        if (id_course != 0) {
            courseA.setResult(true);
            tempCourseDao.addTasks(tasks, id_course);
        }
        return courseA;
    }

    public List<Course> getCourses() throws TempDAOException {
        return tempCourseDao.showAllCourse();
    }

    public List<TaskCourse> getTasks(int id_course) throws TempDAOException {
        return tempCourseDao.getAllTaskByIdCourse(id_course);
    }


    public void cancelNewCourse(CourseA courseA) throws TempDAOException {
        tempCourseDao.removeCourse(tempCourseDao.removeTasks(courseA));
    }

    public void acceptNewCourse(CourseA courseA) throws TempDAOException {
        String name = courseA.getName();
        String desc = courseA.getDesc();
        int size = courseA.getTasksC().size();
        int id_course = tempCourseDao.addCourseInMain(name, desc, size);
        List<TaskA> list = new ArrayList<>();
        for (TaskCourse taskCourse : courseA.getTasksC()) {
            TaskA taskA = new TaskA();
            taskA.setName(taskCourse.getName());
            taskA.setDesc(taskCourse.getDescription());
            taskA.setAnswer(taskCourse.getTask());
            taskA.setTask(taskCourse.getAnswer());
            list.add(taskA);
        }
        tempCourseDao.addTasks((TaskA[]) list.toArray(), id_course);
        cancelNewCourse(courseA);
    }
}
