import TaskItem from './TaskItem';

function TaskList({
  tasks,
  toggleTask,
  deleteTask
}) {
  return (
    <div>
      <h2>タスク一覧</h2>

      {tasks.map(task => (
        <TaskItem
          key={task.id}
          task={task}
          toggleTask={toggleTask}
          deleteTask={deleteTask}
        />
      ))}
    </div>
  );
}

export default TaskList;