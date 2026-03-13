package org.thunderdog.challegram.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import org.thunderdog.challegram.telegram.Tdlib;

public class TGCallService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static TGCallService currentInstance() {
        return null;
    }

    public boolean compareCall(Tdlib tdlib, int callId) {
        return false;
    }

    public long getCallDuration() {
        return -1;
    }

    public int getCallBarsCount() {
        return -1;
    }

    public static void markLogViewed() {
    }

    public long getConnectionId() {
        return 0;
    }
}
