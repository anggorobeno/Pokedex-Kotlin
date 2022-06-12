package com.example.pokedex.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.pokedex.databinding.FragmentBottomDialogBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class BaseDialog(val context: Context, contentView: Int) {
  private val dialog: BottomSheetDialog = BottomSheetDialog(context)
  private var bottomSheetBehavior: BottomSheetBehavior<View>? = null
  var saveButtonListener: DialogCallback? = null
  private var _binding: FragmentBottomDialogBinding? = null
  private val binding get() = _binding!!

  init {
    val inflater = LayoutInflater.from(context)
    _binding =
      FragmentBottomDialogBinding.inflate(
        inflater,
        dialog.findViewById(android.R.id.content),
        false
      )
    dialog.setContentView(binding.root)
  }

  interface DialogCallback {
    fun onDialogShow(text: String)
  }

  fun setNickname(listener: DialogCallback) {
    saveButtonListener = listener
    if (binding.tvInputUsername.text != null || binding.tvInputUsername.text.toString()
        .isNotEmpty() || binding.tvInputUsername.text.toString().isNotBlank()
    ) {
      binding.btnSaveUsername.setOnClickListener {
        saveButtonListener?.onDialogShow(binding.tvInputUsername.text.toString().trim())
      }
    }
  }

  fun show() {
    dialog.show()
  }

  fun dismiss() {
    dialog.dismiss()
  }

}