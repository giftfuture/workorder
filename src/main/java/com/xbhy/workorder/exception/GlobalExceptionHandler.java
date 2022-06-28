package com.xbhy.workorder.exception;

import com.xbhy.workorder.constant.UserConstant;
import com.xbhy.workorder.util.ResponseResult;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

@ControllerAdvice
@Order(value = Ordered.LOWEST_PRECEDENCE )
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Object badArgumentHandler(IllegalArgumentException e) {
        e.printStackTrace();
        logger.error(e.toString());
        return ResponseResult.badArgumentValue();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public Object badArgumentHandler(MethodArgumentTypeMismatchException e) {
        e.printStackTrace();
        logger.error(e.toString());
        return ResponseUtil.badArgumentValue();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public Object badArgumentHandler(MissingServletRequestParameterException e) {
        e.printStackTrace();
        logger.error(e.toString());
        return ResponseUtil.badArgumentValue();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Object badArgumentHandler(HttpMessageNotReadableException e) {
        e.printStackTrace();
        logger.error(e.toString());
        return ResponseUtil.badArgumentValue();
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public Object badArgumentHandler(ValidationException e) {
        e.printStackTrace();
        logger.error(e.toString());
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                String message = "请求参数异常！";//((PathImpl) item.getPropertyPath()).getLeafNode().getName() + item.getMessage();
                return ResponseUtil.fail(402, message);
            }
        }
        return ResponseUtil.badArgumentValue();
    }

    @ExceptionHandler(UnknownAccountException.class)
    @ResponseBody
    public Object unknownAccountHandler(UnknownAccountException e) {
        logger.error(e.toString() + ": " + e.getMessage());
        e.printStackTrace();
        return ResponseUtil.fail(UserConstant.USER_INVALID_CODE, UserConstant.USER_INVALID_ERROR);
    }

    @ExceptionHandler(InvalidAccountException.class)
    @ResponseBody
    public Object invalidAccountHandler(InvalidAccountException e) {
        logger.error(e.toString() + ": " + e.getMessage());
        e.printStackTrace();
        return ResponseUtil.fail(UserConstant.USER_INVALID_CODE, UserConstant.USER_INVALID_ERROR);
    }



    @ExceptionHandler(UnpassException.class)
    @ResponseBody
    public Object unPassExceptionHandler(UnpassException e) {
        logger.error(e.toString() + ": " + e.getMessage());
        e.printStackTrace();
        return ResponseUtil.fail(UserConstant.UNPASS_CODE, UserConstant.UNPASS_ERROR);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public Object unauthorizedHandler(UnauthorizedException e) {
        logger.error(e.toString() + ": " + e.getMessage());
        e.printStackTrace();
        return ResponseUtil.unauthz();
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object seriousHandler(Exception e) {
        logger.error(e.toString() + ": " + e.getMessage());
        e.printStackTrace();
        return ResponseUtil.serious();
    }
}
