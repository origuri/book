import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

const Detail = (props) => {
  const { id } = useParams();
  console.log('detail id->', id);

  const [book, setBook] = useState({
    id: '',
    title: '',
    author: '',
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
  return (
    <div>
      <h1>상세보기</h1>

      <hr />
      <h1>{book.title}</h1>
      <h1>{book.author}</h1>
    </div>
  );
};

export default Detail;
