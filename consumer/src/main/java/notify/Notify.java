package notify;

/**
 * Created by allan on 16-11-2.
 */
public interface Notify {

    void onreturn(String msg, String param);

    void onthrow(Throwable ex, String param);
}
