package com.nambi.book.springboot.web;

import com.nambi.book.springboot.config.auth.LoginUser;
import com.nambi.book.springboot.config.auth.dto.SessionUser;
import com.nambi.book.springboot.service.system.MenuService;
import com.nambi.book.springboot.web.dto.post.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
