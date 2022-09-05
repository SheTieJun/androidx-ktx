/*
 * MIT License
 *
 * Copyright (c) 2022 SheTieJun
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package me.shetj.datastore

import android.content.Context
import androidx.annotation.NonNull
import androidx.datastore.core.DataStore
import androidx.datastore.migrations.SharedPreferencesMigration
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

fun Context.dataStoreKit(): DataStoreKit {
    return DataStoreKit(this)
}

class DataStoreKit(
    private val context: Context,
    name: String = "_DataStore_Kit",
    private val oldSp: List<String> = listOf()
) {

    private val Context.myDataStore by preferencesDataStore(
        name = name,
        produceMigrations = { context -> oldSp.map { context.getSPByName(it) } }
    )

    val dataStore: DataStore<Preferences> by lazy { context.myDataStore }

    /**
     * Save 保存
     *
     * @param T [Type]
     * @param key
     * @param value
     * @return
     */
    @Suppress("UNCHECKED_CAST")
    suspend inline fun <reified T : Any> save(@NonNull key: String, @NonNull value: T): Boolean {
        try {
            dataStore.edit {
                when (T::class) {
                    Int::class -> {
                        it[intPreferencesKey(key)] = value as Int
                    }
                    Double::class -> {
                        it[doublePreferencesKey(key)] = value as Double
                    }
                    String::class -> {
                        it[stringPreferencesKey(key)] = value as String
                    }
                    Boolean::class -> {
                        it[booleanPreferencesKey(key)] = value as Boolean
                    }
                    Float::class -> {
                        it[floatPreferencesKey(key)] = value as Float
                    }
                    Long::class -> {
                        it[longPreferencesKey(key)] = value as Long
                    }
                    Set::class -> {
                        it[stringSetPreferencesKey(key)] = value as Set<String>
                    }
                    else -> {
                        throw IllegalArgumentException(" Can't handle 'value' ")
                    }
                }
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * Get Flow by Preferences.Key
     *
     * @param T
     * @param key
     * @return
     */
    @Suppress("UNCHECKED_CAST")
    inline fun <reified T : Any> get(@NonNull key: String, @NonNull defaultValue: T): Flow<T> {
        val data = dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            when (T::class) {
                Int::class -> {
                    it[intPreferencesKey(key)]
                }
                Double::class -> {
                    it[doublePreferencesKey(key)]
                }
                String::class -> {
                    it[stringPreferencesKey(key)]
                }
                Boolean::class -> {
                    it[booleanPreferencesKey(key)]
                }
                Float::class -> {
                    it[floatPreferencesKey(key)]
                }
                Long::class -> {
                    it[longPreferencesKey(key)]
                }
                Set::class -> {
                    it[stringSetPreferencesKey(key)]
                }
                else -> {
                    null
                }
            } ?: defaultValue
        }
        return data as Flow<T>
    }

    /**
     * Get Flow by Preferences.Key
     *
     * @param T
     * @param key
     * @return
     */
    @Suppress("UNCHECKED_CAST")
    inline fun <reified T : Any> get(@NonNull key: String): Flow<T?> {
        val data = dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            when (T::class) {
                Int::class -> {
                    it[intPreferencesKey(key)]
                }
                Double::class -> {
                    it[doublePreferencesKey(key)]
                }
                String::class -> {
                    it[stringPreferencesKey(key)]
                }
                Boolean::class -> {
                    it[booleanPreferencesKey(key)]
                }
                Float::class -> {
                    it[floatPreferencesKey(key)]
                }
                Long::class -> {
                    it[longPreferencesKey(key)]
                }
                Set::class -> {
                    it[stringSetPreferencesKey(key)]
                }
                else -> {
                    null
                }
            }
        }
        return data as Flow<T?>
    }

    suspend inline fun <reified T : Any> getFirst(@NonNull key: String, @NonNull defaultValue: T): T {
        var resultValue = defaultValue
        dataStore.data.first {
            resultValue = (
                when (T::class) {
                    Int::class -> {
                        it[intPreferencesKey(key)]
                    }
                    Double::class -> {
                        it[doublePreferencesKey(key)]
                    }
                    String::class -> {
                        it[stringPreferencesKey(key)]
                    }
                    Boolean::class -> {
                        it[booleanPreferencesKey(key)]
                    }
                    Float::class -> {
                        it[floatPreferencesKey(key)]
                    }
                    Long::class -> {
                        it[longPreferencesKey(key)]
                    }
                    Set::class -> {
                        it[stringSetPreferencesKey(key)]
                    }
                    else -> {
                        null
                    }
                } ?: defaultValue
                ) as T
            true
        }
        return resultValue
    }

    /**
     * Remove 移除单个key的值
     *
     * @param T
     * @param key
     * @return
     */
    suspend inline fun <reified T : Any> remove(@NonNull key: String): Boolean {
        try {
            dataStore.edit {
                when (T::class) {
                    Int::class -> {
                        it.remove(intPreferencesKey(key))
                    }
                    Double::class -> {
                        it.remove(doublePreferencesKey(key))
                    }
                    String::class -> {
                        it.remove(stringPreferencesKey(key))
                    }
                    Boolean::class -> {
                        it.remove(booleanPreferencesKey(key))
                    }
                    Float::class -> {
                        it.remove(floatPreferencesKey(key))
                    }
                    Long::class -> {
                        it.remove(longPreferencesKey(key))
                    }
                    Set::class -> {
                        it.remove(stringSetPreferencesKey(key))
                    }
                    else -> {
                        throw IllegalArgumentException(" Can't remove 'key' ")
                    }
                }
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * Clear 清空
     *
     * @return
     */
    suspend inline fun clear(): Boolean {
        try {
            dataStore.edit {
                it.clear()
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}

/**
 * Get SharedPreferencesMigration by name
 *
 * @param name sp fileName
 * @return
 */
internal fun Context.getSPByName(name: String): SharedPreferencesMigration<Preferences> {
    return SharedPreferencesMigration(this, name)
}
