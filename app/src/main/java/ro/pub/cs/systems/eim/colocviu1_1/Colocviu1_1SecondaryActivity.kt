package ro.pub.cs.systems.eim.colocviu1_1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Colocviu1_1SecondaryActivity : AppCompatActivity() {
    private lateinit var text: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_colocviu1_1_secondary)

        text = findViewById(R.id.text)
        text.setText(intent.getStringExtra("mainText"))

        val registerButton = findViewById<Button>(R.id.register)
        val cancelButton = findViewById<Button>(R.id.cancel)

        registerButton.setOnClickListener{
            setResult(Activity.RESULT_OK, null)
            finish()
        }

        cancelButton.setOnClickListener{
            setResult(Activity.RESULT_CANCELED, null)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}