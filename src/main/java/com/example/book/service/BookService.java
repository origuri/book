package com.example.book.service;

import com.example.book.entity.Book;
import com.example.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book 저장하기(Book book){
        return bookRepository.save(book);
    }

    /*
    * select에 readOnly를 걸면 jpa의 변경감지 내부기능이 비활성화 되고
    * 연산 도중 값이 update 되더라도 바뀐 값이 아닌 기존의 값을 유지해줌(정합성 유지)
    * 단 insert는 못막음. => 유령 데이터 현상.
    * */
    @Transactional(readOnly = true)
    public Book 한건가저오기(Long id){
        return bookRepository.findById(id).orElseThrow(()->new IllegalArgumentException("아이디 확인"));
    }

    @Transactional(readOnly = true)
    public List<Book> 모두가져오기(){
        return bookRepository.findAll();
    }

    public Book 수정하기(Long id, Book book){
        // 더티 체킹으로 update 하기
        Book bookEntity = bookRepository.findById(id).orElseThrow(()->new IllegalArgumentException("아이디 확인"));
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setTitle(book.getTitle());
        return bookEntity;
    }

    public String 삭제하기(Long id){
        bookRepository.deleteById(id);
        return "ok";
    }
}
