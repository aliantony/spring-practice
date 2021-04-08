package com.an.annotation;

import org.springframework.core.annotation.AnnotationUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Playground {
  public static void main(String[] args) {
    try {
      Method method = TestParent.class.getMethod("test", new Class[]{HttpServletRequest.class});
      Annotation staticTextAnnot = AnnotationUtils.findAnnotation(method, StaticTextAnnotation.class);
      System.out.println("@StaticTextAnnotation of method is: "+staticTextAnnot);
      System.out.println("@StaticTextAnnotation method value: "+AnnotationUtils.getValue(staticTextAnnot, "text"));
      System.out.println("@StaticTextAnnotation method default value: "+AnnotationUtils.getDefaultValue(staticTextAnnot, "text"));
      System.out.println("@StaticTextAnnotation value: "+AnnotationUtils.getValue(staticTextAnnot));

      
      Annotation classNameAnnotation = AnnotationUtils.findAnnotation(TestChildren.class, ClassNameAnnotation.class);
      System.out.println("@ClassNameAnnotation of TestChildren.class is: "+classNameAnnotation);
      System.out.println("@ClassNameAnnotation method value: "+AnnotationUtils.getValue(classNameAnnotation, "className"));
      System.out.println("@ClassNameAnnotation method default value: "+AnnotationUtils.getDefaultValue(classNameAnnotation, "className"));
      System.out.println("@ClassNameAnnotation value: "+AnnotationUtils.getValue(classNameAnnotation));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}