package com.pk10.control;

import com.pk10.bean.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ron on 16-9-7.
 */
@Controller
public class AdminHomeControl {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoControl.class);
    @RequestMapping("toAdminHome")
    public String toAdminHome(HttpServletRequest request)
    {
        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userinfo");
        if (userInfo != null) {
            if(userInfo.getIsagent() == 3){
                return "admin/admin-index";
            }else{
                return "admin/admin-agent";
            }
        } else {
            return "redirect:adminlogin.html";
        }
    }

}