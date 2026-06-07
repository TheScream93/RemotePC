package com.example.remotepc

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SettingsDialog : DialogFragment() {

    private lateinit var layoutPassword: TextInputLayout
    private lateinit var layoutSshKey: TextInputLayout
    private lateinit var filePicker: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filePicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                view?.findViewById<TextInputEditText>(R.id.inputSshKeyPath)
                    ?.setText(it.path ?: it.toString())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dialog_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutPassword = view.findViewById(R.id.layoutPassword)
        layoutSshKey = view.findViewById(R.id.layoutSshKey)

        view.findViewById<MaterialButtonToggleGroup>(R.id.toggleAuthType)
            .addOnButtonCheckedListener { _, checkedId, isChecked ->
                if (!isChecked) return@addOnButtonCheckedListener
                when (checkedId) {
                    R.id.btnPassword -> {
                        layoutPassword.visibility = View.VISIBLE
                        layoutSshKey.visibility = View.GONE
                    }
                    R.id.btnSshKey -> {
                        layoutPassword.visibility = View.GONE
                        layoutSshKey.visibility = View.VISIBLE
                    }
                }
            }

        layoutSshKey.setEndIconOnClickListener {
            filePicker.launch("*/*")
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}