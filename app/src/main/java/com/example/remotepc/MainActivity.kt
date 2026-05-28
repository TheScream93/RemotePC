package com.example.remotepc

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Пов'язуємо елементи UI з Kotlin кодом
        val editTextInput = findViewById<EditText>(R.id.editTextInput)
        val buttonSave = findViewById<Button>(R.id.buttonSave)
        val textViewSaved = findViewById<TextView>(R.id.textViewSaved)

        // Завантажуємо збережені дані при запуску
        loadSavedData(textViewSaved)

        // Додаємо слухач на кнопку "Зберегти"
        buttonSave.setOnClickListener {
            val inputText = editTextInput.text.toString()

            // Перевіряємо, чи користувач щось вводив
            if (inputText.isEmpty()) {
                Toast.makeText(this, "Введіть текст!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Зберігаємо дані
            saveData(inputText)

            // Оновлюємо TextView з новим текстом
            textViewSaved.text = "Збережено: $inputText"

            // Очищаємо input поле
            editTextInput.text.clear()

            // Показуємо повідомлення користувачу
            Toast.makeText(this, "Дані збережені!", Toast.LENGTH_SHORT).show()
        }
    }

    // Функція для збереження даних
    private fun saveData(text: String) {
        // SharedPreferences - місця зберіганні даних на пристрої
        val sharedPref = getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        // Зберігаємо текст з ключем "saved_text"
        editor.putString("saved_text", text)
        editor.apply() // Застосовуємо зміни
    }

    // Функція для завантаження даних
    private fun loadSavedData(textView: TextView) {
        val sharedPref = getSharedPreferences("MyApp", Context.MODE_PRIVATE)

        // Отримуємо збережений текст (якщо його немає, повертаємо "нічого")
        val savedText = sharedPref.getString("saved_text", "нічого") ?: "нічого"

        // Показуємо на ецені
        textView.text = "Збережено: $savedText"
    }
}

