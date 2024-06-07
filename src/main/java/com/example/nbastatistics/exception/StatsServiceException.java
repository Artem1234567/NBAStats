package com.example.nbastatistics.exception;

public record StatsServiceException(int statusCode, String message) {
}
