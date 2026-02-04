import java.math.BigDecimal;
import java.math.RoundingMode;
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
                // Validation: ตรวจสอบการหารด้วยศูนย์
                if (num2 == 0)
                    throw new ArithmeticException("Cannot divide by zero!");
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }

    // ตัด 0 ที่ไม่ใช่เกรด ตัดเศษที่ไม่ใช่ใจ
    public static String autoFormat(double value) {
        BigDecimal bd = BigDecimal.valueOf(value).stripTrailingZeros();

        // ถ้าเป็นจำนวนเต็ม
        if (bd.scale() <= 0) {
            return bd.toPlainString();
        }

        // ถ้ามีทศนิยมเกิน 3 ตำแหน่งจะปัด
        if (bd.scale() > 3) {
            bd = bd.setScale(3, RoundingMode.HALF_UP)
                    .stripTrailingZeros();
        }

        return bd.toPlainString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // 1. Validate First Number
            System.out.print("Enter first number: ");
            while (!sc.hasNextDouble()) {
                System.out.println("Error: Please enter a valid number.");
                sc.nextLine();
            }
            double n1 = sc.nextDouble();

            // 2. Validate Operator
            System.out.print("Enter operator (+, -, *, /): ");
            char op = sc.next().charAt(0);
            while ("+-*/".indexOf(op) == -1) {
                System.out.print("Error: Invalid operator. Try again (+, -, *, /): ");
                op = sc.next().charAt(0);
            }

            // 3. Validate Second Number
            System.out.print("Enter second number: ");
            while (!sc.hasNextDouble()) {
                System.out.println("Error: Please enter a valid number.");
                sc.next(); // clear buffer
            }
            double n2 = sc.nextDouble();

            // ประมวลผล
            Calculator calc = new Calculator(n1, n2, op);
            double result = calc.calculate();
            System.out.println("Result: " + Calculator.autoFormat(result));

        } catch (ArithmeticException e) {
            System.out.println("Math Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred.");
        } finally {
            sc.close();
        }
    }
}