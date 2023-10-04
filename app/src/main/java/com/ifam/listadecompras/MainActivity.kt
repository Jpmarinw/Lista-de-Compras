package com.ifam.listadecompras

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val itemsAdapterr = ItemsAdapter()
        recyclerView.adapter = itemsAdapterr

        val button = findViewById<Button>(R.id.button)
        val editText = findViewById<EditText>(R.id.editTextText)

        button.setOnClickListener {

            //Verifica se a caixa está vazia
            if(editText.text.isEmpty()){
                editText.error = "Preencha um valor!"

                //indica ao compilador que o return é especificamente pra uma listener do botão
                return@setOnClickListener
            }

            val itemName = editText.text.toString().trim()
                val item = ItemModel(
                    name = itemName,
                    onRemove = {
                        itemsAdapterr.removeItem(it)
                    }
                )
                itemsAdapterr.addItem(item)
            //Limpa a caixa assim que adiciona um item
                editText.text.clear()

            recyclerView.addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(editText.windowToken, 0)
                    }
                }
            }
            )
        }
    }
}