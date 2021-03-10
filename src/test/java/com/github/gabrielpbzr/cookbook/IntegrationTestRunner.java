package com.github.gabrielpbzr.cookbook;

import com.github.gabrielpbzr.cookbook.utils.Configuration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;

/**
 * Custom JUnit Test runner for integration tests
 *
 * @author gabriel
 */
public class IntegrationTestRunner extends Runner {
    private static final int PORT = 50000;
    private Class testClass;
    private Configuration config = Configuration.getInstance();
    private Application app = new Application(config);
    private Logger logger = LoggerFactory.getLogger(IntegrationTestRunner.class);

    public IntegrationTestRunner(Class testClass) {
        super();
        this.testClass = testClass;
        
    }

    @Override
    public Description getDescription() {
        return Description.createSuiteDescription(testClass);
    }

    @Override
    public void run(RunNotifier notifier) {
        String methodName = "";
        try {
            Object testObject = testClass.newInstance();
            app.start(PORT);
            System.out.println("CONTEXT PATH => "+app.getBaseURL());
            for (Method method : testClass.getMethods()) {
                methodName = method.getName();
                if (!method.isAnnotationPresent(Test.class)) {
                    if (method.isAnnotationPresent(Setup.class)) {
                        method.invoke(testObject);
                    }
                    continue;
                }
                Description testDescription = Description.createTestDescription(testClass, method.getName());
                notifier.fireTestStarted(testDescription);
                method.invoke(testObject, app);
                notifier.fireTestFinished(testDescription);
            }
            app.stop();
        } catch (Exception e) {
           logger.error(methodName, e);
        }
    }

}
