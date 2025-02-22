import java.util.Random;
import java.util.concurrent.Semaphore;

public class Writer extends Thread{
    static Shared Shared_obj;
    static Semaphore wmutex, M1, M2, M3;
    Random rand = new Random();
    int time_to_sleep = rand.nextInt(31);

    Writer(Shared Shared_obj, Semaphore wmutex, Semaphore M1, Semaphore M2, Semaphore M3){
        Writer.Shared_obj = Shared_obj;
        Writer.wmutex = wmutex;
        Writer.M1 = M1;
        Writer.M2 = M2;
        Writer.M3 = M3;
    }

    public void run(){
        try {
            //Entry Section
            System.out.println("WRITER " + this.getName() + " is ENTERING WRITER CODE");
            wmutex.acquire();

            Shared_obj.incWriteCount();
            if (Shared_obj.getWriteCount() == 1){
                M1.acquire();
            }

            wmutex.release();
            M2.acquire();

            //Critical Section
            System.out.println("WRITER " + this.getName() + " is now in Critical Section performing WRITE");
            System.out.println("WRITER " + this.getName() + " is writing for " + time_to_sleep + " milliseconds");
            sleep(time_to_sleep);
            M2.release();

            //Exit Section
            System.out.println("WRITER " + this.getName() + " is now EXITING");
            wmutex.acquire();
            Shared_obj.decWriteCount();
            if (Shared_obj.getWriteCount() == 0){
                M1.release();
            }
            wmutex.release();
        } catch (InterruptedException e) {
            System.out.println("Interrupt in Writer");
        }

    }
}
