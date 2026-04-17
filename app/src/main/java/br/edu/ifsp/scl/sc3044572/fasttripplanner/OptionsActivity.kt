package br.edu.ifsp.scl.sc3044572.fasttripplanner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.ifsp.scl.sc3044572.fasttripplanner.ui.theme.FastTripPlannerTheme

class OptionsActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val destination = intent.getStringExtra("EXTRA_DESTINATION") ?: ""
        val days = intent.getIntExtra("EXTRA_DAYS", 0)
        val budget = intent.getDoubleExtra("EXTRA_BUDGET", 0.0)

        setContent {
            FastTripPlannerTheme() {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    OptionsLayout(
                        modifier = Modifier.padding(innerPadding),
                        dest = destination,
                        days = days,
                        budget = budget
                    )
                }
            }
        }
    }
}

@Composable
fun OptionsLayout(modifier: Modifier = Modifier, dest: String, days: Int, budget: Double){
    val context = LocalContext.current

    val hotelOptions = listOf("Econômica", "Conforto", "Luxo")
    var selectedHotel by remember { mutableStateOf(hotelOptions[0]) }

    var hasTransport by remember { mutableStateOf(false) }
    var hasFood by remember { mutableStateOf(false) }
    var hasTours by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Opções para: $dest",
            style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Tipo de Hospedagem:", style = MaterialTheme.typography.titleMedium)
        hotelOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedHotel),
                        onClick = { selectedHotel = text },
                        role = Role.RadioButton
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = (text == selectedHotel), onClick = null)
                Text(text = text, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Serviços Adicionais:", style = MaterialTheme.typography.titleMedium)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = hasTransport, onCheckedChange = { hasTransport = it })
            Text("Transporte (R$ 300)")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = hasFood, onCheckedChange = { hasFood = it })
            Text("Alimentação (R$ 50/dia)")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = hasTours, onCheckedChange = { hasTours = it })
            Text("Passeios (R$ 120/dia)")
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = { (context as Activity).finish() }
            ) {
                Text("Voltar")
            }

            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    val intent = Intent(context, SummaryActivity::class.java).apply {
                        putExtra("EXTRA_DESTINATION", dest)
                        putExtra("EXTRA_DAYS", days)
                        putExtra("EXTRA_BUDGET", budget)

                        putExtra("EXTRA_HOTEL", selectedHotel)
                        putExtra("EXTRA_TRANSPORT", hasTransport)
                        putExtra("EXTRA_FOOD", hasFood)
                        putExtra("EXTRA_TOURS", hasTours)
                    }
                    context.startActivity(intent)
                }
            ) {
                Text("Calcular")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun OptionsLayoutPreview(){
    OptionsLayout(
        modifier = Modifier.fillMaxSize(),
        dest = "Suiça",
        days = 5,
        budget = 2500.0
    )
}