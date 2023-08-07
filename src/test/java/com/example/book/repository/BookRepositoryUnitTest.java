package com.example.book.repository;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
/*
 * 단위 테스트를 할 때에 db와 관련된 것만 띄워주면 됨
 * @DataJpaTest => 레파지토리,하이버네이트를 띄워주는 어노테이션
 *
 * @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
 * => 가짜 db로 테스트한다는 뜻.
 * @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
 * => 진짜 db로 테스트한다는 뜻 => 통합 테스트 때 사용.
 * */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class BookRepositoryUnitTest {

    // @DataJpaTest가 레파지토리를 띄워주었기 때문에 @Mock으로 할 필요 없음.
    @Autowired
    private BookRepository bookRepository;
}
