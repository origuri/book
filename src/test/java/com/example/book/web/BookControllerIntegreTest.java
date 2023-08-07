package com.example.book.web;
// 통합 테스트(모든 bean들을 똑같이 IoC 롤리고 테스트하는 것.)

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class BookControllerIntegreTest {

    @Autowired
    private MockMvc mockMvc;
}
