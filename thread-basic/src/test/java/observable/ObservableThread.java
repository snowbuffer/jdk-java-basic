package observable;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-07-29 18:09
 */
public class ObservableThread<T> extends Thread implements Observable {

    private TaskLifeCycle<T> taskLifeCycle;

    private Task<T> task;

    private Cycle cycle;

    public ObservableThread(Task<T> task) {
        this(new TaskLifeCycle.EmptyTaskLifeCycle<>(), task);
    }

    public ObservableThread(TaskLifeCycle<T> taskLifeCycle, Task<T> task) {
        this.taskLifeCycle = taskLifeCycle;
        this.task = task;
    }

    @Override
    public final void run() {
        try {
            this.update(Cycle.RUNNING, null, null);
            T result = task.call();
            this.update(Cycle.DONE, result, null);
        } catch (Exception e) {
            this.update(Cycle.ERROR, null, e);
        }
    }

    private void update(Cycle cycle, T t, Exception e) {
        if (cycle == null) {
            return;
        }
        this.cycle = cycle;
        try {
            Thread thread = Thread.currentThread();
            switch (cycle) {
                case STARTED:
                    taskLifeCycle.onStart(thread);
                    break;
                case RUNNING:
                    taskLifeCycle.onRunning(thread);
                    break;
                case DONE:
                    taskLifeCycle.onFinished(thread, t);
                    break;
                case ERROR:
                    taskLifeCycle.onError(thread, e);
                    break;
            }
        } catch (Exception ex) {
            if (cycle == Cycle.ERROR) {
                throw ex;
            }
            System.out.println(this.cycle.name() + "发生未知异常, 监听器忽略此异常");
        }
    }

    @Override
    public void start() {
        try {
            this.update(Cycle.STARTED, null, null);
        } catch (Exception e) {
            System.out.println("当前线程started监听事件发生未知异常，已跳过该事件监听");
        }
        super.start();
    }

    @Override
    public Cycle getCycle() {
        return this.cycle;
    }
}

