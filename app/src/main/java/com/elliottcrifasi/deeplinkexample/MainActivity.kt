package com.elliottcrifasi.deeplinkexample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.findNavController
import androidx.navigation.get
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val CHANNEL_ID = "1"
    private val navController by lazy { findNavController(R.id.mainNavigationFragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = nav_view
        navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController)
        createNotificationChannel()
    }

    override fun onStart() {
        super.onStart()
        navController.graph.forEach {
            Log.d(javaClass.name, it.id.toString())
            Log.d(javaClass.name, it.label.toString())
        }


        navController.navigate(Uri.parse("example://notifications/5"))
    }

    override fun onStop() {
        super.onStop()
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = "channel"
        val descriptionText = "description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createDeepLinkNotification(destination: Int) {
        val destinationLabel = navController.graph[destination].label
        val intent = navController
            .createDeepLink()
            .setDestination(destination)
            .createTaskStackBuilder()
            .getPendingIntent(0,0)

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle("Notification Deep Link to $destinationLabel")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(intent)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify((Math.random()*100).toInt(), builder.build())
        }
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navController.handleDeepLink(intent)
    }
}
