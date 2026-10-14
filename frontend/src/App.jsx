import { useEffect, useState } from 'react';

function App() {
  const [tasks, setTasks] = useState([]);
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');

  const fetchTasks = () => {
    fetch('http://localhost:8080/tasks')
      .then(response => response.json())
      .then(data => setTasks(data));
  };

  useEffect(() => {
    fetchTasks();
  }, []);

  // タスク追加
  const createTask = async () => {
    const response = await fetch('http://localhost:8080/tasks', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        title,
        description,
        completed: false,
      }),
    });

    if (response.ok) {
      setTitle('');
      setDescription('');
      fetchTasks();
    }
  };

  // 完了・未完了を切り替える
  const toggleTask = async (task) => {
    const response = await fetch(
      `http://localhost:8080/tasks/${task.id}`,
      {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          title: task.title,
          description: task.description,
          completed: !task.completed,
        }),
      }
    );

    if (response.ok) {
      fetchTasks();
    }
  };

  // タスク削除
  const deleteTask = async (id) => {
    const response = await fetch(
      `http://localhost:8080/tasks/${id}`,
      {
        method: 'DELETE',
      }
    );

    if (response.ok) {
      fetchTasks();
    }
  };

  return (
    <div>
      <h1>タスク管理</h1>

      <div>
        <input
          type="text"
          placeholder="タイトル"
          value={title}
          onChange={e => setTitle(e.target.value)}
        />

        <input
          type="text"
          placeholder="説明"
          value={description}
          onChange={e => setDescription(e.target.value)}
        />

        <button onClick={createTask}>
          追加
        </button>
      </div>

      <h2>タスク一覧</h2>

      {tasks.map(task => (
        <div key={task.id}>
          <p>
            {task.completed ? '✅' : '⬜'}
            {' '}
            {task.title}
          </p>

          <p>{task.description}</p>

          <button onClick={() => toggleTask(task)}>
            {task.completed ? '未完了に戻す' : '完了'}
          </button>

          <button onClick={() => deleteTask(task.id)}>
            削除
          </button>
        </div>
      ))}
    </div>
  );
}

export default App;