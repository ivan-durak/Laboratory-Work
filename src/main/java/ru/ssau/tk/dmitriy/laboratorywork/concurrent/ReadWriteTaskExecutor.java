package ru.ssau.tk.dmitriy.laboratorywork.concurrent;

import ru.ssau.tk.dmitriy.laboratorywork.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.ZeroFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ReadWriteTaskExecutor {
    public static void main(String[] args) {
        LinkedListTabulatedFunction link = new LinkedListTabulatedFunction(new ZeroFunction(10), 1, 10, 10);
        List<Thread> threadList = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(20);
        for (int i = 0; i < 20; i++) {
            threadList.add(new Thread(new ReadWriteTask(link, countDownLatch::countDown)));
        }
        for (Thread thread : threadList) {
            thread.start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        System.out.println(link);

    }
}
