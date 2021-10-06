package ru.ssau.tk.dmitriy.laboratorywork.functions;

class Node {
    public Node next;  //следующий
    public Node prev;  //предыдущий
    public double x;
    public double y;

    public Node() {
    }
    public Node(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
