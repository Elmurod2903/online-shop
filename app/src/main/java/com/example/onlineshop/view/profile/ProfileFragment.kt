package com.example.onlineshop.view.profile

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentProfileBinding
import com.example.onlineshop.utils.LocaleManager
import com.example.onlineshop.view.GmailActivity
import com.example.onlineshop.utils.PrefUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.orhanobut.hawk.Hawk
import java.util.jar.Manifest

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding

    private lateinit var dialog: AlertDialog
    private var pathToImage: String? = null

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
        if (_binding!!.userName.text.isEmpty()) {
            val username = PrefUtils.getFullName()
            _binding?.userName?.setText(username).toString()
        }
        if (_binding!!.number.text.isEmpty()) {
            val number = PrefUtils.getNumber()
            _binding?.number?.setText(number).toString()
        }
        _binding?.userImage?.setOnClickListener {
            requestReadWritePermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
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

    override fun onResume() {
        super.onResume()
            val newGmail = PrefUtils.getNewGmail()
            _binding?.gmailNumber?.setText(newGmail).toString()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        LocaleManager.setLocale(context)
    }


    private val requestReadWritePermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                requestLauncherGallery.launch(intent)
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!requireActivity().shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        dialog = MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Give access to files")
                            .setMessage("Grant permission in settings")
                            .setPositiveButton("Ok") { _, _ ->
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri =
                                    Uri.fromParts("package", requireActivity().packageName, null)
                                intent.data = uri
                                startActivity(intent)
                            }
                            .setNegativeButton("Cancel", null)
                            .create()
                        dialog.show()
                    }
                }
                Toast.makeText(
                    requireContext(),
                    "You need to grand permission to select image",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    private val requestLauncherGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == RESULT_OK) {
                val imageUri: Uri? = result.data?.data
//                PrefUtils.setImageUser(image)
                val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, imageUri)
//                val imageStream = context?.openFileInput(imageUri.toString())
//                val selectedImage = BitmapFactory.decodeStream(imageStream)
                _binding?.userImage?.setImageBitmap(bitmap)

                pathToImage = bitmap.toString()
            }
        }

}