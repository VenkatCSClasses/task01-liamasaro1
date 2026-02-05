package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {

        // Equivalence Class: Normal balance
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance(), 0.001);

        // Edge Case: Zero balance
        BankAccount bankAccount2 = new BankAccount("z@h.com", 0);
        assertEquals(0, bankAccount2.getBalance(), 0.001);

        // Equivalence Class: Small decimal balance
        BankAccount bankAccount3 = new BankAccount("v@l.com", .30);
        assertEquals(.30, bankAccount3.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
       
        // Valid withdrawal- middle range
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance(), 0.001);

        // Edge case: withdraw entire balance
        bankAccount.withdraw(100);
        assertEquals(0, bankAccount.getBalance(), 0.001);

        // Edge case: withdraw zero
        BankAccount bankAccount2 = new BankAccount("c@ab.com", 150);
        bankAccount2.withdraw(0);
        assertEquals(150, bankAccount2.getBalance(), 0.001);
        
        // Equivalence class: floating point precision with repeated small withdrawals
        BankAccount bankAccount3 = new BankAccount("d@c.com", 1.0);
        for (int i = 0; i < 10; i++) {
            bankAccount3.withdraw(0.1);
        }
        assertEquals(0, bankAccount3.getBalance(), 0.001);

        // Equivalence class: small decimal withdrawal
        BankAccount bankAccount4 = new BankAccount("e@f.com", 50);
        bankAccount4.withdraw(.60);
        assertEquals(49.40, bankAccount4.getBalance(), 0.001);

        // Equivalence class: withdrawal just over balance
        assertThrows(InsufficientFundsException.class, () -> bankAccount4.withdraw(49.80));

        // Exception: isufficient funds
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));

        // Exception: negative withdrawal
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-50));

        // Exception: negative withdrawal
        BankAccount bankAccount5 = new BankAccount("abc@def.com", 500);
        assertThrows(IllegalArgumentException.class, () -> bankAccount5.withdraw(-30));

        // Exception: withdrawal with more than 2 decimal places
        assertThrows(IllegalArgumentException.class, () -> bankAccount5.withdraw(300.6789));


    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // Equivalence Class: Valid email
        assertFalse(BankAccount.isEmailValid(null)); //Boundary case: Null input
        assertFalse( BankAccount.isEmailValid(""));         //Equivalence Class: Empty input
        assertFalse( BankAccount.isEmailValid("ab.com"));    // Equivalence Class: Missing @ symbol
        assertFalse(BankAccount.isEmailValid("a@bcdef")); // Equivalence Class: Missing Domain suffix/dot
        assertFalse(BankAccount.isEmailValid("a@bcdef.")); // Boundary Case: Dot at the end of domain
        assertFalse(BankAccount.isEmailValid("a@b@..c")); // Equivalence Class: Invalid characters/consecutive dots
        assertFalse(BankAccount.isEmailValid("abc_@abc.com")); // Equivalence Class: Invalid character in prefix
        assertFalse(BankAccount.isEmailValid("-abc.com")); // Boundary Case: Prefix starts with a invalid character
        assertFalse(BankAccount.isEmailValid("ab_c@abc.com")); // Equivalence Class: Invalid character in prefix
        assertFalse(BankAccount.isEmailValid("@abc.com")); // Boundary Case: Empty prefix
        assertFalse(BankAccount.isEmailValid("abc@")); // Boundary Case: Empty domain
        assertFalse(BankAccount.isEmailValid(".abc@abc.com")); // Boundary Case: Prefix starts with a dot
        assertFalse(BankAccount.isEmailValid("abc @b.com")); // Equivalence Class: Contains whitespace
        
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));

        // Exception: Invalid Account creation with starting balanca that has more than 2 decinal places
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@bcd.com",200.6578));
        
        // Exception: Invalid Account creation with negative starting balance
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("d@ef.net",-40));
    
    }

    @Test
    void isAmountValidTest(){

        // Boundary Case: Valid zero amount 
        assertTrue(BankAccount.isAmountValid(0)); 

        // Equivalence Class: more thna 2 deicmimal places, should be false
        assertFalse(BankAccount.isAmountValid(30.876534));

        // Boundary Case: valid amount with exactly two decimal places
        assertTrue(BankAccount.isAmountValid(100.67));

        // Equivalence Class: valid amount with one decimal place
        assertTrue(BankAccount.isAmountValid(1025.5));

        // Equivalence Class: negative amount
        assertFalse(BankAccount.isAmountValid(-50));

        // Equivalence Class: valid amount with no decimal places
        assertTrue(BankAccount.isAmountValid(75));

        // Equivalence Class: valid amount with no decimal places, big amount
        assertTrue(BankAccount.isAmountValid(9999));

        // Equivalence class: valid really small amount
        assertTrue(BankAccount.isAmountValid(0.02));

        // Equivalence class: valid really big amount
        assertTrue(BankAccount.isAmountValid(1000000.93));

        // Equivalence class: valid amount with many trailing zeros, should be true
        assertTrue(BankAccount.isAmountValid(45.55000000)); 

        // Equivalence class: valid amount with many trailing zeros, small amount
        assertTrue(BankAccount.isAmountValid(0.1000000));

        // Equivalence class: negative amount 
        assertFalse(BankAccount.isAmountValid(-5000));
    }
}