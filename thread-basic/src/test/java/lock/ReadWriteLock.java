package lock;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-07-30 17:32
 */
public interface ReadWriteLock {

    Lock readLock();

    Lock writeLock();

    int getWritingWriters();

    int getWaitingWriters();

    int getReadingReaders();

    static ReadWriteLock readWriteLock() {
        return new ReadWriteLockImpl();
    }

    static ReadWriteLock readWriteLock(boolean preferWriter) {
        return new ReadWriteLockImpl(preferWriter);
    }

    class ReadWriteLockImpl implements ReadWriteLock {
        private final Object MUTEX = new Object();

        private int writingWriters = 0;
        private int waitingWriters = 0;
        private int readingReaders = 0;
        private boolean preferWriter;

        public ReadWriteLockImpl() {
            this(true);
        }

        public ReadWriteLockImpl(boolean preferWriter) {
            this.preferWriter = preferWriter;
        }

        @Override
        public Lock readLock() {
            return new ReadLock(this);
        }

        @Override
        public Lock writeLock() {
            return new WriteLock(this);
        }

        @Override
        public int getWritingWriters() {
            return this.writingWriters;
        }

        @Override
        public int getWaitingWriters() {
            return this.waitingWriters;
        }

        @Override
        public int getReadingReaders() {
            return this.readingReaders;
        }

        void incrementWritingWriters() {
            this.writingWriters++;
        }

        void incrementWaitingWriters() {
            this.waitingWriters++;
        }

        void incrementReadingReaders() {
            this.readingReaders++;
        }

        void deincrementWritingWriters() {
            this.writingWriters--;
        }

        void deincrementWaitingWriters() {
            this.waitingWriters--;
        }

        void deincrementReadingReaders() {
            this.readingReaders--;
        }

        Object getMUTEX() {
            return this.MUTEX;
        }

        boolean getPreferWriter() {
            return this.preferWriter;
        }

        void changePrefer(boolean preferWriter) {
            this.preferWriter = preferWriter;
        }
    }

    class ReadLock implements Lock {

        private ReadWriteLockImpl readWriteLock;

        public ReadLock(ReadWriteLockImpl readWriteLock) {
            this.readWriteLock = readWriteLock;
        }

        @Override
        public void lock() throws InterruptedException {
            synchronized (readWriteLock.getMUTEX()) {
                while (readWriteLock.getWritingWriters() > 0
                        || (readWriteLock.getPreferWriter() && readWriteLock.getWaitingWriters() > 0)) {
                    readWriteLock.getMUTEX().wait();
                }
                readWriteLock.incrementReadingReaders();
            }
        }

        @Override
        public void unlock() {
            synchronized (readWriteLock.getMUTEX()) {
                readWriteLock.deincrementReadingReaders();
                readWriteLock.changePrefer(true);
                readWriteLock.getMUTEX().notifyAll();
            }
        }
    }

    class WriteLock implements Lock {

        private ReadWriteLockImpl readWriteLock;

        public WriteLock(ReadWriteLockImpl readWriteLock) {
            this.readWriteLock = readWriteLock;
        }

        @Override
        public void lock() throws InterruptedException {
            synchronized (readWriteLock.getMUTEX()) {
                readWriteLock.incrementWaitingWriters();
                try {
                    while (readWriteLock.getWritingWriters() > 0
                            || readWriteLock.getReadingReaders() > 0) {
                        readWriteLock.getMUTEX().wait();
                    }
                } finally {
                    readWriteLock.deincrementWaitingWriters();
                }
                readWriteLock.incrementWritingWriters();
            }
        }

        @Override
        public void unlock() {
            synchronized (readWriteLock.getMUTEX()) {
                readWriteLock.deincrementWritingWriters();
                readWriteLock.changePrefer(false);
                readWriteLock.getMUTEX().notifyAll();
            }
        }
    }
}

