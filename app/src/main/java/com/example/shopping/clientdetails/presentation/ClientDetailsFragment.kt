package com.example.shopping.clientdetails.presentation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shopping.R
import com.example.shopping.common.presentation.model.UIToolbar


const val ARG_CLIENT = "client"


class ClientDetailsFragment : Fragment() {
    private var clientId: Long? = null

    companion object {
        val uiToolbar : UIToolbar
            get() = UIToolbar(showSpinner = false, showTitle = true, showRightAction = false)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            clientId = it.getLong(ARG_CLIENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_client_details, container, false)
    }
}