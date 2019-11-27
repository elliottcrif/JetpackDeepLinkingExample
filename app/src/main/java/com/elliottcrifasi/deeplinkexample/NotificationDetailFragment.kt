package com.elliottcrifasi.deeplinkexample

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_notification_detail.*

class NotificationDetailFragment : Fragment(R.layout.fragment_notification_detail) {

    val args: NotificationDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notificationIdText.text = "ID: ${args.id}"
    }
}
