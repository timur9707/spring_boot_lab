package com.epam.springLabEpam.aspect;

import com.epam.springLabEpam.controller.TaskController;
import com.epam.springLabEpam.exception.SubscriptionException;
import com.epam.springLabEpam.model.Task;
import com.epam.springLabEpam.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


@Aspect
@Component
public class SubscriptionAspect {
    private TaskController taskController;

    @Autowired
    SubscriptionAspect(TaskController taskController) {
        this.taskController = taskController;
    }

    @Before("execution(public * com.epam.springLabEpam.controller.TaskController.createTask()) && args(user,task)")
    public void checkSubscriptionAdvice(JoinPoint joinPoint, User user, Task task) {
        System.out.println("Aspect");
        if (taskController.getUserTasks(user.getId()).size() == 10 && !user.getSubscription().equals(getSecretWord())) {
            throw new SubscriptionException();
        }
    }

    public static String getSecretWord() {
        String secretWord = "secret";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(secretWord.getBytes());
        return Arrays.toString(md.digest());
    }
}
