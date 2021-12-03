package ru.ssau.tk.dmitriy.laboratorywork.concurrent;
import ru.ssau.tk.dmitriy.laboratorywork.functions.ConstantFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.dmitriy.laboratorywork.functions.TabulatedFunction;

import java.util.concurrent.CountDownLatch;

public class AddingMultiplyingTaskExecutor {
    public static void main(String[] args) throws InterruptedException {
        TabulatedFunction tabulatedFunction = new LinkedListTabulatedFunction(new ConstantFunction(2), 1, 100, 100);
        CountDownLatch countDownLatch = new CountDownLatch(3);
        Thread thread1 = new Thread(new MultiplyingTask(tabulatedFunction, countDownLatch::countDown));
        Thread thread2 = new Thread(new MultiplyingTask(tabulatedFunction, countDownLatch::countDown));
        Thread thread3 = new Thread(new AddingTask(tabulatedFunction, countDownLatch::countDown));

        thread1.start();
        thread2.start();
        thread3.start();
        countDownLatch.await();

        System.out.println(tabulatedFunction.toString());
    }
}