package com.example.book.web;

// 단위 테스트(Controller 관련 로직만 띄우기) Filter, ControllerAdvice 등

import com.example.book.entity.Book;
import com.example.book.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/*
* @ExtendWith(SpringExtension.class)
* 이 어노테이션은 spring 환경으로 테스트를 확장시켜주는 어노테이션이라 필수!
* 근데 @WebMvcTest 안에 들어있음으로 생략 가능
*
* @AutoConfigureMockMvc 이게 있어야
* @Autowired private MockMvc mockMvc; di 가능
* 근데 @WebMvcTest 안에 들어있음으로 생략 가능
* */
@WebMvcTest() // Controller, Filter, ControllerAdvice 같은 어노테이션만 띄워줌.
@Slf4j
public class BookControllerUnitTest {

    /*
    * 주소 호출을 해주는 역할을 한다.
    * */
    @Autowired
    private MockMvc mockMvc;

    /*
    * @WebMvcTest로 인해 controller는 이미 떠있음.
    * 근데 BookService가 떠있지 않으니까 에러가 발생함.
    * @InjectMocks으로 BookController를 만들고 @Mock BookService로 넣어줘도 되지만
    * 이미 controller가 떠 있는 상태라서 굳이 할 필요 없음
    * 이때 사용하는 것이 @MockBean 어노테이션
    * spring환경에서 IoC 환경에 bean으로 등록 됨.
    * 물론 가짜 BookService
    * */
    @MockBean
    private BookService bookService;

    @Test
    public void save_테스트() throws Exception {
        /*
        * given : 테스트를 위한 준비
        * 저장할 데이터를 오브젝트로 만들었고,
        * 오브젝트를 json으로 바꿔주는 메소드를 사용해서 변환했고,
        * 저장하면 이러한 결과를 받아와야 한다고 지정함.
        * */
        Book book = new Book(null,"junit 테스트 해보자","임오리");
        String content = new ObjectMapper().writeValueAsString(book);
        log.info("json content -> {}",content);
        // 내가 저장하기를 하면 이런 결과가 나와야 해
        when(bookService.저장하기(book)).thenReturn(new Book(1L, "junit 테스트 해보자", "임오리"));

        /*
        * when (테스트 실행)
        * http 메소드를 post로 받고 보낼 타입은 json, 보낼 오브젝트는 content, 받는 스타일은 json으로 설정
        * */
        ResultActions resultActions = mockMvc.perform(
                post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON)
        );
        /*
        * then (검증)
        * 여기서 통과하면 테스트 통과임.
        *
        * 내가 기대하는 값(andExpect)
        * .andExpect(status().isCreated())
        * => 성공하면 201응답을 기대한다
        * .andExpect(jsonPath("$.title").value("junit 테스트 해보자"))
        * => 응답한 json($)의 title의 value가 "junit 테스트 해보자" 일 것이다.
        *
        * 보여주는 값(andDo)
        * andDo(MockMvcResultHandlers.print());
        * =>
        * */
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("junit 테스트 해보자"))
                .andDo(MockMvcResultHandlers.print());
    }

    /*
    * 트렌젝션이 끝나고 전부 rollBack
    * */
    @Test
    public void findAll_테스트() throws Exception{

        /*
        * given : 테스트를 위한 준비
        * 모두 찾아오는 건데 트렌젝션이 끝나면 전부 리셋됨
        * 가져올 데이터가 없다는 뜻
        * => 우선 내가 더미 데이터를 넣어놔야 함.
        * */
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "스프링", "오리"));
        books.add(new Book(2L, "리액트", "구리"));

        when(bookService.모두가져오기()).thenReturn(books);

        /*
        * when : 메소드 실행
        * get 메소드는 accept만 적으면 됨.
        * */
        ResultActions resultActions = mockMvc.perform(
                get("/book")
                        .accept(MediaType.APPLICATION_JSON)
        );

        /*
        * then : 결과
        * */
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[0].title").value("스프링"))
                .andDo(MockMvcResultHandlers.print());


    }
 }
