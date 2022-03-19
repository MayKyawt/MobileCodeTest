package com.mkt.mobilecodetest.data


 class Resource<out R> (val status: DataState, val data: R?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(DataState.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(DataState.ERROR, data, msg)
        }

        fun <T> duplicate(): Resource<T> {
            return Resource(DataState.DUPLICATE,null,null)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(DataState.LOADING, data, null)
        }
    }
}
