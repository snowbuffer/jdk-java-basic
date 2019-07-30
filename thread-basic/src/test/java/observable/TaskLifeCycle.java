package observable;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-07-29 18:05
 */
public interface TaskLifeCycle<T> {

    void onStart(Thread thread);

    void onRunning(Thread thread);

    void onFinished(Thread thread, T result);

    void onError(Thread thread, Exception e);

    class EmptyTaskLifeCycle<T> implements TaskLifeCycle<T> {
        @Override
        public void onStart(Thread thread) {
            // 空实现
        }

        @Override
        public void onRunning(Thread thread) {
            // 空实现
        }

        @Override
        public void onFinished(Thread thread, T result) {
            // 空实现
        }

        @Override
        public void onError(Thread thread, Exception e) {
            // 空实现
        }
    }
}

