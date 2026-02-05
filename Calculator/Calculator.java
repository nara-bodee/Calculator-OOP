import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calculator {
    private double num1;
    private double num2;
    private char operator;

    public Calculator(double num1, double num2, char operator) {
        this.num1 = num1;
        this.num2 = num2;
        this.operator = operator;
    }

    public double calculate() {
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                if (num2 == 0)
                    throw new ArithmeticException("Cannot divide by zero!");
                return num1 / num2;
            case '^':
                return Math.pow(num1, num2);
            case '%':
                if (num2 == 0)
                    throw new ArithmeticException("Cannot modulo by zero!");
                return num1 % num2;
            case 's':
            case 'S':
                if (num1 < 0)
                    throw new ArithmeticException("Cannot calculate square root of negative number!");
                return Math.sqrt(num1);
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }

    public static String autoFormat(double value) {
        BigDecimal bd = BigDecimal.valueOf(value).stripTrailingZeros();
        if (bd.scale() <= 0)
            return bd.toPlainString();
        if (bd.scale() > 3)
            bd = bd.setScale(3, RoundingMode.HALF_UP).stripTrailingZeros();
        return bd.toPlainString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // [NEW] สร้าง List เก็บประวัติ
        List<String> historyLog = new ArrayList<>();
        boolean continueCalculation = true;

        System.out.println("=== Calculator Program ===");

        while (continueCalculation) {
            try {
                // 1. Validate First Number
                System.out.print("\nEnter first number: ");
                while (!sc.hasNextDouble()) {
                    System.out.println("Error: Please enter a valid number.");
                    sc.next();
                }
                double n1 = sc.nextDouble();

                // 2. Validate Operator
                System.out.print("Enter operator (+, -, *, /, ^, %, s[sqrt]): ");
                char op = sc.next().charAt(0);
                while ("+-*/^%sS".indexOf(op) == -1) {
                    System.out.print("Error: Invalid operator. Try again: ");
                    op = sc.next().charAt(0);
                }

                // 3. Validate Second Number
                double n2 = 0;
                if (op != 's' && op != 'S') {
                    System.out.print("Enter second number: ");
                    while (!sc.hasNextDouble()) {
                        System.out.println("Error: Please enter a valid number.");
                        sc.next();
                    }
                    n2 = sc.nextDouble();
                }

                Calculator calc = new Calculator(n1, n2, op);
                double result = calc.calculate();
                String formattedResult = Calculator.autoFormat(result);

                System.out.println("Result: " + formattedResult);

                String equation;
                if (op == 's' || op == 'S') {
                    equation = "sqrt(" + Calculator.autoFormat(n1) + ") = " + formattedResult;
                } else {
                    equation = Calculator.autoFormat(n1) + " " + op + " " + Calculator.autoFormat(n2) + " = "
                            + formattedResult;
                }
                historyLog.add(equation);

            } catch (ArithmeticException e) {
                System.out.println("Math Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred.");
            }

            while (true) {
                System.out.print("\nDo you want to calculate again? (y/n) or view history (h): ");
                String response = sc.next().toLowerCase();

                if (response.equals("h")) {
                    // แสดงประวัติ
                    System.out.println("\n--- Calculation History ---");
                    if (historyLog.isEmpty()) {
                        System.out.println("No history yet.");
                    } else {
                        for (int i = 0; i < historyLog.size(); i++) {
                            System.out.println((i + 1) + ". " + historyLog.get(i));
                        }
                    }

                } else if (response.equals("y") || response.equals("yes")) {
                    continueCalculation = true;
                    break;
                } else if (response.equals("n") || response.equals("no")) {
                    continueCalculation = false;
                    break;
                } else {
                    System.out.println("Invalid input. Please type 'y', 'n', or 'h'.");
                }
            }
        }
        System.out.println("\nThank you for using Calculator!");
        sc.close();
    }
}