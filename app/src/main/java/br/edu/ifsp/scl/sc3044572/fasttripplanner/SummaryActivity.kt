package br.edu.ifsp.scl.sc3044572.fasttripplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import br.edu.ifsp.scl.sc3044572.fasttripplanner.ui.theme.FastTripPlannerTheme
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.material3.Scaffold
import androidx.compose.ui.tooling.preview.Preview

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