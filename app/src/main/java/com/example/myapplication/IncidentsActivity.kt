package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class IncidentsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MostrarIncidentes("Android")
                }
            }
        }
    }
}
@Composable
fun MostrarListaDeIncidentes(listaDeDatos: List<Reporte>){
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(listaDeDatos){ reporte ->
            Text(text = reporte.persona + " -> " + reporte.incidente)
        }
    }
}
@Composable
fun MostrarIncidentes(name: String, modifier: Modifier = Modifier) {
    val database = Firebase.database
    val myRef = database.getReference("Incidentes")
    var listaDeDatos by remember { mutableStateOf(emptyList<Reporte>()) }

// Read from the database
    myRef.addValueEventListener(object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            val value = snapshot.children
            var listaDeReportes = mutableListOf<Reporte>()
            value.forEach {
                val objetoCompleto = it
                val incidente = objetoCompleto.getValue(Reporte::class.java)
                listaDeReportes.add(incidente!!)
                Log.d("incidentes", "cada dato: " + incidente?.persona + " " + incidente?.incidente)
            }
            listaDeDatos = listaDeReportes

        }

        override fun onCancelled(error: DatabaseError) {
            Log.w("incidentes", "Failed to read value.", error.toException())
        }

    })
    MostrarListaDeIncidentes(listaDeDatos)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        MostrarIncidentes("Android")
    }
}