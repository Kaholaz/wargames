package org.ntnu.vsbugge.wargames;

import junit.framework.TestCase;

public class WargamesCLITest extends TestCase {

    public void testLoadTestData() {
        WargamesCLI cli = new WargamesCLI();
        try {
            cli.loadTestData();
        } catch (IllegalArgumentException e) {
            System.out.println(e.fillInStackTrace());
            fail("Something went wrong in the loading of test data.");
        }
    }
}