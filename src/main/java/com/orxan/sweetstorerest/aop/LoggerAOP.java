package com.orxan.sweetstorerest.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Configuration
@Aspect
public class LoggerAOP {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(com.orxan.sweetstorerest..*)")
    public void springBeanPointCut(){}

    @Around("@annotation(LoggerAnnotation)")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        StringBuilder logString=new StringBuilder();
        List<String> paramNames;

        try {
            MethodSignature codeSignature = (MethodSignature) joinPoint.getSignature();
            paramNames= Arrays.asList(codeSignature.getParameterNames());
            logString.append("method name:").append(codeSignature.getName());
        } catch (Exception e) {
            logger.error("Can't record action. You must compile the code with debug mode=true");
            return joinPoint.proceed();
        }

        logString
                .append(",time:").append(new Date());
        try {

            Object[] arguments = joinPoint.getArgs();
            StringBuilder params = new StringBuilder();
            for (int index = 0; index < arguments.length; index++) {
                String paramName = paramNames.get(index);
                String paramValue = arguments[index] != null ? arguments[index].toString() : null;

                params
                        .append("{name :").append(paramName)
                        .append(",value :").append(paramValue)
                        .append(".}");
            }
            printLogAction(logString,params);
        } catch (Exception e) {}
        try {
            Object returned=joinPoint.proceed();
            StringBuilder params=new StringBuilder();
            if(returned != null){
                String returnedStr = returned.toString();
                params
                        .append("{name: ").append("RETURN")
                        .append(",value : ").append(returnedStr.substring(0, returnedStr.length() < 100 ? returnedStr.length() : 100))
                        .append(returnedStr.length() < 100 ?"":"...")
                        .append("}");
            }
            printLogAction(logString,params);

        } catch (Exception e) {
            logger.error("Exception occurred while intercepting method " + joinPoint.getSignature().getName(), e);
        }

        return joinPoint.proceed();
    }

    private void printLogAction(StringBuilder logString,StringBuilder params){
        logString.append(",params:[").append(params).append("]");
        logger.info(String.valueOf(logString));

    }

    @AfterThrowing(pointcut = "springBeanPointCut()",throwing = "e")
    public void logAfterThrowing(Exception e) {
        logger.error(e.getMessage());
    }
}
