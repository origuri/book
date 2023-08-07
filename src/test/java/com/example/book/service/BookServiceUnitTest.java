package com.example.book.service;



import com.example.book.repository.BookRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/*
 * 서비스는 기능적인 로직을 검증하는 곳.
 * bookRepository를 db랑도 통신하기 때문에 di하는 순간 단위테스트가 아닌 통합테스트가 됨.
 * 근데 이게 없으면 테스트가 성공했는지 확인이 안됌.
 * 그래서 BookRepository를 가짜 버전으로 띄워 주는 방법이 있음.
 *
 * @ExtendWith(MockitoExtension.class)
 * Spring IoC에 띄워주는게 아니라 Mock IoC에 띄워주는 것.
 *
 * */
@ExtendWith(MockitoExtension.class)
public class BookServiceUnitTest {

    /*
    * @InjectMocks란 BookService 객체가 Mock에 만들어질 때
    * @Mock으로 등록된 모든 애들을 di 해줌.
    *
    * 즉 BookService가 뜨면 @Mock private BookRepository bookRepository;를 사용할 수 있게 됨.
    * */
    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;
}
