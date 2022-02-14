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

    public void testStartWithoutDataThrowsException() {
        WargamesCLI cli = new WargamesCLI();
        try {
            cli.start();
            fail("Starting without loading in data should throw an exception");
        } catch (IllegalStateException e) {
            assertEquals("Data was not loaded before starting the battle", e.getMessage());
        } catch (Exception e) {
            fail("IllegalArgument exception should be thrown when starting without loaded data");
        }
    }
}