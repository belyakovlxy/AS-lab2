package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import javax.crypto.KeyGenerator

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {
  abstract fun itemDao(): ItemDao
  companion object {
    @Volatile
    private var Instance: InventoryDatabase? = null

    fun getDatabase(context: Context): InventoryDatabase {
      return Instance ?: synchronized(this) {
        Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
          .build()
          .also { Instance = it }
      }
    }
  }

  private fun createDB(context: Context): InventoryDatabase {
    val passphrase = getPassphrase() ?: initializePassphrase(context)
    val factory = InventoryDatabase(passphrase)
    return Room.databaseBuilder(
      context.applicationContext,
      InventoryDatabase::class.java,
      "stock.db"
    )
      .openHelperFactory(factory)
      .fallbackToDestructiveMigration()
      .build()
  }

  private fun getPassphrase(): ByteArray? {
    val passphraseString = Settings.password
    return passphraseString?.toByteArray(Charsets.ISO_8859_1)
  }
  
  private fun generatePassphrase(): ByteArray {
    val keyGenerator = KeyGenerator.getInstance("AES")
    keyGenerator.init(256)
    return keyGenerator.generateKey().encoded
  }

  private fun initializePassphrase(context: Context): ByteArray {
    val passphrase = generatePassphrase()
    Settings.password = passphrase.toString(Charsets.ISO_8859_1)

    return passphrase
  }

}