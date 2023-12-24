package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotation.Log;

public class Ioc {
    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);

    private Ioc() {}

    public static TestLoggingInterface createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(
                Ioc.class.getClassLoader(), new Class<?>[] {TestLoggingInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface myClass;
        private final Set<Method> methodsStore;

        DemoInvocationHandler(TestLoggingInterface testLoggingInterface) {
            this.myClass = testLoggingInterface;
            this.methodsStore = analyzeMethodsInClass();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (methodsStore.contains(method)) {
                logger.info("Proxy for method: '{}', param: '{}'", method.getName(), args);
            }
            return method.invoke(myClass, args);
        }

        private Set<Method> analyzeMethodsInClass() {
            Set<Method> methodsStore = new HashSet<>();
            Method[] methods = myClass.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Log.class)) {
                    Method findedMethod = findMethodInInterface(method);
                    methodsStore.add(findedMethod);
                }
            }
            return methodsStore;
        }

        private Method findMethodInInterface(Method method) {
            String methodName = method.getName();
            Class<?>[] clazz = method.getParameterTypes();
            try {
                for (Class<?> inter : myClass.getClass().getInterfaces()) {
                    return inter.getMethod(methodName, clazz);
                }
            } catch (NoSuchMethodException | SecurityException e) {
                logger.error("Method not found: '{}'", method.getName());
            }
            logger.error(
                    "Class:'{}' does not has interface.", myClass.getClass().getName());
            return null;
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" + "myClass=" + myClass + '}';
        }
    }
}
