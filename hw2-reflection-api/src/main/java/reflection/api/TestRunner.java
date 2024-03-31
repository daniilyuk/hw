package reflection.api;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class TestRunner {
    public static void run(Class<?> testClass) {
        try {
            Object instance = testClass.getDeclaredConstructor().newInstance();
            Method[] methods = testClass.getDeclaredMethods();
            List<Method> testMethods = new ArrayList<>();
            Method beforeSuiteMethod = null;
            Method afterSuiteMethod = null;

            for (Method method : methods) {
                if (method.isAnnotationPresent(Test.class)) {
                    testMethods.add(method);
                } else if (method.isAnnotationPresent(BeforeSuite.class)) {
                    if (beforeSuiteMethod == null)
                        beforeSuiteMethod = method;
                    else
                        throw new RuntimeException();
                } else if (method.isAnnotationPresent(AfterSuite.class)) {
                    if (afterSuiteMethod == null)
                        afterSuiteMethod = method;
                    else
                        throw new RuntimeException();
                }
            }

            if (beforeSuiteMethod != null)
                beforeSuiteMethod.invoke(instance);

            Collections.sort(testMethods, Comparator.comparingInt(m -> m.getAnnotation(Test.class).priority()));

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

            if (afterSuiteMethod != null)
                afterSuiteMethod.invoke(instance);

            System.out.println("Tests summary:");
            System.out.println("Successful: " + successfulTests);
            System.out.println("Failed: " + failedTests);
            System.out.println("Total: " + testMethods.size());

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}

