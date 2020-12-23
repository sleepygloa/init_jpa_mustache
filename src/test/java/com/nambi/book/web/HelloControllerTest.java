package com.nambi.book.web;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

//@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = HelloController.class,
//excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
//})
public class HelloControllerTest {

//    @Autowired
//    private MockMvc mvc;
//
//    @WithMockUser(roles="USER")
//    @Test
//    public void hello가_리턴된다() throws Exception{
//        String hello = "hello";
//
//        mvc.perform(get("/hello"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(hello));
//    }
//
//    @WithMockUser(roles="USER")
//    @Test
//    public void helloDto가_리턴된다() throws Exception{
//        String name = "hello";
//        int amount = 1000;
//
//        mvc.perform(get("/hello/dto")
//                        .param("name", name)
//                        .param("amount", String.valueOf(amount)))
//                    .andExpect(status().isOk())
//                    .andExpect( jsonPath("$.name", is(name)))
//                    .andExpect( jsonPath("$.amount", is(amount)))
//                    ;
//
//    }
}
