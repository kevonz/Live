package com.springapp.mvc;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fivestars.interfaces.bbs.client.Client;
import com.fivestars.interfaces.bbs.util.XMLHelper;



@Controller
@RequestMapping("/")
public class HelloController {
	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("message", "请登录");
		return "hello";
	}

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(ModelMap model, HttpServletRequest request,
                        HttpServletResponse response, User userinfo) {
        if (userinfo.getUsername().equals("admin")
                && userinfo.getPassword().equals("admin")) {
            request.setAttribute("userinfo", userinfo);
            String ucsynLogin = login(userinfo.getUsername(),userinfo.getPassword());
            request.setAttribute("ucsynlogin", ucsynLogin);
            return "success";
        } else {
            model.addAttribute("message", "用户名密码不正确");
            return "hello";
        }
    }

    public static String login(String userName,String pwd){
        Client e = new Client();
        String result = e.uc_user_login(userName, pwd);
        String $ucsynlogin = "";

        LinkedList<String> rs = XMLHelper.uc_unserialize(result);
        if(rs.size()>0){
            int $uid = Integer.parseInt(rs.get(0));
            String $username = rs.get(1);
            String $password = rs.get(2);
            String $email = rs.get(3);
            if($uid > 0) {
                $ucsynlogin = e.uc_user_synlogin($uid);
                System.out.println("登录成功" + $ucsynlogin);
            } else if($uid == -1) {
                System.out.println("用户不存在,或者被删除");
            } else if($uid == -2) {
                System.out.println("密码错");
            } else {
                System.out.println("未定义");
            }
        }else{
            System.out.println("Login failed");
            System.out.println(result);
        }
        return $ucsynlogin;
    }

    public static String logout(){
        Client uc = new Client();
        String $ucsynlogout = uc.uc_user_synlogout();
        System.out.println("退出成功"+$ucsynlogout);
        return $ucsynlogout;
    }
}