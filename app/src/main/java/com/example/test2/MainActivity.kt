package com.example.test2

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.renderscript.Sampler.Value
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var convertText: TextView
    lateinit var text1: TextView
    lateinit var editText1: EditText
    lateinit var text2: TextView
    lateinit var editText2: EditText
    lateinit var currencySpinner1: Spinner
    lateinit var currencySpinner2: Spinner
    var items = mutableListOf<Currency>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        convertText = findViewById(R.id.textView3)
        text1 = findViewById(R.id.textView1)
        text2 = findViewById(R.id.textView2)
        editText1 = findViewById(R.id.editText1)
        editText2 = findViewById(R.id.editText2)
        var isUpdating = false


        //1 chuan bi du lieu
        items.add(Currency("USD - US Dollar", "USD" ,1.0, "$"))
        items.add(Currency("EUR - Euro", "EUR",0.9, "€"))
        items.add(Currency("GBP - British Pound","GBP", 0.8, "£"))
        items.add(Currency("JPY - Japanese Yen", "JPY",120.0, "¥"))
        items.add(Currency("AUD - Australian Dollar", "AUD",1.5,"$"))
        items.add(Currency("CAD - Canadian Dollar", "CAD",1.3,"$"))
        items.add(Currency("VND - Vietnamese Dong", "VND",23000.0, "₫"))


        //2 tao adapter
//        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        val adapter = ArrayAdapter(this, R.layout.layout_simple_item,R.id.text_content, items.map { it.name })

        // 3 thiet lap adapter cho danh sach
        currencySpinner1 = findViewById(R.id.spinner1)
        currencySpinner2 = findViewById(R.id.spinner2)
        currencySpinner1.adapter = adapter
        currencySpinner2.adapter = adapter

        //4. xu ly su kien
//        listView.onItemClickListener = object : AdapterView.OnItemClickListener{
//            override fun onItemClick(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                Toast.makeText(this@MainActivity, "Clicked item $position", Toast.LENGTH_SHORT).show()
//            }
//        }

        currencySpinner1.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@MainActivity,"Clicked item $position", Toast.LENGTH_SHORT).show()
                text1.setText(items[position].symbol)
                updateFromSpinner1()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        currencySpinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@MainActivity,"Clicked item $position", Toast.LENGTH_SHORT).show()
                text2.setText(items[position].symbol)
                updateFromSpinner2()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        editText1.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(isUpdating) return
                isUpdating = true
                updateEditText1()
                isUpdating = false
            }

            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

        })

        editText2.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(isUpdating) return
                val text = s.toString()
                isUpdating = true
                updateEditText2()
                isUpdating = false
            }

            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

        })




//        val adapter2 = ArrayAdapter(
//            this,
//            android.R.layout.simple_list_item_1,
//            items
//        )
//        val spinner = findViewById<Spinner>(R.id.spinner1)
//        spinner.adapter = adapter2
//
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                Toast.makeText(this@MainActivity, "Clicked item $position", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//        }
//        spinner.setSelection(8)

    }
    fun formatNumber(value: Double): String{
        val formatter = DecimalFormat("#.##")
        return formatter.format(value)
    }

    fun check() = editText1.text.isNotEmpty() && editText1.text.toString().toDoubleOrNull() != null
            && editText2.text.isNotEmpty() && editText2.text.toString().toDoubleOrNull() != null

    fun updateConvertText(){
        convertText.setText("1 ${items[currencySpinner1.selectedItemPosition].shortName} = ${items[currencySpinner2.selectedItemPosition].value/items[currencySpinner1.selectedItemPosition].value} ${items[currencySpinner2.selectedItemPosition].shortName} ")
    }

    fun updateFromSpinner1(){
        updateConvertText()
        if(check())  {
            editText1.setText(formatNumber(editText2.text.toString().toDouble()
                    *items[currencySpinner1.selectedItemPosition].value/items[currencySpinner2.selectedItemPosition].value))
        }

    }
    fun updateFromSpinner2(){
        updateConvertText()
        if(check()){
            editText2.setText(formatNumber(editText1.text.toString().toDouble()
                    *items[currencySpinner2.selectedItemPosition].value/items[currencySpinner1.selectedItemPosition].value))
        }

    }

    fun updateEditText1(){
        if(check())  {
            editText2.setText(formatNumber(editText1.text.toString().toDouble()
                    *items[currencySpinner2.selectedItemPosition].value/items[currencySpinner1.selectedItemPosition].value))
        }
    }

    fun updateEditText2(){
        if(check())  {
            editText1.setText(formatNumber(editText2.text.toString().toDouble()
                    *items[currencySpinner1.selectedItemPosition].value/items[currencySpinner2.selectedItemPosition].value))
        }
    }
}