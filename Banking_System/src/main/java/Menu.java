import LuhnAlgorithm.LuhnAlgorithmCheck;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.exit;

public class Menu {
    private CardDatabase base;

    public Menu(CardDatabase dataBase) {
        base = dataBase;
    }

    public void printMenuNew() {
        System.out.println("1. Create an account" + '\n' +
                "2. Log into account" + '\n' +
                "0. Exit");
    }

    public void printMenuLogged() {
        System.out.println("1. Balance" + '\n' +
                "2. Add income" + '\n' +
                "3. Do transfer" + '\n' +
                "4. Close account" + '\n' +
                "5. Log out" + '\n' +
                "0. Exit");
    }

    public String getString(ArrayList<Integer> cardInfo) {
        String cardNumber = "";
        for (Integer i: cardInfo)
            cardNumber += i.toString();
        return cardNumber;
    }

    public void processBefore() {
        Scanner scanner = new Scanner(System.in);
        int choice = -3;
        while (choice != 0) {
            printMenuNew();
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    int id = base.countRows() + 1;
                    CreditCardInfo card = new CreditCardInfo();

                    String insert = "INSERT INTO card VALUES " + "(" +
                            id + "," +
                            getString(card.getNumber()) + ", " +
                            getString(card.getPin()) + "," +
                            card.getBalance().toString() + ")";

                    int i = base.addString(insert);
                    System.out.println("Your card has been created");
                    String cardNumber = getString(card.getNumber());
                    System.out.println("Your card number:\n" + cardNumber);
                    String pinStr = getString(card.getPin());
                    System.out.println("Your card PIN:\n" + pinStr);
                    break;

                case 2:
                    System.out.println("Enter your card number:");
                    String number = scanner.next();
                    String regexNumber = "400000\\d{10}";
                    System.out.println("Enter your PIN:");
                    String pin = scanner.next();
                    String regexPin = "\\d{4}";
                    if (!number.matches(regexNumber) || !pin.matches(regexPin) || !LuhnAlgorithmCheck.checkNumber(number)) {
                        System.out.println("Wrong card number or PIN!");
                        break;
                    }

                    CreditCardInfo credit = base.findCard(number, pin);
                    if (credit != null) {
                        System.out.println("You have successfully logged in!");
                        processInAccount(credit, base);
                        break;
                    }

                    System.out.println("Wrong card number or PIN!");
                    break;
                case 0:
                    System.out.println("Bye!");
                    exit(0);
                default:
                    System.out.println("Try again!");
            }
        }
    }

    public void processInAccount(CreditCardInfo currentCard, CardDatabase base) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 0) {
            printMenuLogged();
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Balance: " + currentCard.getBalance());
                    break;
                case 2:
                    System.out.println("Enter income:");
                    int income = scanner.nextInt();
                    addIncome(currentCard, income);
                    break;
                case 3:
                    doTransfer(currentCard);
                    break;
                case 4:
                    base.closeAccount(getString(currentCard.getNumber()));
                    System.out.println("The account has been closed!");
                    processBefore();
                    break;
                case 5:
                    System.out.println("You have successfully logged out!");
                    processBefore();
                    break;
                case 0:
                    System.out.println("Bye!");
                    exit(0);
                default:
                    System.out.println("Try again!");
            }
        }
    }

    public void doTransfer(CreditCardInfo currentCard) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Transfer\n" +
                "Enter card number:");
        String number = scanner.next();

        String regexNumber = "400000\\d{10}";

        if (!LuhnAlgorithmCheck.checkNumber(number)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
            System.out.println(number + " " + number.length());
            return;
        }

        CreditCardInfo card = base.findCardForTransfer(number);

        if (!number.matches(regexNumber) && card == null) {
            System.out.println("Such a card does not exist.");
            return;
        }

        if (getString(currentCard.getNumber()).equals(getString(card.getNumber()))) {
            System.out.println("You can't transfer money to the same account!");
            return;
        }

        System.out.println("Enter how much money you want to transfer:");
        int income = scanner.nextInt();
        int currentBalance = base.getBalance(getString(currentCard.getNumber()));
        if (income > currentBalance) {
            System.out.println("Not enough money!");
            return;
        }
        addIncome(card, income);
        addIncome(currentCard, income - 2*income);
        System.out.println("Success!");
    }


    public void addIncome(CreditCardInfo currentCard, int income) {
        String number = getString(currentCard.getNumber());
        base.addIncome(income, number);
        int balance = base.getBalance(number);
        currentCard.setBalance(balance);
    }
}
