package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    FormularioInicial("Android")
                }
            }
        }
    }


    @Composable
    fun FormularioInicial(name: String, modifier: Modifier = Modifier) {
        var persona by remember { mutableStateOf("") }
        var incidente by remember { mutableStateOf("") }
        var contexto = this

        Column {
            BasicTextField(
                value=persona,
                onValueChange = {persona = it}


            )
            BasicTextField(
                value=incidente,
                onValueChange = {incidente = it}
            )

            Button(onClick = {
                // Write a message to the database
                val database = Firebase.database
                val myRef = database.getReference("Incidentes")

                val reporte: Reporte = Reporte()
                reporte.incidente = incidente
                reporte.persona = persona

                myRef.child(reporte.hashCode().toString()).setValue(reporte)


                var intent: Intent = Intent(contexto, IncidentsActivity::class.java)
                startActivity(intent)
            }) {
                Text(text = "Enviar datos")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun FormularioInicialPreview() {
        MyApplicationTheme {
            FormularioInicial("Android")
        }
    }
}