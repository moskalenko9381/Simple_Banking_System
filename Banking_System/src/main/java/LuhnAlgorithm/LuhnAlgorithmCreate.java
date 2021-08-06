package LuhnAlgorithm;

import java.util.ArrayList;
import java.util.Random;

public class LuhnAlgorithmCreate {

    public static ArrayList<Integer> createNumber() {
        ArrayList<Integer> number = new ArrayList<>();
        number.add(4);
        for (int i = 0; i < 5; i++)
            number.add(0);

        for (int i = 0; i < 9; i++) {
            Random random = new Random();
            int sign = random.nextInt(10);
            number.add(sign);
        }


        ArrayList<Integer> checkArray = new ArrayList<>();
        checkArray.addAll(0, number);

        int sum = 0;
        for (int i = 0; i < 15; i++) {
            if (i % 2 == 0) {
                int multiply2 = checkArray.get(i) * 2;
                if (multiply2 > 9)
                    multiply2 -= 9;

                checkArray.set(i, multiply2);
            }
            sum += checkArray.get(i);
        }
        int checkSum = 10 - sum%10;
        if (sum%10 == 0)
            checkSum = 0;
        number.add(checkSum);
        System.out.print("\n");

        return number;
    }
}