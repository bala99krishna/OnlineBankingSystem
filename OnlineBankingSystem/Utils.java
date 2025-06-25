import java.util.HashMap;

public class Utils {
    public static HashMap<String, BankAccount> accounts = new HashMap<>();

    public static boolean accountExists(String accNum) {
        return accounts.containsKey(accNum);
    }

    public static void createAccount(String accNum, String name, String pass) {
        accounts.put(accNum, new BankAccount(accNum, name, pass));
    }

    public static BankAccount getAccount(String accNum) {
        return accounts.get(accNum);
    }
}
