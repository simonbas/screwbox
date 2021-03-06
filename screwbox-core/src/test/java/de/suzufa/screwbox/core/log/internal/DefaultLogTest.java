package de.suzufa.screwbox.core.log.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import de.suzufa.screwbox.core.log.LogLevel;
import de.suzufa.screwbox.core.log.LoggingAdapter;

@ExtendWith(MockitoExtension.class)
class DefaultLogTest {

    @InjectMocks
    DefaultLog log;

    @Mock
    LoggingAdapter loggingAdapter;

    @Test
    void info_invokesLoggingAdapter() {
        log.info("message-text");

        verify(loggingAdapter).log(LogLevel.INFO, "message-text");
    }

    @Test
    void debug_invokesLoggingAdapter() {
        log.debug("message-text");

        verify(loggingAdapter).log(LogLevel.DEBUG, "message-text");
    }

    @Test
    void warn_invokesLoggingAdapter() {
        log.warn("message-text");

        verify(loggingAdapter).log(LogLevel.WARNING, "message-text");
    }

    @Test
    void error_invokesLoggingAdapter() {
        log.error("message-text");

        verify(loggingAdapter).log(LogLevel.ERROR, "message-text");
    }

    @Test
    void setAdapter_changesLoggingAdapter() {
        LoggingAdapter alternativeAdapter = Mockito.mock(LoggingAdapter.class);

        log.setAdapter(alternativeAdapter);
        log.log(LogLevel.INFO, "logging adapter changed");

        verify(loggingAdapter, never()).log(any(), any());
        verify(alternativeAdapter).log(LogLevel.INFO, "logging adapter changed");
    }

    @Test
    void setMinimumSeverity_levelWarning_disablesLoggingInfoAndDebug() {
        log.setMinimumSeverity(LogLevel.WARNING);
        log.info("not logged");
        log.debug("also not logged");

        verify(loggingAdapter, never()).log(any(), any());
    }

    @Test
    void setMinimumSeverity_levelWarning_allowsLoggingWarningAndError() {
        log.setMinimumSeverity(LogLevel.WARNING);
        log.warn("logged");
        log.error("also logged");

        verify(loggingAdapter).log(LogLevel.WARNING, "logged");
        verify(loggingAdapter).log(LogLevel.ERROR, "also logged");
    }

    @Test
    void setMinimumSeverity_levelNull_throwsException() {
        assertThatThrownBy(() -> log.setMinimumSeverity(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("minimum level is required");
    }

    @Test
    void disable_turnsOffLogging() {
        log.disable();

        log.info("not logged");

        verify(loggingAdapter, never()).log(any(), any());
    }

    @Test
    void enable_turnsOnLogging() {
        log.disable();
        log.enable();

        log.info("logged");

        verify(loggingAdapter).log(LogLevel.INFO, "logged");
    }

    @Test
    void isActive_returnsCurrentActiveStatus() {
        assertThat(log.isActive()).isTrue();
        log.disable();
        assertThat(log.isActive()).isFalse();
    }
}
