package com.example.nbastatistics.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalServiceExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<StatsServiceException> handleException(Exception ex, Throwable cause) {
        log.error("handleException: {}", ex.getMessage());
        String errorMessage = String.format("Error: %s; Cause: %s", ex.getMessage(),
                cause != null ? cause.getMessage() : "N/A");
        return new ResponseEntity<>(
                new StatsServiceException(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<StatsServiceException> handleIllegalArgumentException(
                                        IllegalArgumentException ex, Throwable cause) {
        log.error("handleIllegalArgumentException: {}", ex.getMessage());
        String errorMessage = String.format("Error: %s; Cause: %s", ex.getMessage(),
                cause != null ? cause.getMessage() : "N/A");
        return new ResponseEntity<>(
                new StatsServiceException(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<StatsServiceException> handleMethodArgumentTypeMismatchException(
                                        MethodArgumentTypeMismatchException ex, Throwable cause) {
        log.error("handleMethodArgumentTypeMismatchException: {}", ex.getMessage());
        String errorMessage = String.format("Error: %s; Cause: %s", ex.getMessage(),
                cause != null ? cause.getMessage() : "N/A");
        return new ResponseEntity<>(
                new StatsServiceException(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FetchPlayerStatsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<StatsServiceException> handleFetchPlayerStatsException(
            FetchPlayerStatsException ex, Throwable cause) {
        log.error("handleFetchPlayerStatsException: {}", ex.getMessage());
        String errorMessage = String.format("Error: %s; Cause: %s", ex.getMessage(),
                cause != null ? cause.getMessage() : "N/A");
        return new ResponseEntity<>(
                new StatsServiceException(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FetchTeamStatsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<StatsServiceException> handleFetchTeamStatsException(
            FetchTeamStatsException ex, Throwable cause) {
        log.error("handleFetchTeamStatsException: {}", ex.getMessage());
        String errorMessage = String.format("Error: %s; Cause: %s", ex.getMessage(),
                cause != null ? cause.getMessage() : "N/A");
        return new ResponseEntity<>(
                new StatsServiceException(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SaveStatsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<StatsServiceException> handleSaveStatsException(
            SaveStatsException ex, Throwable cause) {
        log.error("handleSaveStatsException: {}", ex.getMessage());
        String errorMessage = String.format("Error: %s; Cause: %s", ex.getMessage(),
                cause != null ? cause.getMessage() : "N/A");
        return new ResponseEntity<>(
                new StatsServiceException(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
