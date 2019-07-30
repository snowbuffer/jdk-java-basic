package lock;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-07-30 17:31
 */
public interface Lock {

    void lock() throws InterruptedException;

    void unlock();

}
