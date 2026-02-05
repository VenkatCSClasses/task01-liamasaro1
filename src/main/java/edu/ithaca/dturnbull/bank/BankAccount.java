package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if(!isAmountValid(startingBalance)) {
            throw new IllegalArgumentException("Starting balance: " + startingBalance + " is invalid, cannot create account");
        }
        else if (isEmailValid(email)){
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
     * @throws IllegalArgumentException if amount is negative
     * @throws InsufficientFundsException if amount is greater than balance
     * 
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount < 0){
            throw new IllegalArgumentException("Cannot withdraw negative amount.");
        }
        else if(!isAmountValid(amount)){
            throw new IllegalArgumentException("Withdrawal amount: " + amount + " is invalid.");
        }
        else if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money.");
        }
    }

    /**
     * @post increases the balance by amount if amount is non-negative and valid amount
     * @throws IllegalArgumentException if amount is negative or invalid
     */
    public void deposit(double amount) {
        if (amount < 0){
            throw new IllegalArgumentException("Cannot withdraw negative amount.");
        }
        else if(!isAmountValid(amount)){
            throw new IllegalArgumentException("Deposit amount: " + amount + " is invalid.");
        }
        else {
            balance += amount;
        }
    }

    /**
     * @post transfers amount from this account to toAccount if amount is non-negative, valid, and smaller than or equal to balance
     * @throws InsufficientFundsException if amount is greater than balance
     * @throws IllegalArgumentException if amount is negative or invalid
     */
    public void transfer(BankAccount toAccount, double amount) throws InsufficientFundsException {

        if(amount < 0) {
            throw new IllegalArgumentException("Cannot transfer negative amount.");
        }
        else if((!isAmountValid(amount))) {
            throw new IllegalArgumentException("Transfer amount: " + amount + " is invalid.");
        }
        else if(amount <= balance) {
            withdraw(amount);
            toAccount.deposit(amount);
        }
        else {
            throw new InsufficientFundsException("Not enough money to transfer.");
        }
        
    }

    /**
     * @post returns true if email is valid, false otherwise
     * @param email
     */

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
        // Basic check for special characters in the prefix
        
        for(int i = 0; i < prefix.length(); i++){
            char c = prefix.charAt(i);
            if(!Character.isLetterOrDigit(c))
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

    /**
     * @post returns true if amount is non-negative and has at most two decimal places, false otherwise
     * @param amount
     */

    public static boolean isAmountValid(double amount){

        // Positive amount check
        if(amount > 0){
            // String representation of amount
            String amountStr = Double.toString(amount);
            // Check for decimal place
            if(amountStr.contains(".")){
                // String representation of decimal part (exclusive of the decimal point)
                String decimalPart = amountStr.substring(amountStr.indexOf(".") + 1);

                // If there are 2 or more digits after decimal point
                if(decimalPart.length() > 2)
                {
                    // Loop through each digit after decimal point
                    for(int i = 0; i < decimalPart.length(); i++) {

                        // Get digit at index i
                        char c = decimalPart.charAt(i);

                        //if any digit is not 0 (not trailing zeros) return false
                        if(c != '0')
                            return false;
                    }
                    // Trailing zeros only, valid amount
                    return true;
                }
                else {
                    // 2 or fewer digits after decimal point, valid amount
                    return true;
                }
            }

            //Valid positive amount with no decimal places
            return true;
        }

        // Negative amount
        return false;
    }
    }
