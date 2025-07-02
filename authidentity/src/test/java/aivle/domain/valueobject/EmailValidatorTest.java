package aivle.domain.valueobject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EmailValidatorTest {

    @Test
    public void testValidEmails() {
        assertTrue(EmailValidator.isValidEmail("test@example.com"));
        assertTrue(EmailValidator.isValidEmail("user.name@domain.co.uk"));
        assertTrue(EmailValidator.isValidEmail("user+tag@example.org"));
        assertTrue(EmailValidator.isValidEmail("user123@test-domain.com"));
    }

    @Test
    public void testInvalidEmails() {
        assertFalse(EmailValidator.isValidEmail("invalid-email"));
        assertFalse(EmailValidator.isValidEmail("@example.com"));
        assertFalse(EmailValidator.isValidEmail("user@"));
        assertFalse(EmailValidator.isValidEmail("user@.com"));
        assertFalse(EmailValidator.isValidEmail(""));
        assertFalse(EmailValidator.isValidEmail(null));
    }

    @Test
    public void testValidateEmailThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            EmailValidator.validateEmail("invalid-email");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            EmailValidator.validateEmail(null);
        });
    }

    @Test
    public void testValidateEmailDoesNotThrowException() {
        assertDoesNotThrow(() -> {
            EmailValidator.validateEmail("valid@example.com");
        });
    }
} 