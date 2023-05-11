package com.nodil.diplom.domain.models

data class TaskModel(
    val name: String,
    val description: String,
    val address: String,
    val customer: String,
    val taskType: Array<Int>,
    val latitude: Float,
    val longitude: Float,
    val state: Int,
    val expireAt: String,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val finishedAt: String? = null,
    val parentTask: TaskModel? = null,
    val id: Int? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TaskModel

        if (name != other.name) return false
        if (description != other.description) return false
        if (address != other.address) return false
        if (customer != other.customer) return false
        if (!taskType.contentEquals(other.taskType)) return false
        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false
        if (state != other.state) return false
        if (expireAt != other.expireAt) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false
        if (finishedAt != other.finishedAt) return false
        if (parentTask != other.parentTask) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + customer.hashCode()
        result = 31 * result + taskType.contentHashCode()
        result = 31 * result + latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + state
        result = 31 * result + expireAt.hashCode()
        result = 31 * result + (createdAt?.hashCode() ?: 0)
        result = 31 * result + (updatedAt?.hashCode() ?: 0)
        result = 31 * result + (finishedAt?.hashCode() ?: 0)
        result = 31 * result + (parentTask?.hashCode() ?: 0)
        result = 31 * result + (id ?: 0)
        return result
    }
}