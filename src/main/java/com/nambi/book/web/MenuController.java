package com.nambi.book.web;

import com.nambi.book.config.auth.LoginUser;
import com.nambi.book.config.auth.dto.SessionUser;
import com.nambi.book.service.system.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class MenuController {
    /************************************************************
     * 메뉴 페이지로 이동
     *************************************************************/
    @GetMapping("/common/menu")
    public String menu(Model model, @LoginUser SessionUser user){
        model.addAttribute("menu", menuService.findAlldesc());
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "system/menu";
    }

    private final MenuService menuService;


}
