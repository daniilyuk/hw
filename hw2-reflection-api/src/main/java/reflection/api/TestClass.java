package reflection.api;

class TestClass {
    @BeforeSuite
    public void beforeSuiteMethod() {
        System.out.println("Before suite method");
    }

    @Test(priority = 10)
    public void testMethod1() {
        System.out.println("Test method 1");
    }

    @Test(priority = 5)
    public void testMethod2() {
        System.out.println("Test method 2");
    }

    @Test(priority = 1)
    public void testMethod3() {
        System.out.println("Test method 3");
    }

    @AfterSuite
    public void afterSuiteMethod() {
        System.out.println("After suite method");
    }
}
