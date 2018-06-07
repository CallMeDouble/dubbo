package com.dragon.functionalInterface;

import java.util.Optional;

/**
 * Created by dragon
 */
public class TestFunctionalInterface {
    public static void main(String[] args) {
        String a = toUpperString((str) -> str.toUpperCase(), "a");
        System.out.println(a);

//
//        Function<Integer, Integer> incr1 = x -> x + 1;
//        Function<Integer, Integer> multiply = x -> x * 2;
//        int x = 2;
//        System.out.println("f(x)=x+1,when x=" + x + ", f(x)=" + incr1.apply(x));
//        System.out.println("f(x)=x+1,g(x)=2x, when x=" + x + ", f(g(x))=" + incr1.compose(multiply).apply(x));
//        System.out.println("f(x)=x+1,g(x)=2x, when x=" + x + ", g(f(x))=" + incr1.andThen(multiply).apply(x));
//        System.out.println("compose vs andThen:f(g(x))=" + incr1.compose(multiply).apply(x) + "," + multiply.andThen(incr1).apply(x));
//
//

        Optional<Integer> canBeEmpty1 = Optional.of(5);
        canBeEmpty1.isPresent();                    // returns true
        canBeEmpty1.get();                          // returns 5
        Optional<Integer> canBeEmpty2 = Optional.empty();
        canBeEmpty2.isPresent();                    // returns false
    }

    public static String toUpperString(MyFunctional<String> myFunctional, String value){
        return myFunctional.getValue(value);
    }
}
