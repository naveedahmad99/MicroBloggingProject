package com.symphony.microblogging.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.symphony.microblogging.data.local.entity.AuthorEntity

@Dao
interface AuthorDao {
    @Query("SELECT * from Author ORDER BY name ASC")
    fun getAllAuthors(): LiveData<List<AuthorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuthors(author: List<AuthorEntity>)
}
