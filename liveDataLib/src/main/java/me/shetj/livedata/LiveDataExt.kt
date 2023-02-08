package me.shetj.livedata

import android.os.Looper
import androidx.core.os.HandlerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import java.util.concurrent.atomic.AtomicBoolean

/****************************************** LiveData ***************************************/
/**
 * Throttle last :间隔固定时间内，取最后一个
 *
 * @param duration
 */
fun <T> LiveData<T>.throttleLast(duration: Long = 1000L) = MediatorLiveData<T>().also { mld ->
    val source = this
    val handler = HandlerCompat.createAsync(Looper.getMainLooper())
    val isUpdate = AtomicBoolean(true) // 用来通知发送delay

    val runnable = Runnable {
        if (isUpdate.compareAndSet(false, true)) {
            mld.value = source.value
        }
    }

    mld.addSource(source) {
        if (isUpdate.compareAndSet(true, false)) {
            handler.postDelayed(runnable, duration)
        }
    }
}

/**
 * Throttle first 间隔固定时间内，取第一个的值
 *
 * @param T
 * @param duration
 */
fun <T> LiveData<T>.throttleFirst(duration: Long = 1000L) = MediatorLiveData<T>().also { mld ->
    val source = this
    val handler = HandlerCompat.createAsync(Looper.getMainLooper())
    val isUpdate = AtomicBoolean(true)

    val runnable = Runnable {
        isUpdate.set(true)
    }

    mld.addSource(source) {
        if (isUpdate.compareAndSet(true, false)) {
            mld.value = source.value
            handler.postDelayed(runnable, duration)
        }
    }
}

/**
 * Throttle latest : 一定时间内，最新的值
 *
 * @param T
 * @param duration
 */
fun <T> LiveData<T>.throttleLatest(duration: Long = 1000L) = MediatorLiveData<T>().also { mld ->

    val isLatest = AtomicBoolean(true)
    val source = this
    val handler = HandlerCompat.createAsync(Looper.getMainLooper())
    val isUpdate = AtomicBoolean(true)

    val runnable = Runnable {
        if (isUpdate.compareAndSet(false, true)) {
            mld.value = source.value
        }
    }

    mld.addSource(source) {
        if (isLatest.compareAndSet(true, false)) {
            mld.value = source.value
        }
        if (isUpdate.compareAndSet(true, false)) {
            handler.postDelayed(runnable, duration)
        }
    }
}

/**
 * Debounce 2个值之间必须间隔，固定时间
 *
 * @param T
 * @param duration
 */
fun <T> LiveData<T>.debounce(duration: Long = 1000L) = MediatorLiveData<T>().also { mld ->
    val source = this
    val handler = HandlerCompat.createAsync(Looper.getMainLooper())

    val runnable = Runnable {
        mld.value = source.value
    }

    mld.addSource(source) {
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, duration)
    }
}

fun LiveData<Boolean>.isTrue() = value == true
