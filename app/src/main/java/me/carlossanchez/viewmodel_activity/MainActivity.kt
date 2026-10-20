package me.carlossanchez.viewmodel_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val vm: CounterViewModel = viewModel()   // UN solo ViewModel
                val nav = rememberNavController()
                NavHost(nav, startDestination = "vista1") {
                    composable("vista1") {
                        Vista1(vm, onNext = { nav.navigate("vista2") })
                    }
                    composable("vista2") {
                        Vista2(vm, onBack = { nav.popBackStack() })
                    }
                }
            }
        }
    }
}

@Composable
fun Vista1(vm: CounterViewModel, onNext: () -> Unit) {
    val count by vm.count.collectAsState()
    Column(
        Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("¿Qué es un ViewModel?", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text("Es una clase que guarda y gestiona los datos de la pantalla. " +
                "Sobrevive a cambios de configuración como rotar el celular. " +
                "Prueba: suma y gira la pantalla.")
        Spacer(Modifier.height(24.dp))
        Text("Contador: $count", style = MaterialTheme.typography.displayMedium)
        Spacer(Modifier.height(16.dp))
        Button(onClick = { vm.increment() }) { Text("Sumar") }
        Spacer(Modifier.height(8.dp))
        OutlinedButton(onClick = onNext) { Text("Ir a la vista 2") }
    }
}

@Composable
fun Vista2(vm: CounterViewModel, onBack: () -> Unit) {
    val count by vm.count.collectAsState()
    Column(
        Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Vista 2", style = MaterialTheme.typography.headlineSmall)
        Text("Esta vista usa el MISMO ViewModel, por eso ve el MISMO valor:")
        Spacer(Modifier.height(16.dp))
        Text("$count", style = MaterialTheme.typography.displayLarge)
        Spacer(Modifier.height(16.dp))
        Button(onClick = onBack) { Text("Volver") }
    }
}