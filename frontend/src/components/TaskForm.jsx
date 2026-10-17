function TaskForm({
  title,
  description,
  setTitle,
  setDescription,
  createTask
}) {
  return (
    <div>
      <h2>タスク追加</h2>

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
  );
}

export default TaskForm;