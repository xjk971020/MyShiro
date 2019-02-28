package cn.xjk.shiro.controller.exceptionHandler;

import cn.xjk.shiro.utils.ExceptionUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xjk
 * @date 2019/2/13 -  11:49
 **/
@ControllerAdvice
public class DefaultExceptionHandler {

    /**
     *异常信息
     */
    private String eMessage = null;

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    /**
     * 未知错误
     * @param request
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView exception(HttpServletRequest request, Exception e) {
        eMessage = "未知错误";
        LOGGER.error(ExceptionUtil.getMessage(e));
        return errorModel(request,eMessage);
    }

    /**
     * 身份认证无法通过
     * @param request
     * @return
     */
    @ExceptionHandler(value = UnauthenticatedException.class)
    public ModelAndView unauthenticatedException(HttpServletRequest request) {
        eMessage = "身份认证无法通过";
        return errorModel(request,eMessage);
    }

    /**
     * 没有权限
     * @param request
     * @return
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    public ModelAndView unauthorizedException(HttpServletRequest request) {
        eMessage = "没有权限";
        return errorModel(request,eMessage);
    }

    /**
     * 未知账户信息错误
     * @param request
     * @return
     */
    @ExceptionHandler(value = UnknownAccountException.class)
    public ModelAndView unknownAccountException(HttpServletRequest request) {
        eMessage = "账户不存在";
        return errorModel(request,eMessage);
    }

    /**
     * 用户或密码错误
     * @param request
     * @return
     */
    @ExceptionHandler(value = IncorrectCredentialsException.class)
    public ModelAndView incorrectCredentialsException(HttpServletRequest request) {
        eMessage = "用户名或者密码错误";
        return errorModel(request,eMessage);
    }

    /**
     * 用户被锁定
     * @param request
     * @return
     */
    @ExceptionHandler(value = LockedAccountException.class)
    public ModelAndView lockedAccountException(HttpServletRequest request) {
        eMessage = "用户被锁定";
        return errorModel(request,eMessage);
    }

    /**
     * 用户被禁用异常
     * @param request
     * @return
     */
    @ExceptionHandler(value = DisabledAccountException.class)
    public ModelAndView disabledAccountException(HttpServletRequest request) {
        eMessage = "用户被禁用";
        return errorModel(request,eMessage);
    }

    /**
     * 登录失败次数过多
     * @param request
     * @return
     */
    @ExceptionHandler(value = ExcessiveAttemptsException.class)
    public ModelAndView excessiveAttemptsException(HttpServletRequest request) {
        eMessage = "登录失败次数过多";
        return errorModel(request, eMessage);
    }

    public ModelAndView errorModel(HttpServletRequest request, String e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception",e);
        mav.addObject("url",request.getRequestURL());
        mav.setViewName("error");
        return mav;
    }
}
