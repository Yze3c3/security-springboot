package com.lsl.security.login;


import com.lsl.security.model.UserDetail;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
         @RequestMapping(value = "/login-success",produces = "text/plain;charset=utf-8")
         public String loginSuccess(){
             return "登录成功,"+getUserName();
         }
    /**
     * 测试资源1
     * @return
     */
    @GetMapping(value = "/r/r1",produces = {"text/plain;charset=UTF-8"})
    @PreAuthorize("hasAnyAuthority('p1')")
    public String r1(){
        return " 访问资源1";
    }

    /**
     * 测试资源2
     * @return
     */
    @GetMapping(value = "/r/r2",produces = {"text/plain;charset=UTF-8"})
    @PreAuthorize("hasAuthority('p1') and hasAuthority('p2')")
    public String r2(){
        return " 访问资源2";
    }


    public String getUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String username=null;
        if(principal instanceof org.springframework.security.core.userdetails.UserDetails){
            UserDetails userDetail = (UserDetails) principal;
            username=userDetail.getUsername();
        }else {
            username=principal.toString();
        }
        return username;
    }
}
