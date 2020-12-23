package com.nambi.book.web;

import com.nambi.book.config.auth.LoginUser;
import com.nambi.book.config.auth.dto.SessionUser;
import com.nambi.book.service.posts.PostsService;
import com.nambi.book.service.system.MenuService;
import com.nambi.book.web.dto.post.PostsResponseDto;
import com.nambi.book.web.dto.system.MenuListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAlldesc());
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    /************************************************************
     * 로그인 페이지로 이동
     *************************************************************/
    @GetMapping("/loginUser")
    public String login(){
        return "loginUser";
    }


    /************************************************************
     * 메뉴 페이지로 이동
     *************************************************************/
    @GetMapping("/menu")
    public String menu(Model model, @LoginUser SessionUser user){
//        model.addAttribute("menu", menuService.findAlldesc());

        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "menu";
    }
    @GetMapping("/menu/list")
    public @ResponseBody List<MenuListResponseDto> menuList(Model model, @LoginUser SessionUser user){
        return menuService.findAlldesc();
    }

    private final MenuService menuService;
    /************************************************************
    * 메인화면 게시판
    *************************************************************/
    @GetMapping("/posts")
    public String posts(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAlldesc());
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "posts";
    }

    @GetMapping("/posts/save")
    public String postsSave(){ return "posts-save"; }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }



    /************************************************************
     * 주소찾기 API
     * https://www.juso.go.kr/openIndexPage.do#
     *************************************************************/
    @GetMapping("/api/juso")
    public String getJuso(){ return "api/juso"; }

    @GetMapping("/api/jusoMapNaver")
    public String getJusoMapNaver(Model model){
        return "api/jusoMapNaver";
    }

    @GetMapping("/api/juso_interpark")
    public String getJuso_interpark(){ return "api/juso_interpark"; }




}
