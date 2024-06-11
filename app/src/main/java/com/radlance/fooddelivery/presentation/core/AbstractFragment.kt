package com.radlance.fooddelivery.presentation.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import javax.inject.Inject

abstract class AbstractFragment<B : ViewBinding> : Fragment() {
    private var _binding: B? = null
    protected val binding: B
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bind(inflater, container)
        return binding.root
    }

    abstract fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): B

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}