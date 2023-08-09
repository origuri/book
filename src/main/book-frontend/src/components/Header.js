import React from 'react';
import {
  Button,
  Col,
  Container,
  Form,
  Nav,
  Navbar,
  Row,
} from 'react-bootstrap';
import { Link } from 'react-router-dom';

const Header = () => {
  return (
    <>
      <Navbar bg="dark" data-bs-theme="dark">
        <Container>
          <Link to={'/'} className={'navbar-brand'}>
            홈
          </Link>
          <Nav className="me-auto">
            <Link to={'/loginForm'} className={'nav-link'}>
              로그인
            </Link>
            <Link to={'/joinForm'} className={'nav-link'}>
              회원가입
            </Link>
            <Link to={'/saveForm'} className={'nav-link'}>
              글쓰기
            </Link>
          </Nav>
          <Form inline>
            <Row>
              <Col xs="auto">
                <Form.Control
                  type="text"
                  placeholder="Search"
                  className=" mr-sm-2"
                />
              </Col>
              <Col xs="auto">
                <Button type="submit">Submit</Button>
              </Col>
            </Row>
          </Form>
        </Container>
      </Navbar>
      <br />
    </>
  );
};

export default Header;
