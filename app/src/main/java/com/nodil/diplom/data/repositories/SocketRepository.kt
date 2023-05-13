package com.nodil.diplom.data.repositories

import com.nodil.diplom.Config
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange

abstract class SocketRepository(path: String) : BaseRepository(path) {

    companion object {
        @JvmStatic
        protected val pusher by lazy { createPusher() }


        private fun createPusher(): Pusher {
            val options = PusherOptions()
            options.setHost(Config.SOCKET_URL)
            options.setWsPort(80)
            options.setWssPort(443)
            val pusher = Pusher(Config.PUSHER_KEY, options)

            pusher.connect(object : ConnectionEventListener {
                override fun onConnectionStateChange(change: ConnectionStateChange) {
                    println(
                        "State changed to " + change.currentState +
                                " from " + change.previousState
                    )
                }

                override fun onError(message: String?, code: String?, e: Exception?) {
                    println("There was a problem connecting!")
                    println(message)
                    println(code)
                    println(e)
                }
            }, ConnectionState.ALL)
            return pusher
        }
    }

}