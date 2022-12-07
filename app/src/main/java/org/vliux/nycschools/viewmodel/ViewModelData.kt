package org.vliux.nycschools.viewmodel

data class ViewModelData<out T>(val status: Status, val data: T?, val error: Exception?) {
    companion object {
        fun <T> success(data: T?): ViewModelData<T> {
            return ViewModelData(Status.SUCCESS, data, null)
        }

        fun <T> error(exception: Exception?): ViewModelData<T> {
            return ViewModelData(Status.ERROR, null, exception)
        }

        fun <T> loading(): ViewModelData<T> {
            return ViewModelData(Status.LOADING, null, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}
