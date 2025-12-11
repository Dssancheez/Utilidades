package es.fpsumma.dam2.utilidades.ui.screens.asignaturas

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import es.fpsumma.dam2.utilidades.model.Asignatura
import es.fpsumma.dam2.utilidades.ui.viewmodel.AsignaturaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListadoAsignaturasScreen(
    navController: NavController,
    vm: AsignaturaViewModel,
    modifier: Modifier = Modifier
) {
    val asignaturas by vm.asignatura.collectAsState()

    var nombre by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var nota by rememberSaveable { mutableStateOf(1.0) }
    var categoria by rememberSaveable { mutableStateOf("") }

    fun handleAddAsignatura() {
        vm.addAsignatura(nombre, descripcion, nota, categoria)
        nombre = ""
        descripcion = ""
        nota = 1.0
        categoria = ""
    }

    fun handleDeleteAsignatura(asignatura: Asignatura) {
        vm.deleteAsignatura(asignatura)
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Listado de Asignaturas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = androidx.compose.ui.Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre de la asignatura") },
                singleLine = true,
                modifier = androidx.compose.ui.Modifier.fillMaxWidth()
            )
            Spacer(androidx.compose.ui.Modifier.height(8.dp))
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                singleLine = false,
                modifier = androidx.compose.ui.Modifier.fillMaxWidth()
            )
            Spacer(androidx.compose.ui.Modifier.height(8.dp))

            OutlinedTextField(
                value = nota.toString(),

                onValueChange = { newValue ->
                    val newDouble = newValue.toDoubleOrNull()

                    if (newDouble != null) {
                        nota = newDouble
                    } else if (newValue.isEmpty()) {
                        nota = 1.0
                    }
                },

                label = { Text("Nota") },
                singleLine = true,

                modifier = androidx.compose.ui.Modifier.fillMaxWidth()
            )
            Spacer(androidx.compose.ui.Modifier.height(8.dp))
            OutlinedTextField(
                value = categoria,
                onValueChange = { categoria = it },
                label = { Text("categoria") },
                singleLine = false,
                modifier = androidx.compose.ui.Modifier.fillMaxWidth()
            )
            Spacer(androidx.compose.ui.Modifier.height(8.dp))
            Button(
                onClick = ::handleAddAsignatura,
                modifier = androidx.compose.ui.Modifier.fillMaxWidth()
            ) { Text("Añadir asignatura") }
            HorizontalDivider(modifier.padding(vertical = 16.dp))


            LazyColumn (
                modifier = modifier
                    .fillMaxSize()
                    .padding((16.dp))
            ){
                items(
                   items = asignaturas,
                    key = {it.id}
                ){ asignatura ->
                    Card (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)

                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary
                            ),
                        shape = RoundedCornerShape(16.dp)
                    ){
                        ListItem(
                            headlineContent = { Text (asignatura.nombre) },

                            supportingContent = {
                                Column {
                                    Text("Descripción: ${asignatura.descripcion}")
                                    Text("Categoría: ${asignatura.categoria}")
                                }
                            },

                            trailingContent = {
                                Text(
                                    "Nota: ${asignatura.nota.toString()}",

                                )



                                IconButton(
                                    onClick = {handleDeleteAsignatura(asignatura)},
                                    modifier = modifier.size(48.dp).padding(top = 10.dp)
                                )

                                {

                                    Icon(
                                        imageVector = Icons.Outlined.Delete,

                                        contentDescription = "Borrar nota"
                                    )
                                }
                            }
                        )
                    }

                }
            }


        }
    }
}