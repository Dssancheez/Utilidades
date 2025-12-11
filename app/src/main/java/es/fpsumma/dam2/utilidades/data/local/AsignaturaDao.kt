package es.fpsumma.dam2.utilidades.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import es.fpsumma.dam2.utilidades.model.Asignatura
import es.fpsumma.dam2.utilidades.model.Tarea
import kotlinx.coroutines.flow.Flow
@Dao

interface AsignaturaDao{


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAsignatura (asignaturas: Asignatura)


    @Update
    suspend fun update(asignaturas: Asignatura)

    @Delete
    suspend fun deleteAsignatura(asignaturas: Asignatura)


    @Query("SELECT * from asignaturas WHERE id = :id")
    fun getAsignatura(id: Int): Flow<Asignatura>


    @Query("SELECT * from asignaturas ORDER BY nombre ASC")
    fun getAllAsignatura(): Flow<List<Asignatura>>



    @Query("DELETE FROM asignaturas")
    suspend fun deleteAll()

    @Query("DELETE FROM asignaturas WHERE id = :id")
    suspend fun deleteById(id: Int)

}

