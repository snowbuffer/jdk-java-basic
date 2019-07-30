package observable;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-07-29 18:02
 */
public interface Observable {

    enum Cycle {
        STARTED,RUNNING,DONE,ERROR
    }

    Cycle getCycle();

    void start();

    void interrupt();
}

