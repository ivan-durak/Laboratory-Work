package ru.ssau.tk.dmitriy.laboratorywork.functions;

public abstract class AbstractTabulatedFunction implements TabulatedFunction {
    protected abstract int floorIndexOfX(double x);

    protected abstract double extrapolateLeft(double x);

    protected abstract double extrapolateRight(double x);

    protected abstract double interpolate(double x, int floorIndex);
    public abstract int  getCount ();
    public abstract double  getX ( int  index );
    public abstract  double  getY ( int  index );
    public abstract  void  setY ( int  index , double  value );

    protected double interpolate(double x, double leftx, double rightx, double y, double lefty, double righty) {
        return (lefty + (x - leftx) * (righty - lefty) / (rightx - leftx));

    }


    @Override

    public double apply(double x) {
        if (x < leftBound()) {
            return extrapolateLeft(x);
        } else if (x > rightBound()) {
            return extrapolateRight(x);
        }
        return indexOfX(x) == -1 ? interpolate(x, floorIndexOfX(x)) : getY(indexOfX(x));

    }
}




