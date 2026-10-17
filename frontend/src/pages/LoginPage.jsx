import LoginForm from '../components/LoginForm';

function LoginPage({
  email,
  password,
  setEmail,
  setPassword,
  register,
  login,
  message
}) {
  return (
    <div>
      <h1>タスク管理</h1>

      <LoginForm
        email={email}
        password={password}
        setEmail={setEmail}
        setPassword={setPassword}
        register={register}
        login={login}
        message={message}
      />
    </div>
  );
}

export default LoginPage;