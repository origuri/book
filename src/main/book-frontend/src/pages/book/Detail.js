import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button } from 'react-bootstrap';

const Detail = (props) => {
  const { id } = useParams();
  console.log('detail id->', id);
  const navigate = useNavigate();

  const [book, setBook] = useState({
    /*id: '',
    title: '',
    author: '',*/
    // 있어도 되고 없어도 됨. {}로 오브젝트라고 정해주면 됨.
  });

  useEffect(() => {
    fetch('http://localhost:8081/book/' + id, {
      method: 'GET',
    })
      .then((res) => res.json())
      .then((res) => {
        setBook(res);
      });
  }, []);

  const deleteBook = () => {
    fetch('http://localhost:8081/book/' + id, {
      method: 'DELETE',
    })
      .then((res) => res.text())
      .then((res) => {
        if (res === 'ok') {
          navigate('/');
        }
      });
  };
  return (
    <div>
      <h1>상세보기</h1>
      <Button variant={'warning'}>
        <Link to={'/updateForm/' + id}>수정</Link>
      </Button>{' '}
      {/*
            상세 페이지라 삭제할 id를 넘길 필요가 없지만 만약 넘기게 된다면 이런식으로
            <Button variant={'danger'} onClick={() => deleteBook(book.id)}>
        */}
      <Button variant={'danger'} onClick={deleteBook}>
        삭제
      </Button>
      <hr />
      <h1>{book.title}</h1>
      <h1>{book.author}</h1>
    </div>
  );
};

export default Detail;
