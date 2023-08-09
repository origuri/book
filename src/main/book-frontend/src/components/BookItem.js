import React from 'react';
import { Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const BookItem = (props) => {
  const { id, title, author } = props.book;
  console.log('sdafsdafdklfjkldsfjdfjsd', props);
  return (
    <Card>
      <Card.Body>
        <Card.Title>{title}</Card.Title>
        <Card.Text>저자 : {author}</Card.Text>
        {/*link to로 태그를 달고 디자인만 부트스트랩의 도움을 받는다.*/}
        <Link to={'/post/' + id} className={'btn btn-primary'}>
          상세보기
        </Link>
      </Card.Body>
    </Card>
  );
};

export default BookItem;
