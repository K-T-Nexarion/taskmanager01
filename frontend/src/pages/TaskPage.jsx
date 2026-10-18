import TaskForm from '../components/TaskForm';
import TaskList from '../components/TaskList';
import UpgradeForm from '../components/UpgradeForm';

function TaskPage({
  currentUser,
  logout,
  title,
  description,
  setTitle,
  setDescription,
  createTask,
  tasks,
  toggleTask,
  deleteTask,
  message,
  upgradeEmail,
  upgradePassword,
  setUpgradeEmail,
  setUpgradePassword,
  upgradeGuest
}) {
  return (
    <div>
      <h1>タスク管理</h1>

      {currentUser.guest ? (
        <p>ゲストとして利用中</p>
      ) : (
        <p>ログイン中：{currentUser.email}</p>
      )}

      <button onClick={logout}>
        ログアウト
      </button>

      <p>{message}</p>

      {currentUser.guest && (
        <UpgradeForm
          upgradeEmail={upgradeEmail}
          upgradePassword={upgradePassword}
          setUpgradeEmail={setUpgradeEmail}
          setUpgradePassword={setUpgradePassword}
          upgradeGuest={upgradeGuest}
        />
      )}

      <TaskForm
        title={title}
        description={description}
        setTitle={setTitle}
        setDescription={setDescription}
        createTask={createTask}
      />

      <TaskList
        tasks={tasks}
        toggleTask={toggleTask}
        deleteTask={deleteTask}
      />
    </div>
  );
}

export default TaskPage;