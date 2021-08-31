package blockchain;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter how many zeros the hash must start with:\n>");
        int numberOfZeros = scanner.nextInt();
        while (numberOfZeros < 0 || numberOfZeros > 15) {
            System.out.println("Number must be between 0 and 6. Try again:\n>");
            numberOfZeros = scanner.nextInt();
        }

        Blockchain blockchain = new Blockchain(numberOfZeros);
        blockchain.add();
        blockchain.add();
        blockchain.add();
        blockchain.add();
        blockchain.add();
    }
}
