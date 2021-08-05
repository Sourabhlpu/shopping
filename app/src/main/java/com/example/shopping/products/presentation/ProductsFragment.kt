package com.example.shopping.products.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shopping.common.presentation.model.UIToolbar
import com.example.shopping.databinding.FragmentProductsBinding


class ProductsFragment : Fragment() {
    private val binding get() = _binding!!

    private var _binding: FragmentProductsBinding? = null

    companion object{
        val uiToolbar : UIToolbar
            get() = UIToolbar(showSpinner = false, showTitle = true, showRightAction = false, isTopNav = true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }
}