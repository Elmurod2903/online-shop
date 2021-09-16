package com.example.onlineshop.view.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.onlineshop.databinding.FragmentProfileBinding
import com.example.onlineshop.view.GmailActivity
import com.example.onlineshop.utils.PrefUtils


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding!!.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding!!.editGmail.setOnClickListener {
            startActivity(Intent(view.context, GmailActivity::class.java))
        }
        if (_binding!!.gmailNumber.text.isEmpty()) {
            val newGmail = PrefUtils.getNewGmail()
            _binding?.gmailNumber?.setText(newGmail).toString()

        }
        if (_binding!!.userName.text.isEmpty()) {
            val username = PrefUtils.getFullName()
            _binding?.userName?.setText(username).toString()
        }
        if (_binding!!.number.text.isEmpty()) {
            val number = PrefUtils.getNumber()
            _binding?.number?.setText(number).toString()
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}