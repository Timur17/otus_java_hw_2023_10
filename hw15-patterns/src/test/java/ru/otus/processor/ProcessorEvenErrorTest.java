package ru.otus.processor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.handler.ComplexProcessor;
import ru.otus.model.Message;
import ru.otus.processor.homework.DateTimeProviderImpl;

class ProcessorEvenErrorTest {

    @Test
    @DisplayName("Тестируем ProcessorThrowException")
    void handleProcessorThrowExceptionIfNonEvesSecondTest() {
        var message = new Message.Builder(1L).field11("field7").build();

        DateTimeProviderImpl dateTimeProvider = new DateTimeProviderImpl();

        Processor processor1 = new ProcessorEvenError(dateTimeProvider);

        var processors = List.of(processor1);

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {});

        var result = complexProcessor.handle(message);

        assertThat(result).isEqualTo(message);

        ProcessorEvenError proc = (ProcessorEvenError) processor1;

        if (dateTimeProvider.isEvenSecond()) {
            System.out.println("OK: " + dateTimeProvider.getSecond());
            assertThat(proc.getRuntimeException()).isNotNull();
        } else {
            System.out.println("KO: " + dateTimeProvider.getSecond());
            assertThat(proc.getRuntimeException()).isNull();
        }
    }
}
