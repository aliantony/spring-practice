package com.an.annotation;

import javax.servlet.http.HttpServletRequest;

@ClassNameAnnotation(className = "TestChildren")
public class TestParent {

  @StaticTextAnnotation(value= "Custom text value", text = "Test text")
  public String test(HttpServletRequest request) {
    return "test";
  }
}