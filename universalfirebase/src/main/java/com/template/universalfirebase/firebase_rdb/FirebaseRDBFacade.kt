package com.template.universalfirebase.firebase_rdb

import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.tasks.await

object FirebaseRDBFacade {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.also { it.keepSynced(true) }

    private val listenerMap = HashMap<String, ChildEventListener>()

    suspend fun <T> getList(topicName: String, clazz: Class<T>): List<IdModel<T>> {
        return databaseReference.child(topicName).get().await().children.map { item ->
            IdModel(item.key!!, item.getValue(clazz)!!)
        }
    }

    suspend fun <T> getItemById(topicName: String, id: String, clazz: Class<T>): IdModel<T>? {
        return databaseReference.child(topicName).child(id).get().await()?.run {
            getValue(clazz)?.let {
                IdModel(id, it)
            }
        }
    }

    fun <T> listenChanges(topicName: String, clazz: Class<T>): Channel<Event<IdModel<T>>> {
        val channel = Channel<Event<IdModel<T>>>(capacity = Channel.UNLIMITED)
        object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                snapshot.key?.let { key ->
                    snapshot.getValue(clazz)?.let { value ->
                        channel.trySend(Event.Added(IdModel(key, value)))
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                snapshot.key?.let { key ->
                    snapshot.getValue(clazz)?.let { value ->
                        channel.trySend(Event.Removed(IdModel(key, value)))
                    }
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }.let {
            listenerMap[topicName] = it
            databaseReference.child(topicName).addChildEventListener(it)
        }
        return channel
    }

    fun stopListenChanges(topicName: String) {
        listenerMap[topicName]?.let {
            databaseReference.child(topicName).removeEventListener(it)
            listenerMap.remove(topicName)
        }
    }

    suspend fun <T> insert(topicName: String, data: T): IdModel<T> {
        return IdModel(databaseReference.child(topicName).push().run {
            setValue(data).await()
            key ?: ""
        }, data)
    }

    suspend fun <T> insert(topicName: String, id: String, data: T): IdModel<T> {
        return IdModel(databaseReference.child(topicName).child(id).run {
            setValue(data).await()
            key ?: ""
        }, data)
    }

    suspend fun <T> update(topicName: String, model: IdModel<T>) {
        databaseReference.child(topicName).child(model.id).setValue(model.data).await()
    }

    suspend fun delete(topicName: String, id: String) {
        databaseReference.child(topicName).child(id).removeValue().await()
    }
}