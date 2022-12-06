package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationChannelCompat.DEFAULT_CHANNEL_ID
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.databinding.ActivityMainBinding
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(DEFAULT_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val slowa = binding.barka
        val image = binding.imageView
        val seek = binding.seekBar
        val array = resources.getStringArray(R.array.barka)

        var builder = NotificationCompat.Builder(this, DEFAULT_CHANNEL_ID)
            .setSmallIcon(R.drawable.notification)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Much longer text that cannot fit one line..."))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)



        var i = 0
        var magic = 1
        var resID = resources.getIdentifier("zwr$magic", "drawable", packageName)


        fun doIt(){
            if(i>=7){
                i = 7
                binding.next.isEnabled = false
            }
            else{
                binding.next.isEnabled = true
            }


            if(i<=0){
                i = 0
                binding.prew.isEnabled = false
            }
            else{
                binding.prew.isEnabled = true
            }

            seek.progress = i

            magic = ((i/2)%4)+1

            resID = resources.getIdentifier("zwr$magic", "drawable", packageName)


            if(abs(i)%2==1){
                image.setImageResource(R.drawable.ref)
                slowa.text = array[0]
            }
            else{
                image.setImageResource(resID)
                slowa.text = array[magic]
            }
        }
        doIt()

        binding.next.setOnClickListener{
            i++
            doIt()

            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(2137, builder.build())
            }
        }

        binding.prew.setOnClickListener{
            i--
            doIt()
        }


        seek.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                i = seek.progress
                doIt()
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
            }
        })
    }
}