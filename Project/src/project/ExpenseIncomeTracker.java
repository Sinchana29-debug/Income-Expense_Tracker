package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpenseIncomeTracker extends JFrame {
    private JTextField dateField, amountField;
    private JComboBox<String> typeBox, categoryBox;
    private JTable recordTable;
    private DefaultTableModel tableModel;
    private JLabel expenseLabel, incomeLabel, balanceLabel;
    private double totalExpense = 0.0, totalIncome = 0.0;

    public ExpenseIncomeTracker() {
        setTitle("Daily Expense & Income Tracker");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ====== Input Panel ======
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Record"));

        // Date
        inputPanel.add(new JLabel("Date (dd-mm-yyyy):"));
        dateField = new JTextField();
        inputPanel.add(dateField);

        // Type (Expense / Income)
        inputPanel.add(new JLabel("Type:"));
        String[] types = {"Expense", "Income"};
        typeBox = new JComboBox<>(types);
        inputPanel.add(typeBox);

        // Category
        inputPanel.add(new JLabel("Category:"));
        String[] categories = {"Food", "Travel", "Shopping", "Bills", "Salary", "Investment", "Others"};
        categoryBox = new JComboBox<>(categories);
        inputPanel.add(categoryBox);

        // Amount
        inputPanel.add(new JLabel("Amount (₹):"));
        amountField = new JTextField();
        inputPanel.add(amountField);

        // Buttons
        JButton addButton = new JButton("Add Record");
        JButton clearButton = new JButton("Clear");
        inputPanel.add(addButton);
        inputPanel.add(clearButton);

        add(inputPanel, BorderLayout.NORTH);

        // ====== Table Panel ======
        String[] columns = {"Date", "Type", "Category", "Amount (₹)"};
        tableModel = new DefaultTableModel(columns, 0);
        recordTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(recordTable);
        add(scrollPane, BorderLayout.CENTER);

        // ====== Summary Panel ======
        JPanel bottomPanel = new JPanel(new GridLayout(1, 3));
        expenseLabel = new JLabel("Total Expense: ₹0.00", SwingConstants.CENTER);
        incomeLabel = new JLabel("Total Income: ₹0.00", SwingConstants.CENTER);
        balanceLabel = new JLabel("Balance: ₹0.00", SwingConstants.CENTER);

        expenseLabel.setFont(new Font("Arial", Font.BOLD, 13));
        incomeLabel.setFont(new Font("Arial", Font.BOLD, 13));
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 13));

        bottomPanel.add(expenseLabel);
        bottomPanel.add(incomeLabel);
        bottomPanel.add(balanceLabel);
        add(bottomPanel, BorderLayout.SOUTH);

        // ====== Button Actions ======
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRecord();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
    }

    private void addRecord() {
        String date = dateField.getText().trim();
        String type = (String) typeBox.getSelectedItem();
        String category = (String) categoryBox.getSelectedItem();
        String amountText = amountField.getText().trim();

        if (date.isEmpty() || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            Object[] row = {date, type, category, amount};
            tableModel.addRow(row);

            if (type.equals("Expense")) {
                totalExpense += amount;
            } else {
                totalIncome += amount;
            }

            updateSummary();
            clearFields();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount! Enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        dateField.setText("");
        amountField.setText("");
        typeBox.setSelectedIndex(0);
        categoryBox.setSelectedIndex(0);
    }

    private void updateSummary() {
        double balance = totalIncome - totalExpense;
        expenseLabel.setText(String.format("Total Expense: ₹%.2f", totalExpense));
        incomeLabel.setText(String.format("Total Income: ₹%.2f", totalIncome));
        balanceLabel.setText(String.format("Balance: ₹%.2f", balance));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ExpenseIncomeTracker().setVisible(true);
        });
    }
}

