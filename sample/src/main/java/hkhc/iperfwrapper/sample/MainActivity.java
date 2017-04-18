package hkhc.iperfwrapper.sample;

import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import hkhc.iperfwrapper.AndroidDefaults;
import hkhc.iperfwrapper.Iperf3;
import hkhc.iperfwrapper.IperfException;
import hkhc.iperfwrapper.R;
import hkhc.utils.FileUtils;

public class MainActivity extends AppCompatActivity {


    private Handler handler = new Handler();
    Iperf3 perf3 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button)findViewById(R.id.button);
        new File(MainActivity.this.getCacheDir()+"/output.txt").delete();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {

                    public void run() {

                        try {

                            perf3 = new Iperf3();
                            perf3
                                    .newTest()
                                    .defaults(new AndroidDefaults(MainActivity.this))
                                    .testRole(Iperf3.ROLE_CLIENT)
                                    .hostname("192.168.51.55")
                                    .logfile(MainActivity.this.getCacheDir()+"/output.txt")
                                    .outputJson(true)
                                    .runClient();
                            handler.post(new Runnable() {
                                public void run() {
                                    TextView tv = (TextView)findViewById(R.id.editResult);
                                    try {
                                        String result = FileUtils.readFileToString(MainActivity.this.getCacheDir() + "/output.txt");
                                        tv.setText(result);
                                    }
                                    catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        catch (IperfException e) {
                            e.printStackTrace();
                        }
                        finally {
                            perf3.freeTest();
                        }

//                        HelloJni h = new HelloJni();
//                        final String msg = h.stringFromJNI();
//                        handler.post(new Runnable() {
//                            public void run() {
//                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
//                            }
//                        });

                    }

                }.start();
            }
        });
    }
}
