package br.edu.ifsp.scl.sc3044572.fasttripplanner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import br.edu.ifsp.scl.sc3044572.fasttripplanner.ui.theme.FastTripPlannerTheme
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class SummaryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val destination = intent.getStringExtra("EXTRA_DESTINATION") ?: "Desconhecido"
        val days = intent.getIntExtra("EXTRA_DAYS", 0)
        val budget = intent.getDoubleExtra("EXTRA_BUDGET", 0.0)

        val hotelType = intent.getStringExtra("EXTRA_HOTEL") ?: "Econômica"
        val hasTransport = intent.getBooleanExtra("EXTRA_TRANSPORT", false)
        val hasFood = intent.getBooleanExtra("EXTRA_FOOD", false)
        val hasTours = intent.getBooleanExtra("EXTRA_TOURS", false)

        setContent {
            FastTripPlannerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SummaryLayout(
                        modifier = Modifier.padding(innerPadding),
                        dest = destination,
                        days = days,
                        budget = budget,
                        hotelType = hotelType,
                        hasTransport = hasTransport,
                        hasFood = hasFood,
                        hasTours = hasTours
                    )
                }
            }
        }
    }
}

@Composable
fun SummaryLayout(
    modifier: Modifier = Modifier,
    dest: String,
    days: Int,
    budget: Double,
    hotelType: String,
    hasTransport: Boolean,
    hasFood: Boolean,
    hasTours: Boolean
) {
    val context = LocalContext.current

    val baseCost = days * budget
    val hotelMultiplier = when (hotelType) {
        "Conforto" -> 1.5
        "Luxo" -> 2.2
        else -> 1.0
    }

    val transportCost = if (hasTransport) 300.0 else 0.0
    val foodCost = if (hasFood) 50.0 * days else 0.0
    val toursCost = if (hasTours) 120.0 * days else 0.0

    val totalCost = (baseCost * hotelMultiplier) + transportCost + foodCost + toursCost
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Resumo da Viagem",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Destino: $dest", style = MaterialTheme.typography.bodyLarge)
        Text("Duração: $days dias", style = MaterialTheme.typography.bodyLarge)
        Text("Orçamento Diário: ${currencyFormat.format(budget)}", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(8.dp))

        Text("Hospedagem: $hotelType", style = MaterialTheme.typography.bodyLarge)
        Text(
            text = "Serviços Inclusos: " + listOfNotNull(
                if (hasTransport) "Transporte" else null,
                if (hasFood) "Alimentação" else null,
                if (hasTours) "Passeios" else null
            ).joinToString(", ").ifEmpty { "Nenhum" },
            style = MaterialTheme.typography.bodyLarge
        )

        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Custo Total Estimado", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = currencyFormat.format(totalCost),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val intent = Intent(context, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                }
                context.startActivity(intent)
                (context as Activity).finish()
            }
        ) {
            Text("Reiniciar Planejamento")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SummaryLayoutPreview(){
    SummaryLayout(
        modifier = Modifier.fillMaxSize(),
        dest = "Suiça",
        days = 5,
        budget = 2500.0,
        hotelType = "Conforto",
        hasTransport = true,
        hasFood = true,
        hasTours = true
    )
}