package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                TipCalculatorScreen()
            }
        }
    }
}

@Composable
fun TipCalculatorScreen(
    initialBill: String = "0",
    initialTipPercent: String = "0"
) {
    var billAmount by remember {
        mutableStateOf(initialBill)
    }
    var tipPercent by remember {
        mutableStateOf(initialTipPercent)
    }

    val tip = calculateTip(billAmount = billAmount, tipPercent = tipPercent)

    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(text = "Calculate Tip")

        EditNumberField(value = billAmount, hint = "Enter Bill") { bill ->
            billAmount = bill
        }
        EditNumberField(value = tipPercent, hint = "Enter Tip Percent") { tip ->
            tipPercent = tip
        }
        DisplayTip(tip = tip)
    }

}

@Composable
fun EditNumberField(value: String, hint: String, onValueChanged: (String) -> Unit) {
    TextField(value = value, onValueChange = onValueChanged, label = { Text(hint) })
}

@Composable
fun DisplayTip(tip: String) {
    Text(text = "Tip Amount is $tip")
}

fun calculateTip(billAmount: String, tipPercent: String): String {
    val tipPercentage = tipPercent.toDoubleOrNull() ?: 0.0
    val bill = billAmount.toDoubleOrNull() ?: 0.0
    val tip = tipPercentage / 100 * bill

    return tip.toString()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen("0", "0")
    }
}