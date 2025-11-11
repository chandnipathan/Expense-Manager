import java.io.*;
import java.util.*;

class Expense implements Serializable {
    private String category;
    private double amount;
    private String date;

    public Expense(String category, double amount, String date) {
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Category: " + category + " | Amount: $" + amount + " | Date: " + date;
    }
}

public class ExpenseManager {
    private static final String FILE_NAME = "expenses.dat";
    private static List<Expense> expenses = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadExpenses();
        int choice;
        do {
            System.out.println("\n=== Expense Manager ===");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Total Spent");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addExpense();
                case 2 -> viewExpenses();
                case 3 -> totalSpent();
                case 4 -> saveExpenses();
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }

    private static void addExpense() {
        sc.nextLine();
        System.out.print("Enter Category: ");
        String category = sc.nextLine();
        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        expenses.add(new Expense(category, amount, date));
        System.out.println("Expense added successfully!");
    }

    private static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded!");
        } else {
            for (Expense e : expenses) {
                System.out.println(e);
            }
        }
    }

    private static void totalSpent() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.toString().contains("$") ? e.toString().split("\\$")[1].contains(" ") ? 0 : 0 : 0;
        }
        total = expenses.stream().mapToDouble(e -> Double.parseDouble(e.toString().split("\\$")[1].split(" ")[0])).sum();
        System.out.println("Total spent: $" + total);
    }

    private static void saveExpenses() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(expenses);
            System.out.println("Expenses saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    private static void loadExpenses() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            expenses = (List<Expense>) ois.readObject();
        } catch (Exception e) {
            expenses = new ArrayList<>();
        }
    }
}
