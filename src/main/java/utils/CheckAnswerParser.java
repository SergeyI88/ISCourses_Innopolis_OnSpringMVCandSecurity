package utils;

import ajax.TaskA;
import org.springframework.stereotype.Component;

@Component
public class CheckAnswerParser {

    public  TaskA checkTask(TaskA taskA) {
        String answer = taskA.getAnswer();
        if (!checkOnSeparator(answer)) {
            return taskA;
        }
        taskA.setResult(true);
        return taskA;
    }

    private  boolean checkOnSeparator(String answer) {
        String[] count = answer.split(";");
        if (!(count.length == 4)) {
            return false;
        }
        for (String s : count) {
            System.out.println(s);
            if (s.length() < 1) {
                return false;
            }
        }
        return true;
    }
}
