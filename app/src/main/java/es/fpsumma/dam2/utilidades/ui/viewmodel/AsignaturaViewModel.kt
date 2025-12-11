package es.fpsumma.dam2.utilidades.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import es.fpsumma.dam2.utilidades.data.local.AppDatabase
import es.fpsumma.dam2.utilidades.model.Asignatura
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class AsignaturaViewModel (app: Application) : AndroidViewModel(app) {

    private val dao = AppDatabase.getDatabase(app.applicationContext).asignaturaDao()

    val asignatura: StateFlow<List<Asignatura>> =
        dao.getAllAsignatura().stateIn(
            viewModelScope,
            SharingStarted.Lazily,
                emptyList()

        )

    fun addAsignatura (nombre: String, descripcion: String, nota: Double, categoria: String?
    )
    = viewModelScope.launch {

        dao.insertAsignatura(
            Asignatura(
                nombre = nombre, descripcion = descripcion, nota = nota, categoria = categoria
            )
        )

    }

    fun deleteAsignatura(asignatura: Asignatura) = viewModelScope.launch {
        dao.deleteAsignatura(asignatura)
    }

}