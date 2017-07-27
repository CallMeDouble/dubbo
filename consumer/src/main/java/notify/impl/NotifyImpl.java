package notify.impl;

import notify.Notify;

/**
 * Created by allan on 16-11-2.
 */
public class NotifyImpl implements Notify {
    public void onreturn(String msg, String param) {

    }

    public void onthrow(Throwable ex, String param) {
        System.out.println("========================================================================");
        ex.printStackTrace();
        System.out.println(param);
    }
}
