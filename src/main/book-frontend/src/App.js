import { Container } from 'react-bootstrap';
import { Route, Routes } from 'react-router-dom';
import Header from './components/Header';
import Home from './pages/book/Home';
import SaveForm from './pages/book/SaveForm';
import Detail from './pages/book/Detail';
import LoginForm from './pages/user/LoginForm';
import JoinForm from './pages/user/JoinForm';
import UpdateForm from './pages/book/UpdateForm';

function App() {
  return (
    <div>
      <Header />
      {/*
        가운데로 오게하는 boot-strap 태그
      */}
      <Container>
        <Routes>
          {/*책 리스트 페이지*/}
          <Route path={'/'} exact={true} element={<Home />} />
          {/*책 저장 폼 페이지*/}
          <Route path={'/saveForm'} exact={true} element={<SaveForm />} />
          {/*책 한권의 정보를 보는 페이지*/}
          <Route path={'/book/:id'} exact={true} element={<Detail />} />
          {/*로그인 페이지*/}
          <Route path={'/loginForm'} exact={true} element={<LoginForm />} />
          {/*회원 가입 페이지*/}
          <Route path={'/joinForm'} exact={true} element={<JoinForm />} />
          {/*수정 페이지*/}
          <Route
            path={'/updateForm/:id'}
            exact={true}
            element={<UpdateForm />}
          />
        </Routes>
      </Container>
    </div>
  );
}

export default App;
