package ru.ssau.tk.dmitriy.laboratorywork.functions;


public class LinkedListTabulatedFunction extends AbstractTabulatedFunction implements Insertable, Removable {
    private int count = 0;
    private Node head;

    public LinkedListTabulatedFunction(double[] xValues, double[] yValues) {
        head = null;
        for (int i = 0; i < xValues.length; i++) {
            addNode(xValues[i], yValues[i]);
        }
    }

    public LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        head = null;
        double step = (xTo - xFrom) / count; //интервал дискретизации
        for (int i = 0; i <= count; i++) {
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

    public int getCount() {
        return count;
    }

    public double leftBound() {
        return head.x;
    }

    public double rightBound() {
        return head.prev.x;
    }

    private Node getNode(int index) {    //метод поиска узла по индексу
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

    public double getX(int index) {
        return getNode(index).x;
    }

    public double getY(int index) {
        return getNode(index).y;
    }

    public void setY(int index, double value) {
        getNode(index).y = value;
    }

    public int indexOfX(double x) { //можно переписать с getX(),
        Node help = head;           //если скажут
        for (int i = 0; i <= count; i++) {
            if (help.x == x) {
                return i;
            }
            help = help.next;
        }
        return -1;
    }

    public int indexOfY(double y) {  //можно переписать с getY(), если
        Node help = head;            //если скажут
        for (int i = 0; i < count; i++) {//в цикле идем по списку в одну сторону, при совпадении значения у и аргумента
            if (help.y == y) {
                return i;    //мгновенный выход из метода
            }
            help = help.next;
        }
        return -1;
    }

    /**
     * из-за особенностей реализации здесь не может быть вызван floorNodeOfX(), т.к. в таком случае мы не сможем
     * определить частные случаи возвращения 0 и count
     */
    public int floorIndexOfX(double x) {
        Node help = head;
        Node outPut = null; //ссылка, предназначенная для выбрасывания наружу
        double difference = 0;
        for (int i = 0; i < count; i++) {  //проходимся в цикле по списку от начала и до конца
            if (help.x == x) {
                return i;
            } //взято из indexOfX, можно было вызывать сам этот метод, но зачем ради одной строки
            if ((difference = x - help.x) > 0) {//здесь находится самый максимальный х в списке, который меньше переданного
                outPut = help;                  //сохраняем ссылку на него
            }                                   //если какой-то х совпадет, то это будет отловлено двумя строчками выше
            help = help.next;
        }
        if (outPut == null) {
            if (difference < 0) {     //выполнение условия: если все х больше переданного, т.к. все х упорядочены
                return 0;
            }
        } else if (difference > 0) { //выполнение условия: если все х меньше переданного, т.к. все х упорядочены
            return count;
        }
        return indexOfX(outPut.x); //IDEA говорит, что outPut может сгенерировать NullPointerException, но по логике она никогда не будет пустой
    }

    /**
     * взята реализация floorIndexOfX(),т.к. этот метод был написан раньше по схожему принципу
     * для повышения производительности
     * <p>
     * тест для этого метода написан в тесте для метода floorIndexOfX(), там сравниваются индексы возращенных элементов
     */
    protected Node floorNodeOfX(double x) {
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

    protected double extrapolateLeft(double x) {
        if (count == 1) return head.x;
        Node firstHelpNode = head;       //начинаем с головы, поэтому метод получения узла не вызывается
        Node secondHelpNode = head.next;
        return super.interpolate(x, firstHelpNode.x, secondHelpNode.x, firstHelpNode.y, secondHelpNode.y);
    }

    protected double extrapolateRight(double x) {
        if (count == 1) return head.x;
        Node secondHelpNode = head.prev;       //начинаем с головы, поэтому метод получения узла не вызывается
        Node firstHelpNode = secondHelpNode.prev;//поменяны индексы местами в соответствии с формулой
        return super.interpolate(x, firstHelpNode.x, secondHelpNode.x, firstHelpNode.y, secondHelpNode.y);
    }

    protected double interpolate(double x, int floorIndex) {
        if (count == 1) return head.x;
        Node helpNode = getNode(floorIndex);  //(k-1)-ый индекс
        return super.interpolate(x, helpNode.x, helpNode.next.x, helpNode.y, helpNode.next.y);
    }

    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) { //для метода apply()
        if (count == 1) return head.x;
        return super.interpolate(x, leftX, rightX, leftY, rightY);
    }

    public double apply(double x) {
        if (x < head.x) return extrapolateLeft(x);//левая экстраполяция для х меньше самого левого
        if (x > head.prev.x) return extrapolateRight(x);//правая экстраполяция для х больше самого правого
        Node desiredNode = floorNodeOfX(x);          //получаем нужный узел
        return interpolate(x, desiredNode.x, desiredNode.next.x, desiredNode.y, desiredNode.next.y);
    }

    public void insert(double x, double y) {
        Node temp = head;
        for (int i = 1; i < count; i++) {
            if (temp.x == x) {
                temp.y = y;
                break;
            } else if (temp.x > x) {
                Node newNode = new Node(x, y);
                newNode.next = temp;
                newNode.prev = temp.prev;
                temp.prev.next = newNode;
                temp.prev = newNode;
                count++;
                break;
            }
            temp = temp.next;
        }
    }

    public void remove(int index) {
        Node temp = head;
        for (int i = 0; i < count; i++) {
            if (i == index) {
                temp.prev.next = temp.next;
                temp.next.prev = temp.prev;
                temp.next = null;
                temp.prev = null;
                count--;
                break;
            }
            temp = temp.next;
        }
    }
}

