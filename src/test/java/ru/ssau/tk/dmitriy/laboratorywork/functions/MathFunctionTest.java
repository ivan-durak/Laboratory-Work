package ru.ssau.tk.dmitriy.laboratorywork.functions;
import  org.testng.Assert ;
import  org.testng.annotations.Test ;



public  class  MathFunctionTest {
    @Test
    public  void  testAndThen () {


        ConstantFunction constantObject =  new  ConstantFunction ( 2 );
        IdentityFunction identityObject =  new  IdentityFunction ();
        ReverseFunction reverseObject =  new  ReverseFunction ();
        CubeFunction cubeObject =  new  CubeFunction ();
        SquareFunction  squareObject =  new  SquareFunction ();
        double Number =  2.5 ;


        Assert. assertEquals (identityObject . andThen (reverseObject) . andThen (cubeObject) . apply (Number), 0.064, 0.000000001);
        Assert . assertEquals (constantObject . andThen (cubeObject) . andThen (squareObject) . apply (Number), 64,0.00000001);

    }

}