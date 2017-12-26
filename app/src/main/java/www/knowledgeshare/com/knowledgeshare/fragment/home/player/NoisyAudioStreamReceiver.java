package www.knowledgeshare.com.knowledgeshare.fragment.home.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import www.knowledgeshare.com.knowledgeshare.service.MediaService;

/**
 * 来电/耳机拔出时暂停播放
 * Created by wcy on 2016/1/23.
 */
public class NoisyAudioStreamReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        MediaService.startCommand(context, Actions.ACTION_MEDIA_PLAY_PAUSE);
    }
}
