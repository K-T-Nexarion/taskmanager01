function UpgradeForm({
  upgradeEmail,
  upgradePassword,
  setUpgradeEmail,
  setUpgradePassword,
  upgradeGuest
}) {
  return (
    <div>
      <h2>アカウント登録</h2>

      <p>
        アカウントを登録すると、作成したタスクをそのまま引き継げます。
      </p>

      <input
        type="email"
        placeholder="メールアドレス"
        value={upgradeEmail}
        onChange={e => setUpgradeEmail(e.target.value)}
      />

      <input
        type="password"
        placeholder="パスワード"
        value={upgradePassword}
        onChange={e => setUpgradePassword(e.target.value)}
      />

      <button onClick={upgradeGuest}>
        アカウント登録
      </button>
    </div>
  );
}

export default UpgradeForm;