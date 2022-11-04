package com.yagmurali.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView? = null;

    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false

    }
    fun onClear(view: View) {
        tvInput?.setText("")
    }

    fun onDecimalPoint(view: View) {
        // if (tvInput?.text?.contains(".") == false) {
            if (lastNumeric && !lastDot) {
                tvInput?.append(".")
                lastNumeric = false
                lastDot = true
            }
        // }
    }

    fun onOperator(view : View) {
        tvInput?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun isOperatorAdded(value: String) : Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") ||
                    value.contains("*") ||
                    value.contains("+") ||
                    value.contains("-")

        }

    }

    fun removeZeroAfterDot(result:String) : String {
        var value = result
        if (result.contains(".0"))
            value = result.substring(0,result.length -2)
        return value
    }

    fun onEquals(view : View) {
        val mgr: ScriptEngineManager = ScriptEngineManager()
        val engine: ScriptEngine = mgr.getEngineByName("rhino")
        println(tvInput?.text.toString())
        tvInput?.setText(
            removeZeroAfterDot(
                engine.eval(
                    tvInput?.text.toString()
                    ).toString()
                )
        )

    }

}