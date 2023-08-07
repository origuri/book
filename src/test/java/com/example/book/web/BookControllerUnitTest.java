package com.example.book.web;

// 단위 테스트(Controller 관련 로직만 띄우기) Filter, ControllerAdvice 등

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

/*
* @ExtendWith(SpringExtension.class)
* 이 어노테이션은 spring 환경으로 테스트를 확장시켜주는 어노테이션이라 필수!
* 근데 @WebMvcTest 안에 들어있음으로 생략 가능
*
* @AutoConfigureMockMvc 이게 있어야
* @Autowired private MockMvc mockMvc; di 가능
* 근데 @WebMvcTest 안에 들어있음으로 생략 가능
* */
@WebMvcTest // Controller, Filter, ControllerAdvice 같은 어노테이션만 띄워줌.
public class BookControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
}
