package com.orxan.sweetstorerest.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class ErrorHandlingAOP {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(com.orxan.sweetstorerest..*)")
    public void springBeanPointCut(){}

    @AfterThrowing(pointcut = "springBeanPointCut()",throwing = "e")
    public void logAfterThrowing(Exception e) {
        logger.error(e.getMessage());
    }
}
