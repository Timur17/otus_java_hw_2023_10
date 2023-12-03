package ru.otus.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.test.annotations.*;

public class LifeCycleTest {

    private static final Logger logger = LoggerFactory.getLogger(LifeCycleTest.class);

    @BeforeAll
    public static void globalSetUp() {
        logger.info("Executing globalSetUp");
    }

    @BeforeAll
    public static void globalSetUp2() {
        logger.info("Executing globalSetUp2");
    }

    @BeforeEach
    public void setUp() {

        logger.info("Executing setUp. Hash code LifeCycleTest - '{}'", this.hashCode());
    }

    @BeforeEach
    public void setUp2() {
        logger.info("Executing setUp2. Hash code LifeCycleTest - '{}'", this.hashCode());
    }

    @Test
    public void firstTest() {
        logger.info("Executing firstTest. Hash code LifeCycleTest - '{}'", this.hashCode());
    }

    @SuppressWarnings("java:S112")
    @Test
    public void secondTest() {
        logger.info("Executing secondTest. Hash code LifeCycleTest - '{}'", this.hashCode());
        throw new RuntimeException("Exception during secondTest");
    }

    @Test
    public void thirdTest() {
        logger.info("Executing thirdTest. Hash code LifeCycleTest - '{}'", this.hashCode());
    }

    @AfterEach
    public void tearDown() {
        logger.info("Executing tearDown. Hash code LifeCycleTest - '{}'", this.hashCode());
    }

    @AfterEach
    public void tearDown2() {
        logger.info("Executing tearDown2. Hash code LifeCycleTest - '{}'", this.hashCode());
    }

    @AfterAll
    public static void globalTearDown() {
        logger.info("Executing globalTearDown");
    }

    @AfterAll
    public static void globalTearDown2() {
        logger.info("Executing globalTearDown2");
    }
}
