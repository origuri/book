package com.example.book.web;
// 통합 테스트(모든 bean들을 똑같이 IoC 롤리고 테스트하는 것.)

import com.example.book.entity.Book;
import com.example.book.repository.BookRepository;
import com.example.book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 *  통합 테스트이므로 필요한 모든 bean을 다 띄워줌.
 *
 *  @ExtendWith(SpringExtension.class)
 * 이 어노테이션은 spring 환경으로 테스트를 확장시켜주는 어노테이션이라 필수!
 * 근데 @SpringBootTest 안에 들어있음으로 생략 가능
 *
 * @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
 * 실제 톰켓을 올리는게 아니라 다른 톰켓으로 테스트
 *
 * webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
 * 실제 톰켓으로 테스트
 *
 * @AutoConfigureMockMvc 이게 있어야
 * @Autowired private MockMvc mockMvc; di 가능
 *
 * @Transactional
 * 각각의 테스트 함수가 완료될 때마다 db를 rollBack 됨
 *
 * */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
//@Transactional
@Slf4j
public class BookControllerIntegreTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void init(){
        entityManager.createNativeQuery("ALTER TABLE book ALTER COLUMN id RESTART WITH 1").executeUpdate();
        log.info("beforeEach 실행됨.");
    }


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
        books.add(new Book(null, "스프링", "오리"));
        books.add(new Book(null, "리액트", "구리"));

        bookRepository.saveAll(books);

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

    @Test
    public void findById_테스트() throws Exception{
        // given
        List<Book> books = new ArrayList<>();
        books.add(new Book(null, "스프링", "오리"));
        books.add(new Book(null, "리액트", "구리"));

        bookRepository.saveAll(books);
        Long id = 2L;


        // when
        ResultActions resultActions = mockMvc.perform(get("/book/{id}", id)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect((jsonPath("$.title").value("리액트")))
                .andDo(MockMvcResultHandlers.print());

    }


    @Test
    public void update_테스트() throws Exception{
        // given
        List<Book> books = new ArrayList<>();
        books.add(new Book(null, "스프링", "오리"));
        books.add(new Book(null, "리액트", "구리"));

        bookRepository.saveAll(books);

        Long id = 1L;
        Book book = new Book(null,"c++ 따라하기","임오리");
        String content = new ObjectMapper().writeValueAsString(book);
        log.info("json content -> {}",content);
        // 내가 저장하기를 하면 이런 결과가 나와야 해


        // when
        ResultActions resultActions = mockMvc.perform(
                put("/book/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect((jsonPath("$.title").value("c++ 따라하기")))
                .andDo(MockMvcResultHandlers.print());

    }
}
