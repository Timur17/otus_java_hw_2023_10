package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotation.Log;
import ru.otus.entity.MethodEntity;

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
        private final Map<MethodEntity, Method> methodsStore;

        DemoInvocationHandler(TestLoggingInterface testLoggingInterface) {
            this.myClass = testLoggingInterface;
            this.methodsStore = analyzeMethodsInClass();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Class<?>[] clazz = getMethodParams(method);
            MethodEntity methodEntity = new MethodEntity(method.getName(), clazz);
            Method m = methodsStore.get(methodEntity);
            if (m.isAnnotationPresent(Log.class)) {
                logger.info("Proxy for method: '{}', param: '{}'", m.getName(), args);
            }
            return method.invoke(myClass, args);
        }

        private Map<MethodEntity, Method> analyzeMethodsInClass() {
            final Map<MethodEntity, Method> methodsStore = new HashMap<>();
            Method[] methods = myClass.getClass().getMethods();
            for (Method method : methods) {
                Class<?>[] clazz = getMethodParams(method);
                Method findedMethod;
                try {
                    findedMethod = myClass.getClass().getDeclaredMethod(method.getName(), clazz);
                    MethodEntity methodEntity = new MethodEntity(method.getName(), clazz);
                    methodsStore.put(methodEntity, findedMethod);
                } catch (NoSuchMethodException e) {
                    logger.trace("Method not found: '{}'", method.getName());
                }
            }
            return methodsStore;
        }

        private Class<?>[] getMethodParams(Method method) {
            Parameter[] parameters = method.getParameters();
            List<?> parameterList =
                    Arrays.stream(parameters).map(Parameter::getType).toList();
            Class<?>[] clazz = new Class<?>[parameterList.size()];
            parameterList.toArray(clazz);
            return clazz;
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" + "myClass=" + myClass + '}';
        }
    }
}
