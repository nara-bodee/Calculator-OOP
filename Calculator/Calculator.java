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
            case '^':
                // Power (ยกกำลัง)
                return Math.pow(num1, num2);
            case '%':
                // Modulo (หารเอาเศษ)
                if (num2 == 0)
                    throw new ArithmeticException("Cannot modulo by zero!");
                return num1 % num2;
            case 's':
            case 'S':
                // Square Root (รากที่สอง) - ใช้ num1 เท่านั้น
                if (num1 < 0)
                    throw new ArithmeticException("Cannot calculate square root of negative number!");
                return Math.sqrt(num1);
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
        boolean continueCalculation = true;

        System.out.println("=== Calculator Program ===");

        while (continueCalculation) {
            try {
                // 1. Validate First Number
                System.out.print("\nEnter first number: ");
                while (!sc.hasNextDouble()) {
                    System.out.println("Error: Please enter a valid number.");
                    sc.nextLine();
                }
                double n1 = sc.nextDouble();

                // 2. Validate Operator
                System.out.print("Enter operator (+, -, *, /, ^, %, s[sqrt]): ");
                char op = sc.next().charAt(0);
                while ("+-*/^%sS".indexOf(op) == -1) {
                    System.out.print("Error: Invalid operator. Try again (+, -, *, /, ^, %, s[sqrt]): ");
                    op = sc.next().charAt(0);
                }

                // 3. Validate Second Number (ไม่ต้องใช้สำหรับ square root)
                double n2 = 0;
                if (op != 's' && op != 'S') {
                    System.out.print("Enter second number: ");
                    while (!sc.hasNextDouble()) {
                        System.out.println("Error: Please enter a valid number.");
                        sc.nextLine();
                    }
                    n2 = sc.nextDouble();
                } else {
                    System.out.println("(Square root uses only the first number)");
                }

                // ประมวลผล
                Calculator calc = new Calculator(n1, n2, op);
                double result = calc.calculate();
                System.out.println("Result: " + Calculator.autoFormat(result));

            } catch (ArithmeticException e) {
                System.out.println("Math Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred.");
            }

            // ถามว่าต้องการคำนวณต่อหรือไม่
            System.out.print("\nDo you want to calculate again? (y/n): ");
            String response = sc.next().toLowerCase();
            continueCalculation = response.equals("y") || response.equals("yes");
        }

        System.out.println("\nThank you for using Calculator!");
        sc.close();
    }
}