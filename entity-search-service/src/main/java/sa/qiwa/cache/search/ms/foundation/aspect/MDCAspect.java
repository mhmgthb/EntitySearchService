package sa.qiwa.cache.search.ms.foundation.aspect;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.reactivestreams.Publisher;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;
import sa.qiwa.cache.search.ms.foundation.common.CommonConstants;
import sa.qiwa.cache.search.ms.foundation.enums.ErrorCodes;
import sa.qiwa.cache.search.ms.foundation.exception.ServiceException;

@Aspect
@Component
@Slf4j
public class MDCAspect {

    private final String EXCEPTION_MSG = "exception while setting MDC in aspect";

   /* @Before("execution(* sa.qiwa.cache.search.ms.application.delegator.*.*(..))")
    public void beforeSearch() {
         Mono.deferContextual(contextView ->{
                    try (MDC.MDCCloseable mdcCloseable = MDC.putCloseable(CommonConstants.MDC_KEY,
            contextView.get(CommonConstants.CONTEXT_KEY))){

        } catch (Throwable e) {
        throw new ServiceException("Exception while setting MDC in aspect", ErrorCodes.UNKNOWN);
        }

        });

    }*/
    //@Around("execution(* sa.qiwa.cache.search.ms.application.delegator.*.*(..))")
    @Around("exceptionHandlerOperations() || serviceOperations()")
    @SneakyThrows()
    public Publisher<?> aroundEndpointExecution(ProceedingJoinPoint joinPoint) {
        //log.info("setting value from context into MDC for logging before controller endpoint execution");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Class<?> returnType = methodSignature.getReturnType();
        /*return Mono.deferContextual(contextView -> {
            try (MDC.MDCCloseable mdcCloseable = MDC.putCloseable(CommonConstants.MDC_KEY, contextView.get(CommonConstants.CONTEXT_KEY))) {
                return (Mono<?>) joinPoint.proceed();
            } catch (Throwable e) {
                // throw new CustomRuntimeException(e, EXCEPTION_MSG);
                //log.warn("exception while setting MDC in aspect",e);
                throw new ServiceException("exception while setting MDC in aspect", ErrorCodes.UNKNOWN);
            }
            return (Publisher<?>) joinPoint.proceed();
        });*/
        if(Mono.class.isAssignableFrom(returnType)) {
            return handleMono(joinPoint, methodSignature);
        }else {
            return (Publisher<?>) joinPoint.proceed();
        }
    }
    private Mono<Object> handleMono(ProceedingJoinPoint joinPoint, MethodSignature methodSignature) {
        return Mono.deferContextual(contextView -> {
            try (MDC.MDCCloseable mdcCloseable = MDC.putCloseable(CommonConstants.MDC_KEY, contextView.get(CommonConstants.CONTEXT_KEY))) {
                log.info(" {}.{}() with arguments = {}",
                        joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                        joinPoint.getArgs()[0]);

                return (Mono<?>) joinPoint.proceed();
            } catch (Throwable e) {
                throw new ServiceException("Exception while setting MDC in aspect", ErrorCodes.UNKNOWN);
            }
        });
    }
    @Pointcut("execution(* sa.qiwa.cache.search.ms.domain.service.EntitySearchService.search*(..))")
    public void serviceOperations(){}
    @Pointcut("execution(* sa.qiwa.cache.search.ms.infrastructure.repository.EntitySearchRepository.search*(..))")
    public void repositoryOperations(){}
    @Pointcut("execution(* sa.qiwa.cache.search.ms.foundation.exception.*.handle*(..))")
    public void exceptionHandlerOperations(){}
    @Pointcut("execution(* reactor.core.publisher.*.doFirst(..))")
    public void reactorLogging(){}
}
