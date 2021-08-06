import LuhnAlgorithm.LuhnAlgorithmCreate;

import java.util.ArrayList;
import java.util.Random;

public class CreditCardInfo {
    private ArrayList<Integer> number = new ArrayList<>();
    private ArrayList<Integer> pin = new ArrayList<Integer>();
    private Integer balance;

    public CreditCardInfo() {
        number = LuhnAlgorithmCreate.createNumber();
        setPin();
        balance = 0;
    }

    public CreditCardInfo(ArrayList<Integer> n, ArrayList<Integer> p, int balance) {
        number = n;
        pin = p;
        this.balance = balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setPin() {
        for (int i = 0; i < 4; i++) {
            Random random = new Random();
            int sign = random.nextInt(10);
            pin.add(sign);
        }
    }

    public  ArrayList<Integer> getPin() {
        return pin;
    }

    public void setNumber() {
        number.add(4);
        for (int i = 0; i < 5; i++)
            number.add(0);
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            int sign = random.nextInt(10);
            number.add(sign);
        }
    }

    public ArrayList<Integer> getNumber() {
        return number;
    }

}
