package com.nodil.diplom.data.repositories
import android.content.Context
import android.content.SharedPreferences


class SharedPreferencesStorage(context: Context)  {
    private val prefs = context.getSharedPreferences("databaseInfo", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = prefs.edit()

     fun saveString(key: String, value: String){
        editor.putString(key, value)
        editor.commit()
    }

     fun getString(key: String) : String {
        return prefs.getString(key, "").toString()
    }

     fun contains(key: String) : Boolean {
        return prefs.contains(key)
    }

     fun delete(key: String){
        editor.remove(key)
        editor.commit()
    }
}