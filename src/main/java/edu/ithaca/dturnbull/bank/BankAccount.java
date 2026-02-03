package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        // Check if the string is null or empty
        if (email == null || email.isEmpty()) {
            return false;
        }

        // Check for exactly @ symbol
        int atIndex = email.indexOf('@');
        if (atIndex == -1 || atIndex != email.lastIndexOf('@')) {
            return false;
        }

        // Split the email into prefix and domain
        String prefix = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        // Prefix validation: Cannot be empty, start/end with a dot, or have consecutive dots
        if (prefix.isEmpty() || prefix.startsWith(".") || prefix.endsWith(".") || prefix.contains("..")) {
            return false;
        }

        // Prefix validation: Check for valid characters (alphanumeric, dots, underscores, hyphens)
        // Basic check for special characters at the start
        if (!Character.isLetterOrDigit(prefix.charAt(0))) {
            return false;
        }

        // Domain validation: Must contain a dot, and the dot cannot be at the start or end
        int dotIndex = domain.lastIndexOf('.');
        if (dotIndex <= 0 || dotIndex == domain.length() - 1) {
            return false;
        }

        // Check for whitespace
        if (email.contains(" ")) {
            return false;
        }

        return true;
}
    }
