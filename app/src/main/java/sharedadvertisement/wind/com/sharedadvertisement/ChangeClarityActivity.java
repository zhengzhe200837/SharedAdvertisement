package sharedadvertisement.wind.com.sharedadvertisement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xiao.nicevideoplayer.Clarity;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;
import sharedadvertisement.wind.com.sharedadvertisement.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChangeClarityActivity extends AppCompatActivity {

    private NiceVideoPlayer mNiceVideoPlayer;
    private String mPath;
    private long mVideoLength;
    public static String VIDEOPATH = "VIDEOPATH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_clarity);
        mPath = getIntent().getStringExtra(VIDEOPATH);
        init();
        android.util.Log.d("zz", "--zz + screenwidth = " + getWindowManager().getDefaultDisplay().getWidth());
        android.util.Log.d("zz", "--zz + screenheight = " + getWindowManager().getDefaultDisplay().getHeight());
    }

    private void init() {
        mNiceVideoPlayer = (NiceVideoPlayer) findViewById(R.id.nice_video_player);
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // IjkPlayer or MediaPlayer
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("video");
        controller.setClarity(getClarites(), 0);
//        zhengzhe start
//        Glide.with(this)
//                .load("/storage/emulated/0/DCIM/Camera/picture.jpg") //also url
//                .placeholder(R.drawable.img_default)
//                .crossFade()
//                .into(controller.imageView());
        controller.imageView().setImageBitmap(getVideoFirstFrame(mPath));
        controller.setLenght(mVideoLength);
//        zhengzhe end
        mNiceVideoPlayer.setController(controller);
    }

//    zhengzhe start
    private Bitmap getVideoFirstFrame(String path) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
//        mmr.setDataSource(getClarites().get(1).videoUrl, new HashMap<String, String>());  //获取网络视频
        String time = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        mVideoLength = Long.valueOf(time);
        int second = Integer.valueOf(time) / 1000;
        Bitmap bitmap = mmr.getFrameAtTime(1,MediaMetadataRetriever.OPTION_CLOSEST_SYNC); // 获取指定时间点的帧图片  单位是微秒
//        Bitmap bitmap = mmr.getFrameAtTime();   //获取第一帧    上面方法比下面方法精确
        return bitmap;
    }
//    zhengzhe end

    public List<Clarity> getClarites() {
        List<Clarity> clarities = new ArrayList<>();
        clarities.add(new Clarity("标清", "270P", mPath));
        clarities.add(new Clarity("标清", "270P", "http://play.g3proxy.lecloud.com/vod/v2/MjUxLzE2LzgvbGV0di11dHMvMTQvdmVyXzAwXzIyLTExMDc2NDEzODctYXZjLTE5OTgxOS1hYWMtNDgwMDAtNTI2MTEwLTE3MDg3NjEzLWY1OGY2YzM1NjkwZTA2ZGFmYjg2MTVlYzc5MjEyZjU4LTE0OTg1NTc2ODY4MjMubXA0?b=259&mmsid=65565355&tm=1499247143&key=f0eadb4f30c404d49ff8ebad673d3742&platid=3&splatid=345&playid=0&tss=no&vtype=21&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super"));
//        clarities.add(new Clarity("高清", "480P", "http://play.g3proxy.lecloud.com/vod/v2/MjQ5LzM3LzIwL2xldHYtdXRzLzE0L3Zlcl8wMF8yMi0xMTA3NjQxMzkwLWF2Yy00MTk4MTAtYWFjLTQ4MDAwLTUyNjExMC0zMTU1NTY1Mi00ZmJjYzFkNzA1NWMyNDc4MDc5OTYxODg1N2RjNzEwMi0xNDk4NTU3OTYxNzQ4Lm1wNA==?b=479&mmsid=65565355&tm=1499247143&key=98c7e781f1145aba07cb0d6ec06f6c12&platid=3&splatid=345&playid=0&tss=no&vtype=13&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super"));
//        clarities.add(new Clarity("超清", "720P", "http://play.g3proxy.lecloud.com/vod/v2/MjQ5LzM3LzIwL2xldHYtdXRzLzE0L3Zlcl8wMF8yMi0xMTA3NjQxMzkwLWF2Yy00MTk4MTAtYWFjLTQ4MDAwLTUyNjExMC0zMTU1NTY1Mi00ZmJjYzFkNzA1NWMyNDc4MDc5OTYxODg1N2RjNzEwMi0xNDk4NTU3OTYxNzQ4Lm1wNA==?b=479&mmsid=65565355&tm=1499247143&key=98c7e781f1145aba07cb0d6ec06f6c12&platid=3&splatid=345&playid=0&tss=no&vtype=13&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super"));
//        clarities.add(new Clarity("蓝光", "1080P", "http://play.g3proxy.lecloud.com/vod/v2/MjQ5LzM3LzIwL2xldHYtdXRzLzE0L3Zlcl8wMF8yMi0xMTA3NjQxMzkwLWF2Yy00MTk4MTAtYWFjLTQ4MDAwLTUyNjExMC0zMTU1NTY1Mi00ZmJjYzFkNzA1NWMyNDc4MDc5OTYxODg1N2RjNzEwMi0xNDk4NTU3OTYxNzQ4Lm1wNA==?b=479&mmsid=65565355&tm=1499247143&key=98c7e781f1145aba07cb0d6ec06f6c12&platid=3&splatid=345&playid=0&tss=no&vtype=13&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super"));
        return clarities;
    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }
}
