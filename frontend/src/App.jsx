import { useEffect, useState } from 'react';
import { Navigate, Route, Routes } from 'react-router';

import LoginPage from './pages/LoginPage';
import TaskPage from './pages/TaskPage';

const API_URL =
  import.meta.env.VITE_API_URL || 'http://localhost:8080';

function App() {
  const [tasks, setTasks] = useState([]);

  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const [upgradeEmail, setUpgradeEmail] = useState('');
  const [upgradePassword, setUpgradePassword] = useState('');

  const [currentUser, setCurrentUser] = useState(null);
  const [message, setMessage] = useState('');

  const [checkingLogin, setCheckingLogin] = useState(true);

  const fetchTasks = async () => {
    try {
      const response = await fetch(`${API_URL}/tasks`, {
        credentials: 'include',
      });

      if (!response.ok) {
        setTasks([]);
        setMessage('タスクの取得に失敗しました');
        return;
      }

      const data = await response.json();

      if (Array.isArray(data)) {
        setTasks(data);
      }
    } catch (error) {
      console.error(error);
      setTasks([]);
      setMessage('サーバーに接続できません');
    }
  };

  const checkLogin = async () => {
    try {
      const response = await fetch(`${API_URL}/auth/me`, {
        credentials: 'include',
      });

      if (response.ok) {
        const data = await response.json();

        setCurrentUser(data);
        await fetchTasks();
      } else {
        setCurrentUser(null);
        setTasks([]);
      }
    } catch (error) {
      console.error(error);

      setCurrentUser(null);
      setTasks([]);
      setMessage('サーバーに接続できません');
    } finally {
      setCheckingLogin(false);
    }
  };

  useEffect(() => {
    checkLogin();
  }, []);

  const register = async () => {
    try {
      const response = await fetch(
        `${API_URL}/auth/register`,
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          credentials: 'include',
          body: JSON.stringify({
            email,
            password,
          }),
        }
      );

      if (response.ok) {
        setMessage('ユーザー登録に成功しました');
      } else {
        setMessage('ユーザー登録に失敗しました');
      }
    } catch (error) {
      console.error(error);
      setMessage('サーバーに接続できません');
    }
  };

  const login = async () => {
    try {
      const body = new URLSearchParams();

      body.append('email', email);
      body.append('password', password);

      const response = await fetch(
        `${API_URL}/auth/login`,
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
          },
          credentials: 'include',
          body,
        }
      );

      if (response.ok) {
        setMessage('ログインしました');
        await checkLogin();
      } else {
        setMessage(
          'メールアドレスまたはパスワードが違います'
        );
      }
    } catch (error) {
      console.error(error);
      setMessage('サーバーに接続できません');
    }
  };

  const guestLogin = async () => {
    try {
      const response = await fetch(
        `${API_URL}/auth/guest`,
        {
          method: 'POST',
          credentials: 'include',
        }
      );

      if (response.ok) {
        setMessage('ゲストとして開始しました');
        await checkLogin();
      } else {
        setMessage('ゲストログインに失敗しました');
      }
    } catch (error) {
      console.error(error);
      setMessage('サーバーに接続できません');
    }
  };

  const upgradeGuest = async () => {
    try {
      const response = await fetch(
        `${API_URL}/auth/upgrade`,
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          credentials: 'include',
          body: JSON.stringify({
            email: upgradeEmail,
            password: upgradePassword,
          }),
        }
      );

      if (response.ok) {
        setUpgradeEmail('');
        setUpgradePassword('');

        setMessage('アカウント登録が完了しました');

        await checkLogin();
      } else {
        setMessage('アカウント登録に失敗しました');
      }
    } catch (error) {
      console.error(error);
      setMessage('サーバーに接続できません');
    }
  };

  const logout = async () => {
    try {
      const response = await fetch(
        `${API_URL}/auth/logout`,
        {
          method: 'POST',
          credentials: 'include',
        }
      );

      if (response.ok) {
        setCurrentUser(null);
        setTasks([]);

        setEmail('');
        setPassword('');

        setUpgradeEmail('');
        setUpgradePassword('');

        setMessage('ログアウトしました');
      } else {
        setMessage('ログアウトに失敗しました');
      }
    } catch (error) {
      console.error(error);
      setMessage('サーバーに接続できません');
    }
  };

  const createTask = async () => {
    try {
      const response = await fetch(
        `${API_URL}/tasks`,
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          credentials: 'include',
          body: JSON.stringify({
            title,
            description,
            completed: false,
          }),
        }
      );

      if (response.ok) {
        setTitle('');
        setDescription('');

        setMessage('タスクを追加しました');

        await fetchTasks();
      } else {
        setMessage('タスクの追加に失敗しました');
      }
    } catch (error) {
      console.error(error);
      setMessage('サーバーに接続できません');
    }
  };

  const toggleTask = async (task) => {
    try {
      const response = await fetch(
        `${API_URL}/tasks/${task.id}`,
        {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          credentials: 'include',
          body: JSON.stringify({
            title: task.title,
            description: task.description,
            completed: !task.completed,
          }),
        }
      );

      if (response.ok) {
        setMessage('タスクを更新しました');

        await fetchTasks();
      } else {
        setMessage('タスクの更新に失敗しました');
      }
    } catch (error) {
      console.error(error);
      setMessage('サーバーに接続できません');
    }
  };

  const deleteTask = async (id) => {
    try {
      const response = await fetch(
        `${API_URL}/tasks/${id}`,
        {
          method: 'DELETE',
          credentials: 'include',
        }
      );

      if (response.ok) {
        setMessage('タスクを削除しました');

        await fetchTasks();
      } else {
        setMessage('タスクの削除に失敗しました');
      }
    } catch (error) {
      console.error(error);
      setMessage('サーバーに接続できません');
    }
  };

  if (checkingLogin) {
    return <p>ログイン状態を確認中...</p>;
  }

  return (
    <Routes>
      <Route
        path="/"
        element={
          <Navigate
            to={currentUser ? '/tasks' : '/login'}
            replace
          />
        }
      />

      <Route
        path="/login"
        element={
          currentUser ? (
            <Navigate to="/tasks" replace />
          ) : (
            <LoginPage
              email={email}
              password={password}
              setEmail={setEmail}
              setPassword={setPassword}
              register={register}
              login={login}
              guestLogin={guestLogin}
              message={message}
            />
          )
        }
      />

      <Route
        path="/tasks"
        element={
          !currentUser ? (
            <Navigate to="/login" replace />
          ) : (
            <TaskPage
              currentUser={currentUser}
              logout={logout}
              title={title}
              description={description}
              setTitle={setTitle}
              setDescription={setDescription}
              createTask={createTask}
              tasks={tasks}
              toggleTask={toggleTask}
              deleteTask={deleteTask}
              message={message}
              upgradeEmail={upgradeEmail}
              upgradePassword={upgradePassword}
              setUpgradeEmail={setUpgradeEmail}
              setUpgradePassword={setUpgradePassword}
              upgradeGuest={upgradeGuest}
            />
          )
        }
      />
    </Routes>
  );
}

export default App;