import java.util.Random;
import java.util.concurrent.Semaphore;

public class Prog1 {
    public static void main(String[] args) {
        // Semaphore initialized to 1 and FAIR
        Shared Shared_obj = new Shared(0,0);
        Semaphore rmutex = new Semaphore(1, true);
        Semaphore wmutex = new Semaphore(1, true);
        Semaphore M1 = new Semaphore(1, true);
        Semaphore M2 = new Semaphore(1, true);
        Semaphore M3 = new Semaphore(1, true);

        Random rand = new Random();
        for (int i = 0; i < 100; i++){
            int randomValue = rand.nextInt(3);
            // Reader being twice as likely to be randomly selected
            if (randomValue == 0){
                Writer writer = new Writer(Shared_obj, wmutex, M1, M2, M3);
                System.out.println("Creating new WRITER " + writer.getName() + "\n");
                writer.start();
            } else {
                Thread reader = new Thread(new Reader(Shared_obj, rmutex, M1, M2, M3));
                System.out.println("Creating new READER " + reader.getName() + "\n");
                reader.start();
            }

            // Thread sleep
            int sleepTime = rand.nextInt(31);
            try{
                Thread.sleep(sleepTime);
            } catch (InterruptedException e){
                System.out.println("Sleep interruption");
            }
        }

        // Main thread sleep to finish execution
        try{
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Interrupt in main");
            throw new RuntimeException(e);
        }
        System.out.println("####### MAIN THREAD ENDING NOW!");
    }
}