package cn.xjk.shiro.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xjk
 * @date 2019/2/12 -  11:33
 **/
@Controller
public class LoginController {

    /**
     * 用户登陆的入口
     *
     * @param userName
     * @param password
     * @param rememberMe
     * @return
     */
    @RequestMapping("/login")
    public String login(
            @RequestParam(value = "username", required = false) String userName,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "remember", required = false) String rememberMe
    ) {
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName,password);
            if (rememberMe != null) {
                usernamePasswordToken.setRememberMe(true);
            } else {
                usernamePasswordToken.setRememberMe(false);
            }
            //登录，即身份校验，由通过Spring注入的UserRealm会自动校验输入的用户名和密码在数据库中是否有对应的值
            subject.login(usernamePasswordToken);
            System.out.println("用户是否登录：" + subject.isAuthenticated());
            return "redirect:index.jsp";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("exceptionTest")
    public String exceptionTest() {
        try{
            if (true) {
                throw new ExcessiveAttemptsException();
            }
        } catch (ExcessiveAttemptsException e) {

        }
        return "redirect:index.jsp";
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
}
