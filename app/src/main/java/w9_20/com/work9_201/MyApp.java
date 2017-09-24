package w9_20.com.work9_201;

import android.app.Application;

import org.xutils.x;

/**
 * Created by shi on 2017/9/20.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }
}
