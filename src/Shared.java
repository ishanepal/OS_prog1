public class Shared {
    private int readCount;
    private int writeCount;

    Shared(int readCount, int writeCount) {
        this.readCount = readCount;
        this.writeCount = writeCount;
    }

    public int getWriteCount() {
        return writeCount;
    }

    public int getReadCount() {
        return readCount;
    }

    public void incReadCount() {
        readCount++;
    }

    public void incWriteCount() {
        writeCount++;
    }

    public void decReadCount(){
        readCount--;
    }

    public void decWriteCount(){
        writeCount--;
    }

}
