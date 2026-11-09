package ro.pub.cs.systems.eim.colocviu1_1

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Colocviu1_1MainActivity : AppCompatActivity() {
    private lateinit var mainText: EditText
    private lateinit var pressedText: EditText

    var pressed = 0;
    var pressedNorth = false
    var pressedSouth = false
    var pressedEast = false
    var pressedWest = false
    var serviceStatus = false

    private val secondaryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "reply: Register", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "reply: Cancel", Toast.LENGTH_LONG).show()
        }
    }

    private fun startService(){
        if (pressed == 4 && !serviceStatus){
            val intent = Intent(this, Colocviu1_1Service::class.java)
            intent.putExtra("mainText", mainText.text.toString())
            startService(intent)
            serviceStatus = true
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_colocviu1_1_main)

        mainText = findViewById(R.id.main_text)
        pressedText = findViewById(R.id.times_pressed)
        val northButton = findViewById<Button>(R.id.north)
        val southButton = findViewById<Button>(R.id.south)
        val eastButton = findViewById<Button>(R.id.east)
        val westButton = findViewById<Button>(R.id.west)
        val activityGo = findViewById<Button>(R.id.activityGo)


        northButton.setOnClickListener{
            mainText.append(", North")
            if (pressedNorth == false){
                pressed++
                pressedText.setText(pressed.toString())
            }
            pressedNorth = true
            startService()
        }

        southButton.setOnClickListener{
            mainText.append(", South")
            if (pressedSouth == false){
                pressed++
                pressedText.setText(pressed.toString())
            }
            pressedSouth = true
            startService()
        }
        eastButton.setOnClickListener{
            mainText.append(", East")
            if (pressedEast == false){
                pressed++
                pressedText.setText(pressed.toString())
            }
            pressedEast = true
            startService()
        }
        westButton.setOnClickListener{
            mainText.append(", East")
            if (pressedWest == false){
                pressed++
                pressedText.setText(pressed.toString())
            }
            pressedWest = true
            startService()
        }

        activityGo.setOnClickListener{
            val intent = Intent(this, Colocviu1_1SecondaryActivity::class.java)
            intent.putExtra("mainText", mainText.text.toString())
            startActivity(intent)
            pressedNorth = false
            pressedSouth = false
            pressedEast = false
            pressedWest = false
            pressed = 0
            pressedText.setText(pressed.toString())
            mainText.setText("")
        }

        val filter = IntentFilter("com.pub.cs.systems.eim.colocviu1_1.MESSAGE")
        ContextCompat.registerReceiver(
            this,
            messageReceiver,
            filter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("pressed", pressed)
        outState.putBoolean("pressedNorth", pressedNorth)
        outState.putBoolean("pressedSouth", pressedSouth)
        outState.putBoolean("pressedEast", pressedEast)
        outState.putBoolean("pressedWest", pressedWest)
        outState.putString("pressedText", pressedText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        pressed = savedInstanceState.getInt("pressed")
        pressedNorth = savedInstanceState.getBoolean("pressedNorth")
        pressedSouth = savedInstanceState.getBoolean("pressedSouth")
        pressedEast = savedInstanceState.getBoolean("pressedEast")
        pressedWest = savedInstanceState.getBoolean("pressedWest")
        pressedText.setText(savedInstanceState.getString("pressedText"))
    }

    private val messageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == "com.pub.cs.systems.eim.colocviu1_1.MESSAGE") {
                val message = intent.getStringExtra("message")
                Log.d("TestEIM_Receiver", "Broadcast received: $message")
            }
        }
    }
    override fun onDestroy() {
        stopService(Intent(applicationContext, Colocviu1_1Service::class.java))
        unregisterReceiver(messageReceiver)
        super.onDestroy()
    }
}