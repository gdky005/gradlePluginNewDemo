package gdky005.timer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import gdky005.lib1.LibTest;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("--->", "===================");
        new Test();
        Log.e("--->", "===================");

        LibTest.show();

    }
}
