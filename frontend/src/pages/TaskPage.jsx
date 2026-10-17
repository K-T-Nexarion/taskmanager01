import TaskForm from '../components/TaskForm';
import TaskList from '../components/TaskList';

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
  message
}) {
  return (
    <div>
      <h1>タスク管理</h1>

      <p>ログイン中：{currentUser.email}</p>

      <button onClick={logout}>
        ログアウト
      </button>

      <p>{message}</p>

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