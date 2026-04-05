package com.example.fakestore.core.di.network

import android.content.Context
import androidx.room.Room
import com.example.fakestore.core.data.local.DatabasePassphrase
import com.example.fakestore.core.data.local.db.AppDatabase
import com.example.fakestore.core.data.local.db.CartDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabasePassphrase(
        @ApplicationContext context: Context
    ): DatabasePassphrase = DatabasePassphrase(context)

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        passphrase: DatabasePassphrase
    ): AppDatabase {
        val factory = SupportFactory(passphrase.getPassphrase())

        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "fakestore_db"
        )
            .openHelperFactory(factory)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCartDao(db: AppDatabase): CartDao = db.cartDao()
}
