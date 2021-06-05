package org.propagate.core.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordUtilsTest {
    @Test
    public void genSalt__correctly_generates_salt() {
        String salt = PasswordUtils.genSalt();
        assertNotNull(salt);
    }

    @Test
    public void hash__correctly_appends_salt() {
        String salt = PasswordUtils.genSalt();
        String hash = PasswordUtils.hash("123", salt);
        assertTrue(hash.startsWith(salt + ":"));
    }

    @Test
    public void check__correctly_matches_passwords() {
        String pw = "123";
        String hash = PasswordUtils.hash(pw, PasswordUtils.genSalt());
        assertTrue(PasswordUtils.check(pw, hash));
    }

}