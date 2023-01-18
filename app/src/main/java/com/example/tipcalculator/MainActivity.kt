package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Switch
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
    initialTipPercent: String = "0",
    shouldRoundUp: Boolean = true
) {
    var billAmount by remember {
        mutableStateOf(initialBill)
    }
    var tipPercent by remember {
        mutableStateOf(initialTipPercent)
    }
    var roundUp by remember {
        mutableStateOf(shouldRoundUp)
    }

    val tip = calculateTip(
        billAmount = billAmount,
        tipPercent = tipPercent,
        roundUp = roundUp
    )

    Column(
        Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(text = "Calculate Tip", modifier = Modifier.align(Alignment.CenterHorizontally))

        EditNumberField(
            value = billAmount,
            hint = "Enter Bill",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onValueChanged = { billAmount = it }
        )
        EditNumberField(
            value = tipPercent,
            hint = "Enter Tip Percent",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onValueChanged = { tipPercent = it }
        )
        RoundTheTipRow(roundUp, onRoundUpChanged = { roundUp = it })
        DisplayTip(tip = tip, modifier = Modifier.align(Alignment.CenterHorizontally))
    }

}

@Composable
fun EditNumberField(
    value: String,
    hint: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(hint) },
        modifier = modifier
    )
}

@Composable
fun DisplayTip(tip: String, modifier: Modifier = Modifier) {
    Text(text = "Tip Amount is $tip", modifier = modifier)
}

@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Round The Tip", modifier = modifier.wrapContentWidth(Alignment.Start))
        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChanged,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }
}

fun calculateTip(billAmount: String, tipPercent: String, roundUp: Boolean): String {
    val tipPercentage = tipPercent.toDoubleOrNull() ?: 0.0
    val bill = billAmount.toDoubleOrNull() ?: 0.0
    val tip = tipPercentage / 100 * bill

    return if (roundUp) {
        kotlin.math.ceil(tip).toString()
    } else
        tip.toString()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen("0", "0")
    }
}