import java.util.ArrayList;

public class BankAccount {
    private String accountNumber;
    private String name;
    private String password;
    private double balance;
    private ArrayList<String> transactionHistory = new ArrayList<>();

    public BankAccount(String accNum, String name, String password) {
        this.accountNumber = accNum;
        this.name = name;
        this.password = password;
        this.balance = 0.0;
        transactionHistory.add("Account created with ₹0 balance.");
    }

    public boolean authenticate(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited ₹" + amount);
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && (balance - amount) >= 500) {
            balance -= amount;
            transactionHistory.add("Withdrew ₹" + amount);
            return true;
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<String> getTransactionHistory() {
        return transactionHistory;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public static boolean confirmPassword(BankAccount account) {
        String input = javax.swing.JOptionPane.showInputDialog(null, "Enter password:");
        return account != null && account.authenticate(input);
    }
}
