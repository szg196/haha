package w9_20.com.work9_201;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    
    private Integer[]music={R.raw.alice,
            R.raw.c,
            R.raw.joshviettiwhereisthelove,
            R.raw.kazenotoorimichi,
            R.raw.summer,
            R.raw.turkey};

    private ListView listView;
    private MediaPlayer mediaPlayer=new MediaPlayer();
    private SeekBar seekBar;
    private boolean aBoolean = false;
    private TextView textView,play;
    private ImageView bac;
    private int num=0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mediaPlayer!=null){
                int z = mediaPlayer.getDuration();
                int b = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(b);
                textView.setText(fr(b)+"/"+fr(z));
            }
        }
    };
    private String fr(int time){
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        return sdf.format(new Date(time));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        seekBar = (SeekBar) findViewById(R.id.seek);
        textView = (TextView) findViewById(R.id.timee);
        play = (TextView) findViewById(R.id.play);
        bac = (ImageView) findViewById(R.id.bac);
        int a = R.mipmap.ic_launcher;


        String []name =new String[music.length];
        for(int i =0;i<name.length;i++){
            name[i]=String.valueOf(music[i]);
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                name
        );

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        final My my =new My();
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                aBoolean = false;
                aBoolean = true;
                seekBar.setMax(0);
                num=1;

                play.setText("⏸");
                mediaPlayer.stop();
                mediaPlayer.seekTo(0);
                mediaPlayer = new MediaPlayer();
                mediaPlayer = MediaPlayer.create(parent.getContext(),music[position]);
                seekBar.setMax(mediaPlayer.getDuration());

                my.start();
                mediaPlayer.start();

            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying() && num!=0){
                    aBoolean = false;
                    My my =new My();
                    my.start();
                    mediaPlayer.pause();
                    play.setText("▶");
                }else if(!mediaPlayer.isPlaying()&&num!=0){
                    aBoolean = true;
                    My my =new My();
                    my.start();
                    mediaPlayer.start();
                    play.setText("⏸");
                }else if(!mediaPlayer.isPlaying()&&num==0){
                    aBoolean = true;
                    num=1;
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer = MediaPlayer.create(MainActivity.this,music[0]);
                    seekBar.setMax(mediaPlayer.getDuration());
                    My my =new My();
                    my.start();
                    play.setText("⏸");
                    mediaPlayer.start();
                }
            }
        });
    }


    class My extends Thread{
        @Override
        public void run() {
            while (aBoolean){
                super.run();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }
    }
}
