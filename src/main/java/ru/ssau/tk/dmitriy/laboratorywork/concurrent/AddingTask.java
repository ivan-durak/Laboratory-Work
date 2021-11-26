package ru.ssau.tk.dmitriy.laboratorywork.concurrent;

import ru.ssau.tk.dmitriy.laboratorywork.functions.TabulatedFunction;

public class AddingTask  implements Runnable{
    private final TabulatedFunction tabulatedFunction;


    public AddingTask(TabulatedFunction tabulatedFunction) {
        this.tabulatedFunction = tabulatedFunction;

    }
    @Override
    public void run() {
        double x;
        double y;
    for(int i=0; i< tabulatedFunction.getCount(); i++){
            x = tabulatedFunction.getX(i);
            synchronized (tabulatedFunction) {
                y = tabulatedFunction.getY(i);
            }
            System.out.printf("%s, i=%d, x=%f, old y=%f", Thread.currentThread().getName(), i, x, y);
        System.out.println();
            tabulatedFunction.setY(i, y + 3);
            y = tabulatedFunction.getY(i);
            System.out.printf("%s, i=%d, x=%f, new y=%f", Thread.currentThread().getName(), i, x, y);
        System.out.println();

        }
        }
    }

