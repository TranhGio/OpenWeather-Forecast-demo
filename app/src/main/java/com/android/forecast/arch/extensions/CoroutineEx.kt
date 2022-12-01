package com.android.forecast.arch.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

fun <T1, T2> CoroutineScope.combineFlows(
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    collectBlock: (suspend (T1, T2) -> Unit)
) {
    launch {
        flow1.combine(flow2) { v1, v2 ->
            collectBlock.invoke(v1, v2)
        }.collect {
            // Empty collect block to trigger ^
        }
    }
}

fun <T1, T2, T3> CoroutineScope.combineFlows(
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    collectBlock: (suspend (T1, T2, T3) -> Unit)
) {
    launch {
        combine(flow1, flow2, flow3) { v1, v2, v3 ->
            collectBlock.invoke(v1, v2, v3)
        }.collect {
            // Empty collect block to trigger ^
        }
    }
}
