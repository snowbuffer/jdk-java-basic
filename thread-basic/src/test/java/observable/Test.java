package observable;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-07-30 16:34
 */
public class Test {

    public static void main(String[] args){
        ObservableThread<String> thread = new ObservableThread<>(() -> {
            System.out.println("ObservableThread.aaaa");
            return  null;
        });
        thread.start();

        ObservableThread<String> thread1 = new ObservableThread<>(new TaskLifeCycle<String>() {
            @Override
            public void onStart(Thread thread) {
                System.out.println("ObservableThread.onStart");
                int i = 1/0;
            }

            @Override
            public void onRunning(Thread thread) {
                System.out.println("ObservableThread.onRunning");
                int i = 1/0;
            }

            @Override
            public void onFinished(Thread thread, String result) {
                System.out.println("ObservableThread.onFinished" + result);
                int i = 1/0;
            }

            @Override
            public void onError(Thread thread, Exception e) {
                System.out.println("ObservableThread.onError" + e);
                int i = 1/0;
            }
        }, () -> {
            int i = 1/0;
            System.out.println("ObservableThread.call");
            return "aaaaaaaaa";
        });

        thread1.start();
    }
}

