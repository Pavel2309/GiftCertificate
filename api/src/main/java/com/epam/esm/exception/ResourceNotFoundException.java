package com.epam.esm.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Long id) {
        super("could not find the resource with id = " + id + ", error code 404" + id);
    }
}
