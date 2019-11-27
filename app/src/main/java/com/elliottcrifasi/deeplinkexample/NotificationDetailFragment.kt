package com.elliottcrifasi.deeplinkexample

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_notification_detail.*

class NotificationDetailFragment : Fragment(R.layout.fragment_notification_detail) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("id")?.let {
            notification_text.text = notification_text.text.toString() + " " + it
        }
    }
}
