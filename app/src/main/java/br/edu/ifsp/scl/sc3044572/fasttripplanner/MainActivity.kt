package br.edu.ifsp.scl.sc3044572.fasttripplanner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.ifsp.scl.sc3044572.fasttripplanner.ui.theme.FastTripPlannerTheme
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FastTripPlannerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FastTripPlannerLayout(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun FastTripPlannerLayout(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var destination by remember { mutableStateOf("") }
    var days by remember { mutableStateOf("") }
    var dailyBudget by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Planeje sua Viagem",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        TextField(
            value = destination,
            onValueChange = { destination = it },
            label = { Text("Destino") },
            modifier = Modifier.fillMaxWidth(),
            isError = showError && destination.isBlank(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = days,
            onValueChange = { newValue ->
                if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                    days = newValue
                }
            },
            label = { Text("Número de Dias") },
            modifier = Modifier.fillMaxWidth(),
            isError = showError && days.toIntOrNull() == null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = dailyBudget,
            onValueChange = { newValue ->
                if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                    dailyBudget = newValue
                }
            },
            label = { Text("Orçamento Diário (R$)") },
            modifier = Modifier.fillMaxWidth(),
            isError = showError && dailyBudget.toDoubleOrNull() == null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            )
        )

        if (showError) {
            Text(
                text = "Por favor, preencha todos os campos corretamente.",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val parsedDays = days.toIntOrNull()
                val parsedBudget = dailyBudget.toDoubleOrNull()

                if (destination.isNotBlank() && parsedDays != null && parsedDays > 0 && parsedBudget != null && parsedBudget > 0) {
                    showError = false

                    val intent = Intent(context, OptionsActivity::class.java).apply {
                        putExtra("EXTRA_DESTINATION", destination)
                        putExtra("EXTRA_DAYS", parsedDays)
                        putExtra("EXTRA_BUDGET", parsedBudget)
                    }
                    context.startActivity(intent)
                } else showError = true
            }
        ) {
            Text("Avançar para Opções")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FastTripPlannerLayout(modifier = Modifier.fillMaxSize())
}