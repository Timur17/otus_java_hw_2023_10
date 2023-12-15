package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
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
        TestLoggingInterface myClass;

        DemoInvocationHandler(TestLoggingInterface testLoggingInterface) {
            this.myClass = testLoggingInterface;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method m = findMethodInClass(method);
            if (m.isAnnotationPresent(Log.class)) {
                logger.info("Proxy for method: '{}', param: '{}'", m.getName(), args);
            }
            return method.invoke(myClass, args);
        }

        private Method findMethodInClass(Method method) {
            Parameter[] parameters = method.getParameters();
            List<?> parameterList =
                    Arrays.stream(parameters).map(Parameter::getType).toList();
            Class<?>[] clazz = new Class<?>[parameterList.size()];
            parameterList.toArray(clazz);
            Method res;
            try {
                res = myClass.getClass().getDeclaredMethod(method.getName(), clazz);
            } catch (NoSuchMethodException e) {
                logger.error("Method not found", e);
                throw new RuntimeException(e);
            }
            return res;
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" + "myClass=" + myClass + '}';
        }
    }
}
