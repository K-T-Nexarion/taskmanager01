function LoginForm({
  email,
  password,
  setEmail,
  setPassword,
  register,
  login,
  guestLogin,
  message
}) {
  return (
    <div>
      <h2>アカウント</h2>

      <input
        type="email"
        placeholder="メールアドレス"
        value={email}
        onChange={e => setEmail(e.target.value)}
      />

      <input
        type="password"
        placeholder="パスワード"
        value={password}
        onChange={e => setPassword(e.target.value)}
      />

      <button onClick={register}>
        新規登録
      </button>

      <button onClick={login}>
        ログイン
      </button>

      <button onClick={guestLogin}>
        ゲストで始める
      </button>

      <p>{message}</p>
    </div>
  );
}

export default LoginForm;