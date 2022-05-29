package ba.unsa.etf.cineaste.data

import androidx.room.*

@Dao
interface CastDao {
    @Insert
    suspend fun insertAll(vararg cast: Cast)

    @Transaction
    @Delete
    suspend fun deleteCast(cast: List<Cast>)
}