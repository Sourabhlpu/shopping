package com.example.shopping.createclient.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shopping.common.presentation.model.UIToolbar
import com.example.shopping.databinding.FragmentCreateClientBinding

class CreateClientFragment : Fragment() {

    companion object{
        val uiToolbar : UIToolbar
            get() = UIToolbar(showSpinner = false, showTitle = true, showRightAction = false, isTopNav = false)
    }

    private val binding get() = _binding!!

    private var _binding: FragmentCreateClientBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateClientBinding.inflate(inflater, container, false)
        return binding.root
    }
}