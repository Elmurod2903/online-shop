package com.example.onlineshop.view.changelanguage

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentChangeLanguageBinding
import com.example.onlineshop.utils.ktx.successToast
import com.example.onlineshop.view.MainActivity
import com.example.onlineshop.view.home.HomeFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.orhanobut.hawk.Hawk


class ChangeLanguageFragment : BottomSheetDialogFragment(), RadioGroup.OnCheckedChangeListener {

    private var _binding: FragmentChangeLanguageBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeLanguageBinding.inflate(inflater, container, false)
        return binding!!.root

    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding!!.btnUzbek.setOnClickListener {
            Hawk.put("pref_lang", "uz")
            successToast(getString(R.string.uzbek))
            requireActivity().finish()
            startActivity(Intent(context, MainActivity::class.java))
            dismiss()
        }
        _binding!!.btnEnglish.setOnClickListener {
            Hawk.put("pref_lang", "en")
            successToast(getString(R.string.english))
            requireActivity().finish()
            startActivity(Intent(context, MainActivity::class.java))
            dismiss()
        }

        val prefs = requireActivity().getSharedPreferences("LANG_PREFS", MODE_PRIVATE)
        _binding!!.rg.check(prefs.getInt("lang_btn_id", R.id.btn_english));
        _binding!!.rg.setOnCheckedChangeListener(this);
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireActivity(), R.style.BottomSheetDialogTheme)

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ChangeLanguageFragment()
    }

    @SuppressLint("CommitPrefEdits")
    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        val editor = requireActivity().getSharedPreferences("LANG_PREFS", MODE_PRIVATE).edit()
        when (group?.checkedRadioButtonId) {
            R.id.btn_uzbek -> {
            }
            R.id.btn_english -> {
            }
        }
        editor.putInt("lang_btn_id", group!!.checkedRadioButtonId)
        editor.apply()
    }


}



