package calculator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int bubblegumEarnings = 202;
        int toffeeEarnings = 118;
        int iceCreamEarnings = 2250;
        int milkChocolateEarnings = 1680;
        int doughnutEarnings = 1075;
        int pancakeEarnings = 80;
        int income = bubblegumEarnings + toffeeEarnings + iceCreamEarnings + milkChocolateEarnings + doughnutEarnings + pancakeEarnings;

        System.out.println("Earned amount:");
        System.out.printf("Bubblegum: $%d%n", bubblegumEarnings);
        System.out.printf("Toffee: $%d%n", toffeeEarnings);
        System.out.printf("Ice cream: $%d%n", iceCreamEarnings);
        System.out.printf("Milk chocolate: $%d%n", milkChocolateEarnings);
        System.out.printf("Doughnut: $%d%n", doughnutEarnings);
        System.out.printf("Pancake: $%d%n", pancakeEarnings);
        System.out.println();
        System.out.printf("Income: $%d%n", income);
        System.out.println("Staff expenses:");

        Scanner scanner = new Scanner(System.in);

        int staffExpenses = scanner.nextInt();

        System.out.println("Other expenses:");

        int otherExpenses = scanner.nextInt();

        System.out.printf("Net income: $%d", income - staffExpenses - otherExpenses);
    }
}