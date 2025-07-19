package com.rest.webservices.spring_with_cloud.exception;

public class CategoryNotFountException extends RuntimeException {
    public CategoryNotFountException(String s) {
        super(s);
    }
}
