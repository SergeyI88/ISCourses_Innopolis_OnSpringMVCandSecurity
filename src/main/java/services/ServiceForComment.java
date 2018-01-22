package services;

import db.dao.TaskCourseDao;
import db.dao.TaskCourseDaoImpl;
import db.dao.exceptions.TaskCourseDaoException;
import db.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ServiceForComment {
    private TaskCourseDao taskCourseDao;
    public TaskCourseDao getTaskCourseDao() {
        return taskCourseDao;
    }
    @Autowired
    public void setTaskCourseDao(TaskCourseDao taskCourseDao) {
        this.taskCourseDao = taskCourseDao;
    }

    public void addCommentToTask(int id_task, int id_user, String comment, long date) {
        try {
            taskCourseDao.addCommentFromUser( id_task,  id_user,  comment,  date);
        } catch (TaskCourseDaoException e) {
            e.printStackTrace();
        }
    }

    public List<Comment> getAllCommentToTask(int id_task) {
        try {
            return taskCourseDao.getAllComments(id_task);
        } catch (TaskCourseDaoException e) {
            e.printStackTrace();
            return null;
        }
    }
}
