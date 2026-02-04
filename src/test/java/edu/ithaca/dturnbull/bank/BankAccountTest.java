package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
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
    }

}