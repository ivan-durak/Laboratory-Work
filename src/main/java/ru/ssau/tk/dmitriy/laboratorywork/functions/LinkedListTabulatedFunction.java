package ru.ssau.tk.dmitriy.laboratorywork.functions;

import ru.ssau.tk.dmitriy.laboratorywork.exceptions.InterpolationException;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction implements Insertable, Removable, Serializable {
    private static final long serialVersionUID = 1179374417522413670L;
    private int count = 0;
    private Node head;

    public static class Node implements Serializable {
        private static final long serialVersionUID = 5996529892347140732L;
        public Node next;
        public Node prev;
        public double x;
        public double y;

        public Node() {
        }

        public Node(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public LinkedListTabulatedFunction(double[] xValues, double[] yValues) {
        if (xValues.length < 2 | yValues.length < 2) {
            throw new IllegalArgumentException("the length of the table is less than the minimum");
        }
        AbstractTabulatedFunction.checkLengthIsTheSame(xValues, yValues);
        AbstractTabulatedFunction.checkSorted(xValues);
        head = null;
        for (int i = 0; i < xValues.length; i++) {
            addNode(xValues[i], yValues[i]);
        }
    }

    public LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (count < 2) {
            throw new IllegalArgumentException("the length of the table is less than the minimum");
        }
        if (xFrom >= xTo) {
            throw new IllegalArgumentException("incorrect end and start coordinates");
        }
        head = null;
        double step = (xTo - xFrom) / (count - 1); //интервал дискретизации
        for (int i = 0; i < count; i++) {
            double value = xFrom + step * i;       //передаваемое значение х, отсчет с xFrom
            addNode(value, source.apply(value));   //y-результат функции source
        }
    }

    private void addNode(double x, double y) {  //метод добавления точки в список
        Node newNode = new Node(x, y);              //конструктор по умолчанию
        if (head == null) {
            head = newNode;
            newNode.next = newNode; //голова списка должна ссылаться на саму себя
            newNode.prev = newNode;
        } else {
            Node last = head.prev;//введение вспомогательной ссылочной переменной, указывающей на конец списка
            head.prev = newNode;  //присваивание ссылки на последнюю точку(пауза)ссылки на только что созданный объект
            newNode.prev = last;  //связывание последнего элемента и только что добавленного
            newNode.next = head;  //связывание только что созданного объекта и головы списка
            last.next = newNode;  //обратное связывание последнего элемента и только что созданного
        }
        count++; //увеличение числа точек в списке
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public double leftBound() {
        return head.x;
    }

    @Override
    public double rightBound() {
        return head.prev.x;
    }

    private Node getNode(int index) {
        if ((index < 0) | (index >= count)) {
            throw new IllegalArgumentException("The invalid index");
        }
        if (index == 0) return head;
        Node desiredNode = null;
        Node help = head;
        if (index <= (double) count / 2) {   //если узел ближе к концу, то идем в обратном направлении
            for (int i = 0; i < index; i++) {//это описано в блоке else
                help = help.next;
                desiredNode = help;
            }
        } else {
            for (int i = 0; i < (count - index); i++) {
                help = help.prev;
                desiredNode = help;
            }
        }
        return desiredNode;
    }

    @Override
    public double getX(int index) {
        if ((index < 0) | (index >= count)) {
            throw new IllegalArgumentException("The invalid index");
        }
        return getNode(index).x;
    }

    @Override
    public double getY(int index) {
        if ((index < 0) | (index >= count)) {
            throw new IllegalArgumentException("The invalid index");
        }
        return getNode(index).y;
    }

    @Override
    public void setY(int index, double value) {
        if ((index < 0) | (index >= count)) {
            throw new IllegalArgumentException("The invalid index");
        }
        getNode(index).y = value;
    }

    @Override
    public int indexOfX(double x) {
        Node help = head;
        for (int i = 0; i <= count; i++) {
            if (help.x == x) {
                return i;
            }
            help = help.next;
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        Node help = head;
        for (int i = 0; i < count; i++) {
            if (help.y == y) {
                return i;
            }
            help = help.next;
        }
        return -1;
    }

    @Override
    public int floorIndexOfX(double x) {
        Node help = head;
        Node outPut = null; //ссылка, предназначенная для выбрасывания наружу
        double difference = 0;
        for (int i = 0; i < count; i++) {
            if (help.x == x) {
                return i;
            }
            if ((difference = x - help.x) > 0) {
                outPut = help;
            }
            help = help.next;
        }
        if (outPut == null) {
            if (difference < 0) {     //выполнение условия: если все х больше переданного, т.к. все х упорядочены
                throw new IllegalArgumentException("x is less than the left border");
            }
        } else if (difference > 0) { //выполнение условия: если все х меньше переданного, т.к. все х упорядочены
            return count;
        }
        return indexOfX(outPut.x);
    }

    protected Node floorNodeOfX(double x) {
        if (x < leftBound()) {
            throw new IllegalArgumentException("x is less than the left border");
        }
        Node outPut = null;
        Node help = head;
        for (int i = 0; i < count; i++) {
            if (x - help.x > 0) {
                outPut = help;
            } else {
                break;
            }
            help = help.next;
        }
        return outPut;
    }

    @Override
    protected double extrapolateLeft(double x) {
        Node firstHelpNode = head;       //начинаем с головы, поэтому метод получения узла не вызывается
        Node secondHelpNode = head.next;
        return super.interpolate(x, firstHelpNode.x, secondHelpNode.x, firstHelpNode.y, secondHelpNode.y);
    }

    @Override
    protected double extrapolateRight(double x) {
        Node secondHelpNode = head.prev;
        Node firstHelpNode = secondHelpNode.prev;//поменяны индексы местами в соответствии с формулой
        return super.interpolate(x, firstHelpNode.x, secondHelpNode.x, firstHelpNode.y, secondHelpNode.y);
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        if ((x < getX(floorIndex)) | (x > getX(floorIndex + 1))) {
            throw new InterpolationException();
        }
        Node helpNode = getNode(floorIndex);  //(k-1)-ый индекс
        return super.interpolate(x, helpNode.x, helpNode.next.x, helpNode.y, helpNode.next.y);
    }

    @Override
    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) { //для метода apply()
        return super.interpolate(x, leftX, rightX, leftY, rightY);
    }

    @Override
    public double apply(double x) {
        if (x < head.x) {
            return extrapolateLeft(x);
        }
        if (x > head.prev.x) {
            return extrapolateRight(x);
        }
        int index;
        if ((index = indexOfX(x)) != -1) { //если такой х есть в таблице
            return getY(index);
        }
        Node desiredNode = floorNodeOfX(x);
        return interpolate(x, desiredNode.x, desiredNode.next.x, desiredNode.y, desiredNode.next.y);
    }

    @Override
    public void insert(double x, double y) {
        if (count == 0) {
            addNode(x, y);
        } else {
            int index;
            if ((index = indexOfX(x)) != -1) {
                setY(index, y);
                return;
            }
            Node temp = head;
            while (true) {
                if (x > temp.x) {
                    if (index < count - 1) {//из-за бесконечного цикла для x>rightBound() будет работать вечно
                        temp = temp.next;
                        index++;
                        continue;
                    }//поэтому выходим, как только заново пошли по списку
                }
                if (index == (count - 1)) {
                    addNode(x, y);
                } else if (index == -1) {
                    Node newNode = new Node(x, y);
                    head.next.prev = head;
                    head.prev.next = newNode;
                    newNode.prev = head.prev;
                    newNode.next = head;
                    head = newNode;
                    count++;
                } else {
                    Node newNode = new Node(x, y);
                    temp.prev.next = newNode;
                    temp.prev = newNode;
                    newNode.prev = temp.prev;
                    newNode.next = temp;
                    count++;
                }
                break;
            }
        }
    }

    @Override
    public void remove(int index) {
        if ((index < 0) | (index > count)) {
            throw new IllegalArgumentException("The invalid index");
        }
        Node delete = getNode(index);
        delete.prev.next = delete.next;
        delete.next.prev = delete.prev;
        if (index == 0) {
            head = delete.next;
        }
        count--;
    }

    @Override
    public Iterator<Point> iterator() {
        return new Iterator<Point>() {
            Node node = head;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public Point next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Point point = new Point(node.x, node.y);
                if (node == head.prev) {
                    node = null;
                } else {
                    node = node.next;
                }
                return point;
            }
        };
    }
}

