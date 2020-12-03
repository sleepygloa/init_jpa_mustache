package com.nambi.book.springboot.web;

import com.nambi.book.springboot.config.auth.LoginUser;
import com.nambi.book.springboot.config.auth.dto.SessionUser;
import com.nambi.book.springboot.service.posts.PostsService;
import com.nambi.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

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
    * 메인화면 게시판
    *************************************************************/
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
