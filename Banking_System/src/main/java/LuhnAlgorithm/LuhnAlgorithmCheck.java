package LuhnAlgorithm;

import java.util.ArrayList;

public class LuhnAlgorithmCheck {

    static ArrayList<Integer> makeArray(String info) {
        ArrayList<Integer> number = new ArrayList<Integer>();
        for (int i = 0; i < info.length(); i++)
            number.add(Integer.parseInt(info.charAt(i) + ""));

        return number;
    }

    public static boolean checkNumber(String info) {
        ArrayList<Integer> number = makeArray(info);
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
        return (checkSum == number.get(number.size() - 1));
    }
}
