package es.fpsumma.dam2.utilidades.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "asignaturas")
data class asignaturas(

    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,


    @ColumnInfo(name = "asignatuara")
    val asignatura: String,


     @ColumnInfo(name = "descripcion")
     val descripcion: String,

    @ColumnInfo(name = "nota", defaultValue = "1")
    val nota: Int = 1,

    @ColumnInfo(name = "categoria")
    val categoria: String? = null

    )

