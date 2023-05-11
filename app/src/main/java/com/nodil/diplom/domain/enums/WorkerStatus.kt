package com.nodil.diplom.domain.enums

enum class WorkerStatus(val value: Int) {
    NOT_WORKING(0),
    WORKING(1),
    GO_TO_TASK(2),
    DO_TASK(3),
    BREAK(4);
    companion object{
        fun getStatusString(status: Int): String {
            return arrayOf(
                "Не на работе",
                "Без задания",
                "Еду на задание",
                "Выполняю задание",
                "Перерыв",
            )[status]
        }
        fun fromInt(value: Int) = WorkerStatus.values().first { it.value == value }


    }

}