package eti.com.abellopes.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


open class Event<out T>(val data: T) {

    var hasBeenHandled = false
        protected set

    fun trigger(body: Event<T>.() -> Unit) {
        if (!hasBeenHandled) {
            hasBeenHandled = true
            body(this)
        }
    }
}

fun <T : Event<*>> LiveData<T>.subscribe(owner: LifecycleOwner, body: T.() -> Unit) {
    observe(owner, Observer {
        it?.trigger { body(it) }
    })
}