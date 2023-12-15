package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotation.Log;

public class TestLogging implements TestLoggingInterface {

    private static final Logger logger = LoggerFactory.getLogger(TestLogging.class);

    @Log
    @Override
    public void calculation(int param) {
        logger.info("Executing inside method calculation with: int param");
    }

    @Override
    public void calculation(int param1, int param2) {
        logger.info("Executing inside method calculation with: int param1, int param2");
    }

    @Override
    public void calculation(int param1, int param2, String param3) {
        logger.info("Executing inside method calculation with: int param1, int param2, String param3");
    }
}
