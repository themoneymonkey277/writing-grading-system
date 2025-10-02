package com.example.writing_grading_system.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
  private final String resourceName;
  private final String fieldName;
  private final Long fieldValue;

  public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue) {
    super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue)); //Post not found with id : '123'
    this.resourceName = resourceName;
    this.fieldValue = fieldValue;
    this.fieldName = fieldName;
  }

  public String getResourceName() {
    return resourceName;
  }

  public String getFieldName() {
    return fieldName;
  }

  public Long getFieldValue() {
    return fieldValue;
  }
}
