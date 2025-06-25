import java.util.ArrayList;
import javax.swing.*;

public class BankSystem {
    private static JFrame frame;
    private static BankAccount currentAccount;

    public static void main(String[] args) {
        showLoginScreen();
    }

    static void showLoginScreen() {
        frame = new JFrame("Bank Login");
        frame.setSize(350, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel accLabel = new JLabel("Account Number:");
        accLabel.setBounds(20, 30, 120, 25);
        frame.add(accLabel);

        JTextField accField = new JTextField();
        accField.setBounds(150, 30, 150, 25);
        frame.add(accField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 70, 120, 25);
        frame.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(150, 70, 150, 25);
        frame.add(passField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(50, 120, 100, 30);
        frame.add(loginBtn);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(170, 120, 100, 30);
        frame.add(registerBtn);

        frame.setVisible(true);

        loginBtn.addActionListener(e -> {
            String acc = accField.getText();
            String pass = new String(passField.getPassword());
            if (Utils.accountExists(acc) && Utils.getAccount(acc).authenticate(pass)) {
                currentAccount = Utils.getAccount(acc);
                frame.dispose();
                showDashboard();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials");
            }
        });

        registerBtn.addActionListener(e -> {
            frame.dispose();
            showRegistrationScreen();
        });
    }

    static void showRegistrationScreen() {
        JFrame regFrame = new JFrame("Register Account");
        regFrame.setSize(350, 300);
        regFrame.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 30, 100, 25);
        regFrame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(130, 30, 170, 25);
        regFrame.add(nameField);

        JLabel accLabel = new JLabel("Account No:");
        accLabel.setBounds(20, 70, 100, 25);
        regFrame.add(accLabel);

        JTextField accField = new JTextField();
        accField.setBounds(130, 70, 170, 25);
        regFrame.add(accField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 110, 100, 25);
        regFrame.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(130, 110, 170, 25);
        regFrame.add(passField);

        JButton createBtn = new JButton("Create");
        createBtn.setBounds(110, 160, 100, 30);
        regFrame.add(createBtn);

        regFrame.setVisible(true);

        createBtn.addActionListener(e -> {
            String name = nameField.getText();
            String acc = accField.getText();
            String pass = new String(passField.getPassword());

            if (Utils.accountExists(acc)) {
                JOptionPane.showMessageDialog(regFrame, "Account already exists!");
            } else {
                Utils.createAccount(acc, name, pass);
                JOptionPane.showMessageDialog(regFrame, "Account created!");
                regFrame.dispose();
                showLoginScreen();
            }
        });
    }

    static void showDashboard() {
        JFrame dash = new JFrame("Welcome, " + currentAccount.getName());
        dash.setSize(400, 400);
        dash.setLayout(null);

        JButton depBtn = new JButton("Deposit");
        depBtn.setBounds(50, 50, 130, 30);
        dash.add(depBtn);

        JButton withBtn = new JButton("Withdraw");
        withBtn.setBounds(200, 50, 130, 30);
        dash.add(withBtn);

        JButton balBtn = new JButton("Check Balance");
        balBtn.setBounds(50, 100, 130, 30);
        dash.add(balBtn);

        JButton transBtn = new JButton("Transaction History");
        transBtn.setBounds(200, 100, 130, 30);
        dash.add(transBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(125, 160, 130, 30);
        dash.add(logoutBtn);

        dash.setVisible(true);

        depBtn.addActionListener(e -> {
            if (!BankAccount.confirmPassword(currentAccount)) {
                JOptionPane.showMessageDialog(dash, "Wrong password!");
                return;
            }
            String amt = JOptionPane.showInputDialog(dash, "Enter amount to deposit:");
            try {
                double value = Double.parseDouble(amt);
                if (currentAccount.deposit(value)) {
                    JOptionPane.showMessageDialog(dash, "₹" + value + " deposited!");
                } else {
                    JOptionPane.showMessageDialog(dash, "Invalid amount!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dash, "Invalid input!");
            }
        });

        withBtn.addActionListener(e -> {
            if (!BankAccount.confirmPassword(currentAccount)) {
                JOptionPane.showMessageDialog(dash, "Wrong password!");
                return;
            }
            String amt = JOptionPane.showInputDialog(dash, "Enter amount to withdraw:");
            try {
                double value = Double.parseDouble(amt);
                if (currentAccount.withdraw(value)) {
                    JOptionPane.showMessageDialog(dash, "₹" + value + " withdrawn!");
                } else {
                    JOptionPane.showMessageDialog(dash, "Invalid amount or minimum ₹500 balance required.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dash, "Invalid input!");
            }
        });

        balBtn.addActionListener(e -> {
            if (!BankAccount.confirmPassword(currentAccount)) {
                JOptionPane.showMessageDialog(dash, "Wrong password!");
                return;
            }
            JOptionPane.showMessageDialog(dash, "Current Balance: ₹" + currentAccount.getBalance());
        });

        transBtn.addActionListener(e -> {
            ArrayList<String> history = currentAccount.getTransactionHistory();
            JTextArea area = new JTextArea();
            for (String h : history) {
                area.append(h + "\n");
            }
            JOptionPane.showMessageDialog(dash, new JScrollPane(area), "Transaction History",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        logoutBtn.addActionListener(e -> {
            dash.dispose();
            showLoginScreen();
        });
    }
}
