package com.app.gdax

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Looper
import okhttp3.*
import java.util.concurrent.TimeUnit

/**
 * Created by mohamed ibrahim on 6/30/2017.
 */

class SocketHelper: LifecycleObserver {

    val parser = MessageParser(db)


    val listener = object : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket?, response: Response?) {
            e("onOpen : " + response?.message())
            webSocket?.send("""{"type": "subscribe" , "product_ids":["ETH-USD"]}""");
        }


        override fun onClosed(webSocket: WebSocket?, code: Int, reason: String?) {
            e("onClosed $code $reason")
        }

        override fun onMessage(webSocket: WebSocket?, text: String?) {
            if (text != null) {
                parser.readMessage(text)
            }
        }

        override fun onClosing(webSocket: WebSocket?, code: Int, reason: String?) {
            e("Close $code $reason")
        }

        override fun onFailure(webSocket: WebSocket?, t: Throwable?, response: Response?) {
            e("FAILURE------------------------------------------------------------")
            e(t)
            e(t?.stackTrace)
            t?.printStackTrace()
            e(response)

        }

    }


    val client = OkHttpClient.Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .build()!!

    val request = Request.Builder()
            .url("wss://ws-feed.gdax.com").build()


    val webSocket = client.newWebSocket(request, listener);


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun shutDown() {
        webSocket.close(1000, "Goodbye!")
        client.dispatcher().executorService().shutdown();

    }

}
