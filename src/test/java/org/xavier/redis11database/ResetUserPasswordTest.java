package org.xavier.redis11database;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResetUserPasswordTest {

    @Test
    public void resetPasswordAndSwapDatabase() {
        new ResetUserPassword().resetPasswordAndSwapDatabase();
    }
}