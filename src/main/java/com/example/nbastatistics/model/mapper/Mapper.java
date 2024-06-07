package com.example.nbastatistics.model.mapper;

public interface Mapper<T, U> {
    T map(T target, U source);
}
