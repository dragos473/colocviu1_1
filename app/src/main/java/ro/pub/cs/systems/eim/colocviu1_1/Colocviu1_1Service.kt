package ro.pub.cs.systems.eim.colocviu1_1

import android.app.Service
import android.content.Intent
import android.os.IBinder

class Colocviu1_1Service : Service() {
    private var processingThread: ProcessingThread? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val text = intent?.getStringExtra("mainText") ?: ""
        processingThread = ProcessingThread(this, text)
        processingThread?.start()
        return START_REDELIVER_INTENT
    }
    override fun onBind(intent: Intent?): IBinder? = null
    override fun onDestroy() {
        processingThread?.stopThread()
        super.onDestroy()
    }
}