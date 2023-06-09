package com.nodil.diplom.data.repositories

import android.util.Log
import com.google.gson.JsonObject
import com.nodil.diplom.data.model.socket.NewTaskSocketModel
import com.nodil.diplom.domain.models.TaskModel
import com.nodil.diplom.domain.repositories.TaskRepository
import com.pusher.client.channel.ChannelEventListener
import com.pusher.client.channel.PusherEvent
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class TaskRepositoryApi: SocketRepository("task/"), TaskRepository{
    override suspend fun getMyTasks(): Array<TaskModel> {
        val response = client.get("myTasks")
        println(response.bodyAsText())
        return response.body()
    }

    override fun subscribe(myId: Int, onNewTask: (TaskModel) -> Unit) {
        val sub = pusher.subscribe(
            "workerTask.$myId",
            object : ChannelEventListener {
                override fun onEvent(event: PusherEvent?) {
                }

                override fun onSubscriptionSucceeded(p0: String?) {
                    Log.e("AUTH SUCCESS", p0.toString())
                }
            }
        )
        sub.bind("NewTask") {
            onNewTask(gsonConverter.fromJson(it?.data, NewTaskSocketModel::class.java).task)
            Log.e("Messages event", it?.data.toString())
        }
    }
    override fun subscribeNotify(myId: Int, onNotify: (String) -> Unit) {
        val sub = pusher.subscribe(
            "workerNotify.$myId",
            object : ChannelEventListener {
                override fun onEvent(event: PusherEvent?) {
                }

                override fun onSubscriptionSucceeded(p0: String?) {
                    Log.e("AUTH workerNotify", p0.toString())
                }
            }
        )
        sub.bind("WorkerNotify") {
            onNotify(gsonConverter.fromJson(it?.data, Map::class.java)["message"].toString())
            Log.e("Messages event", it?.data.toString())
        }
    }
}