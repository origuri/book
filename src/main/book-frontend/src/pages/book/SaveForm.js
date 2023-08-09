import React, { useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const SaveForm = () => {
  // history가 없어지고 useNavigate 사용
  const navigate = useNavigate();

  /*
   * 오브젝트를 초기화
   * */
  const [book, setBook] = useState({
    title: '',
    author: '',
  });

  const changeValue = (e) => {
    /*
     * setBook에 깊은 복사를 실행하는 이유는
     * 처음 책 제목을 넣고 다시 changeValue가 실행되면
     * book은 초기화 되기 때문에 깊은 복사를 사용하여 초기화를 막는 것.
     * 즉 깊은 복사를 하지 않는다면
     * title, author 순으로 적었다면 마지막에 적은 author만 넘어감.
     * */
    setBook({
      ...book,
      // name = title or author
      [e.target.name]: e.target.value,
    });
  };

  const submitBook = (e) => {
    e.preventDefault(); // submit 작동안 함.
    const title = e.target.title.value.trim();
    const author = e.target.author.value.trim();
    console.log(title, author);
    if (title === '' || author === '') {
      alert('title 또는 author의 값을 넣어주세요');
      e.target.title.focus();
      return false;
    }

    fetch('http://localhost:8081/book', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json; charset=utf-8', // 필수 없으면 에러남
      },
      body: JSON.stringify(book), // 오브젝트를 json 데이터로 변경 해줌.
    })
      /*
       * 응답이 왔을 때 res안에 status가 201이면
       * 이 데이터를 json 형태로 변경해서 리턴해라
       * 아니면 null값을 줘라
       * */
      .then((res) => {
        console.log(1, res);
        if (res.status === 201) {
          return res.json();
        } else {
          return null;
        }
      })
      /*
       * json 형태로 리턴 받은 응답이
       * null이 아니면 콘솔에 뿌려라
       * */
      .then((res) => {
        if (res !== null) {
          navigate('/');
        } else {
          alert('등록실패');
        }
      });
  };

  return (
    <Form onSubmit={submitBook}>
      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>Title</Form.Label>
        <Form.Control
          type="text"
          placeholder="Enter title"
          onChange={changeValue}
          name={'title'}
        />
        <Form.Text className="text-muted">책 제목 넣어라</Form.Text>
      </Form.Group>

      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>Author</Form.Label>
        <Form.Control
          type="text"
          placeholder="Enter author"
          onChange={changeValue}
          name={'author'}
        />
        <Form.Text className="text-muted">저자 넣어라</Form.Text>
      </Form.Group>

      <Button variant="primary" type="submit">
        Submit
      </Button>
    </Form>
  );
};

export default SaveForm;
