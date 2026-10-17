function TaskItem({
  task,
  toggleTask,
  deleteTask
}) {
  return (
    <div>
      <p>
        {task.completed ? '✅' : '⬜'} {task.title}
      </p>

      <p>{task.description}</p>

      <button onClick={() => toggleTask(task)}>
        {task.completed ? '未完了に戻す' : '完了'}
      </button>

      <button onClick={() => deleteTask(task.id)}>
        削除
      </button>
    </div>
  );
}

export default TaskItem;