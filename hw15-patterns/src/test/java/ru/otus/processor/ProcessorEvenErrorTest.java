package ru.otus.processor;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.handler.ComplexProcessor;
import ru.otus.model.Message;
import ru.otus.processor.homework.DateTimeProvider;

class ProcessorEvenErrorTest {

    @Test
    @DisplayName("Тестируем ProcessorThrowException нечетная секунда")
    void handleProcessorThrowExceptionIfEvesSecondTest() {
        DateTimeProvider dateTimeProvider = Mockito.mock(DateTimeProvider.class);
        Mockito.when(dateTimeProvider.getDate()).thenReturn(LocalDateTime.now().withSecond(1));

        var message = new Message.Builder(1L).field11("field7").build();

        Processor processor1 = new ProcessorEvenError(dateTimeProvider);

        var processors = List.of(processor1);

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {});

        var result = complexProcessor.handle(message);

        assertThat(result).isEqualTo(message);

        ProcessorEvenError proc = (ProcessorEvenError) processor1;
        assertThat(proc.getRuntimeException()).isNull();
    }

    @Test
    @DisplayName("Тестируем ProcessorThrowException четная секунда")
    void handleProcessorThrowExceptionIfNonEvesSecondTest() {
        DateTimeProvider dateTimeProvider = Mockito.mock(DateTimeProvider.class);
        Mockito.when(dateTimeProvider.getDate()).thenReturn(LocalDateTime.now().withSecond(2));

        var message = new Message.Builder(1L).field11("field7").build();

        Processor processor1 = new ProcessorEvenError(dateTimeProvider);

        var processors = List.of(processor1);

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {});

        var result = complexProcessor.handle(message);

        assertThat(result).isEqualTo(message);

        ProcessorEvenError proc = (ProcessorEvenError) processor1;
        assertThat(proc.getRuntimeException()).isNotNull();
    }
}
