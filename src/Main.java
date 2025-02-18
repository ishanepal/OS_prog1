import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore rmutex = new Semaphore(1);
        Semaphore wmutex = new Semaphore(1);
        Semaphore M1 = new Semaphore(1);
        Semaphore M2 = new Semaphore(1);
        Semaphore M3 = new Semaphore(1);

        for (int i = 0; i < 100; i++){

        }
    }
}