package cn.com.x1001.watch;

public abstract class Watch extends Thread{
    public void pause(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {

        }
    }
}
