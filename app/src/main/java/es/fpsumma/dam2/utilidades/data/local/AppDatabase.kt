package es.fpsumma.dam2.utilidades.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.fpsumma.dam2.utilidades.model.Asignatura
import es.fpsumma.dam2.utilidades.model.Tarea

// Esto le dice a Room: “esta es mi base de datos”
// - entities: qué tablas tendrá (aquí, la tabla Tarea)
// - version: versión del esquema (si cambias campos/tabla, suele subir)
// - exportSchema=false: no guarda el “historial” del esquema en archivos

@Database(entities = [Tarea::class, Asignatura::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Room necesita que le digamos qué DAO vamos a usar para acceder a la BD
    // (el DAO contiene métodos como insertar, borrar, consultar, etc.)
    abstract fun tareaDao(): TareaDao
    abstract fun asignaturaDao(): AsignaturaDao

    companion object {
        // Guardamos aquí la única instancia de la BD (para no crear 20 bases de datos)
        @Volatile
        private var Instance: AppDatabase? = null

        // Función para obtener la BD desde cualquier parte de la app
        fun getDatabase(context: Context): AppDatabase {

            // Si ya existe, la devolvemos.
            // Si no existe, la creamos (una sola vez).
            return Instance ?: synchronized(this) {

                // synchronized: evita que 2 hilos creen la BD a la vez
                Room.databaseBuilder(
                                context,
                                AppDatabase::class.java,
                                "utilidades" // nombre del archivo de la base de datos en el móvil
                            ).fallbackToDestructiveMigration(false)
                    .build()
                    .also { Instance = it } // guardamos la BD creada para reutilizarla
            }
        }
    }
}