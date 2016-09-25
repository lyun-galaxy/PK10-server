package com.pk10.control;

import com.pk10.bean.GivenMoneyRecord;
import com.pk10.bean.UserInfo;
import com.pk10.service.GivenMoneyRecordService;
import com.pk10.service.UserInfoService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by dengfengdecao on 16/9/24.
 */
@SessionAttributes({"captcha", "userinfo"})
@Controller
@Scope("prototype")
public class GivenMoneyRecordController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private GivenMoneyRecordService givenMoneyRecordService;

    // 当前用户赠送金币给对方用户
    @RequestMapping(value = "given-money", method = RequestMethod.POST)
    public synchronized String givenMoney(ModelMap map, @ModelAttribute GivenMoneyRecord givenMoneyRecord, String code) throws Exception {
        if (map.get("captcha").equals(code)) {
            UserInfo currentUser = userInfoService.getUserInfoByUsername(new UserInfo(givenMoneyRecord.getCurrentUsername(), null));
            UserInfo opposingUser = userInfoService.getUserInfoByUsername(new UserInfo(givenMoneyRecord.getOpposingUsername(), null));
            // 赠送的金额大于当前用户的余额
            if (currentUser.getMoney() < givenMoneyRecord.getGivenMoney()) {
                map.addAttribute("error_response", "赠送的金额大于您当前的余额");
            } else {
                currentUser.setMoney(currentUser.getMoney() - givenMoneyRecord.getGivenMoney());
                opposingUser.setMoney(opposingUser.getMoney() + givenMoneyRecord.getGivenMoney());
                userInfoService.save(currentUser);
                userInfoService.save(opposingUser);

                givenMoneyRecord.setTime(new Date());
                givenMoneyRecordService.save(givenMoneyRecord);

                map.put("userinfo", currentUser);
                map.put("captcha", "");
                map.addAttribute("success_response", "赠送成功!");
            }
        } else {
            map.addAttribute("error_response", "验证码失效");
        }

        return "admin/response";
    }
}
