package ro.pub.cs.systems.eim.colocviu1_1
import android.content.Context
import android.content.Intent
import android.provider.SyncStateContract
import java.util.Date
import kotlin.math.sqrt
import kotlin.random.Random

class ProcessingThread(private val context: Context, private var text: String) : Thread() {
    @Volatile private var isRunning = true


    override fun run(){
        try {
            Thread.sleep(2000)
        } catch(e: InterruptedException) {
            isRunning = false
        }
        sendMessage()
    }

    private fun sendMessage(){
        val intent = Intent()
        intent.action = "com.pub.cs.systems.eim.colocviu1_1.MESSAGE"
        intent.putExtra("message", "${Date()}, $text")
        intent.setPackage(context.packageName)
        context.sendBroadcast(intent)
    }

    fun stopThread() {
        isRunning = false
        interrupt()
    }

}