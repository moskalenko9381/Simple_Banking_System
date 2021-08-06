public class Main {
    public static void main(String[] args) {
        if (args[0].equals("-fileName")) {
            CardDatabase base = new CardDatabase("jdbc:sqlite:" + args[1]);
            base.createDatabase();
            Menu menu = new Menu(base);
            menu.processBefore();
        }
    }
}
