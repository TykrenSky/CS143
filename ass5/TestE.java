// Short program to test whether the expressions generated using the file
// sentence2.txt are legal.  They can often be very long.  You should include
// the expression as indicated below and you should be able to compile the
// program.  It might throw an exception when run, but it should compile.

import static java.lang.Math.*;

public class TestE {
    public static void main(String[] args) {
        double x = 3;
        double y = 4;
        double z = - tan ( 42 ) % 92 + ( 92 - 1 - 0 + ( y ) ) + max ( - - pow ( abs ( 92 ) , 42 - y ) - 42 , x % cos ( ( x / 0 / 42 + 1 ) % x ) % 42 );
        System.out.println(z);
    }
}
