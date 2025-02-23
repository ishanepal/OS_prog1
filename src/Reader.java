import java.util.Random;
import java.util.concurrent.Semaphore;

public class Reader implements Runnable{
    private static Shared Shared_obj;
    private static Semaphore rmutex, M1, M2, M3;
    Random rand = new Random();
    int time_to_sleep = rand.nextInt(21);

    Reader(Shared Shared_obj, Semaphore rmutex, Semaphore M1, Semaphore M2, Semaphore M3){
        Reader.Shared_obj = Shared_obj;
        Reader.rmutex = rmutex;
        Reader.M1 = M1;
        Reader.M2 = M2;
        Reader.M3 = M3;
    }

    @Override
    public void run() {
        try{
            System.out.println("READER " + Thread.currentThread().getName() + " is ENTERING READER CODE\n");
            //Entry Section
            M3.acquire();
            M1.acquire();
            rmutex.acquire();

            Shared_obj.incReadCount();
            if (Shared_obj.getReadCount() == 1){
                M2.acquire();
            }
            rmutex.release();
            M1.release();
            M3.release();

            //Critical Section
            System.out.println("READER " + Thread.currentThread().getName() + " is in CRITICAL SECTION performing READ\n");
            System.out.println("READER " + Thread.currentThread().getName() + " is reading for " + time_to_sleep + " milliseconds\n");
            Thread.sleep(time_to_sleep);

            //Exit Section
            rmutex.acquire();
            Shared_obj.decReadCount();
            if (Shared_obj.getReadCount() == 0){
                M2.release();
            }
            rmutex.release();
            System.out.println("READER " + Thread.currentThread().getName() + " is now EXITING\n");
        } catch (InterruptedException e) {
            System.out.println("Interrupted Exception");
        }
    }
}
