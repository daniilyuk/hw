package reflection.api;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestRunner {

    public static void run(Class<?> testClass) {
        try {
            Object instance = testClass.getDeclaredConstructor().newInstance();
            List<Method> testMethods = getTestMethods(testClass);
            Method beforeSuiteMethod = getBeforeSuiteMethod(testClass);
            Method afterSuiteMethod = getAfterSuiteMethod(testClass);

            invokeMethod(instance, beforeSuiteMethod);

            int[] testResults = executeTests(instance, testMethods);

            invokeMethod(instance, afterSuiteMethod);

            printSummary(testResults, testMethods.size());

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static List<Method> getTestMethods(Class<?> testClass) {
        List<Method> testMethods = new ArrayList<>();
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
        }
        testMethods.sort(Comparator.comparingInt(m -> m.getAnnotation(Test.class).priority()));
        return testMethods;
    }

    private static Method getBeforeSuiteMethod(Class<?> testClass) throws NoSuchMethodException {
        return getAnnotatedMethod(testClass, BeforeSuite.class);
    }

    private static Method getAfterSuiteMethod(Class<?> testClass) throws NoSuchMethodException {
        return getAnnotatedMethod(testClass, AfterSuite.class);
    }

    private static Method getAnnotatedMethod(Class<?> testClass, Class annotationClass) throws NoSuchMethodException {
        Method[] methods = testClass.getDeclaredMethods();
        Method annotatedMethod = null;
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotationClass)) {
                if (annotatedMethod == null)
                    annotatedMethod = method;
                else
                    throw new RuntimeException();
            }
        }
        return annotatedMethod;
    }

    private static void invokeMethod(Object instance, Method method) throws IllegalAccessException, InvocationTargetException {
        if (method != null)
            method.invoke(instance);
    }

    private static int[] executeTests(Object instance, List<Method> testMethods) {
        int successfulTests = 0;
        int failedTests = 0;
        for (Method testMethod : testMethods) {
            try {
                testMethod.invoke(instance);
                successfulTests++;
            } catch (IllegalAccessException | InvocationTargetException e) {
                failedTests++;
                System.err.println("Test failed: " + testMethod.getName());
                e.printStackTrace();
            }
        }
        return new int[]{successfulTests, failedTests};
    }

    private static void printSummary(int[] testResults, int totalTests) {
        System.out.println("Tests summary:");
        System.out.println("Successful: " + testResults[0]);
        System.out.println("Failed: " + testResults[1]);
        System.out.println("Total: " + totalTests);
    }
}


