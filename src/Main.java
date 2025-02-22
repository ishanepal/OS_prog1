import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Shared Shared_obj = new Shared(0,0);
        Semaphore rmutex = new Semaphore(1, true);
        Semaphore wmutex = new Semaphore(1, true);
        Semaphore M1 = new Semaphore(1, true);
        Semaphore M2 = new Semaphore(1, true);
        Semaphore M3 = new Semaphore(1, true);

        Random rand = new Random();
        for (int i = 0; i < 100; i++){
            int randomValue = rand.nextInt(3);
            if (randomValue == 0){
                Writer writer = new Writer(Shared_obj, wmutex, M1, M2, M3);
                writer.start();
                System.out.println("Creating new WRITER " + writer.getName());
            } else {
                Thread reader = new Thread(new Reader(Shared_obj, rmutex, M1, M2, M3));
                reader.start();
                System.out.println("Creating new READER " + reader.getName());
            }

            int sleepTime = rand.nextInt(31);
            try{
                Thread.sleep(sleepTime);
            } catch (InterruptedException e){
                System.out.println("Sleep interruption");
            }
        }

        try{
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Interrupt in main");
            throw new RuntimeException(e);
        }
        System.out.println("####### MAIN THREAD ENDING NOW!");
    }
}