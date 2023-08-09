import React, { useEffect, useState } from 'react';
import BookItem from '../../components/BookItem';

const Home = () => {
  // 처음에 책이 얼마나 있는지 모르니까 빈 배열로 선언한다.
  const [books, setBooks] = useState([]);

  // 함수 실행시 최초 한번 실행되는 것.
  useEffect(() => {
    /*
     * ajax 비동기 통신
     * => 페이지를 그리는 동안에도 다운을 받을 수 있게 하기 위해
     * fetch => url을 요청
     * 첫번째 then => 데이터(res)를 json타입으로 바꿔주.
     * 두번째 then => json타입으로 바꾼 데이터를 뿌려줌.
     * */
    fetch('http://localhost:8081/books')
      .then((res) => res.json())
      .then((res) => {
        console.log('잘나오나 봅시다 => ', res);
        setBooks(res);
      });
  }, []); // 여기에 book을 넣으면 무한반복이 됨.

  return (
    <div>
      {/* books의 길이만큼 돌려라
        react는 변경 감지를 배열의 key값으로 하는데
        그 key값을 id 값으로 한다.
      */}
      {books.map((book) => (
        <BookItem key={book.id} book={book} />
      ))}
    </div>
  );
};

export default Home;
