package ru.ssau.tk.dmitriy.laboratorywork.functions;

//TODO: когда абстрактный класс будет написан, добавить его в описание данного класса
public class LinkedListTabulatedFunction {
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
        double step = (xTo - xFrom) / (count - 1); //интервал дискретизации
        for (int i = 0; i <= count - 1; i++) {
            double value = xFrom + step * i;       //передаваемое значение х, отсчет с xFrom
            addNode(value, source.apply(value));   //y-результат функции source
        }
    }

    private void addNode(double x, double y) {  //метод добавления точки в список
        Node newNode = new Node();              //конструктор по умолчанию
        newNode.x = x;
        newNode.y = y;
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
        if (index <= (count / 2)) {        //если узел ближе к концу, то идем в обратном направлении
            Node desiredNode = null;     //это описано в блоке else
            Node help = head;
            for (int i = 0; i < index; i++) {
                help = help.next;
                desiredNode = help;
            }
            return desiredNode;
        } else {
            Node desiredNode = null;
            Node help = head;
            for (int i = 0; i < (count - index); i++) {
                help = help.prev;
                desiredNode = help;
            }
            return desiredNode;
        }
    }

    public double getX(int index) { //НЕ ДОЛЖЕН ВЫЗЫВАТЬСЯ ДЛЯ ПЕРВОГО ЭЛЕМЕНТА ТАБЛИЦЫ
        return getNode(index).x;
    }

    public double getY(int index) { //НЕ ДОЛЖЕН ВЫЗЫВАТЬСЯ ДЛЯ ПЕРВОГО ЭЛЕМЕНТА ТАБЛИЦЫ
        return getNode(index).y;
    }

    public void setY(int index, double value) {
        getNode(index).y = value;
    }

    public int indexOfX(double x) { //можно переписать с getX(),
        Node help = head;           //если скажут
        for (int i = 0; i <= count; i++) {
            if (help.x == x) return i;
            help = help.next;
        }
        return -1;
    }

    public int indexOfY(double y) {  //можно переписать с getY(), если
        Node help = head;            //если скажут
        int i;                       //в цикле идем по списку в одну сторону, при совпадении значения у и аргумента
        for (i = 0; i < count; i++) {//мгновенный выход из метода
            if (help.y == y) return i;
            help = help.next;
        }
        return -1;
    }

    public int floorIndexOfX(double x) {
        Node help = head;
        Node outPut = null; //ссылка, предназначенная для выбрасывания наружу
        double difference = 0;
        int i;
        for (i = 0; i < count; i++) {  //проходимся в цикле по списку от начала и до конца
            if (help.x == x)
                return i; //взято из indexOfX, можно было вызывать сам этот метод, но зачем ради одной строки
            if ((difference = x - help.x) > 0) {//здесь находится самый максимальный х в списке, который меньше переданного
                outPut = help;                  //сохраняем ссылку на него
            }                                   //если какой-то х совпадет, то это будет отловлено двумя строчками выше
            help = help.next;
        }
        if (outPut == null) {                   //выполнение условия: если все х меньше переданного, т.к. все х упорядочены
            if (difference < 0)
                return 0;       //выполнение условия: если все х больше переданного, т.к. все х упорядочены
        } else if (difference > 0) return count;//IDEA говорит, что outPut может сгенерировать NullPointerException,
        return indexOfX(outPut.x);              // но по логике она никогда не будет пустой
    }

    protected double extrapolateLeft(double x) {
        if (count == 1) return head.x;
        Node firstHelpNode = head;       //начинаем с головы, поэтому метод получения узла не вызывается
        Node secondHelpNode = head.next;
        return firstHelpNode.y + ((secondHelpNode.y - firstHelpNode.y) / (secondHelpNode.x - firstHelpNode.x))//
                * (x - firstHelpNode.x);
    }

    protected double extrapolateRight(double x) {
        if (count == 1) return head.x;
        Node secondHelpNode = head.prev;       //начинаем с головы, поэтому метод получения узла не вызывается
        Node firstHelpNode = secondHelpNode.prev;//поменяны индексы местами в соответствии с формулой
        return firstHelpNode.y + ((secondHelpNode.y - firstHelpNode.y) / (secondHelpNode.x - firstHelpNode.x))//
                * (x - firstHelpNode.x);
    }

    protected double interpolate(double x, int floorIndex) {
        if (count == 1) return head.x;
        Node helpNode = getNode(floorIndex);  //(k-1)-ый индекс
        return helpNode.y + ((helpNode.next.y - helpNode.y) / (helpNode.next.x - helpNode.x)) * (x - helpNode.x);
    }
    //TODO: сделать задание со звездочкой+тесты и 3*
}
