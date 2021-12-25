package cn.com.x1001.watch;

/**
 * @author keven1z
 * @date 2021/12/24
 */
public class WatchManager {
    public static void startWatch(Watch... watches){
        for (Watch watch:watches){
            if (watch != null){
                watch.start();
            }
        }
    }
}
