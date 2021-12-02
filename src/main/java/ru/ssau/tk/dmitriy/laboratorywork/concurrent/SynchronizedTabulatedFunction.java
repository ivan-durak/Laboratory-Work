package ru.ssau.tk.dmitriy.laboratorywork.concurrent;

import ru.ssau.tk.dmitriy.laboratorywork.functions.Point;
import ru.ssau.tk.dmitriy.laboratorywork.functions.TabulatedFunction;

import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedTabulatedFunction implements TabulatedFunction {
    private TabulatedFunction tabulatedFunction;
    private Lock lock = new ReentrantLock(true);

    public SynchronizedTabulatedFunction(TabulatedFunction tabulatedFunction) {
        this.tabulatedFunction = tabulatedFunction;
    }

    @Override
    public int getCount() {
        lock.lock();
        int count;
        try {
            count = tabulatedFunction.getCount();
        } finally {
            lock.unlock();
        }
        return count;
    }

    @Override
    public double getX(int index) {
        lock.lock();
        double x;
        try {
            x = tabulatedFunction.getX(index);
        } finally {
            lock.unlock();
        }
        return x;
    }

    @Override
    public double getY(int index) {
        lock.lock();
        double y;
        try {
            y = tabulatedFunction.getY(index);
        } finally {
            lock.unlock();
        }
        return y;
    }

    @Override
    public void setY(int index, double value) {
        lock.lock();
        try {
            tabulatedFunction.setY(index, value);
        } finally {
            lock.lock();
        }
    }

    @Override
    public int indexOfX(double x) {
        lock.lock();
        int index;
        try {
            index = tabulatedFunction.indexOfX(x);
        } finally {
            lock.unlock();
        }
        return index;
    }

    @Override
    public int indexOfY(double y) {
        lock.lock();
        int index;
        try {
            index = tabulatedFunction.indexOfY(y);
        } finally {
            lock.unlock();
        }
        return index;
    }

    @Override
    public double leftBound() {
        lock.lock();
        double value;
        try {
            value = tabulatedFunction.leftBound();
        } finally {
            lock.unlock();
        }
        return value;
    }

    @Override
    public double rightBound() {
        lock.lock();
        double value;
        try {
            value = tabulatedFunction.rightBound();
        } finally {
            lock.unlock();
        }
        return value;
    }

    @Override
    public Iterator<Point> iterator() {
        return null;
    }

    @Override
    public double apply(double x) {
        lock.lock();
        double value;
        try {
            value = tabulatedFunction.apply(x);
        } finally {
            lock.unlock();
        }
        return value;
    }
}
