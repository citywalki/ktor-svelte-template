package pro.walkin.memos.domain

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.ThreadContextElement

public typealias BizContextMap = Map<String, String>?


class RouteContext : ThreadContextElement<BizContextMap>, AbstractCoroutineContextElement(Key) {

    public companion object Key : CoroutineContext.Key<RouteContext>

    override fun restoreThreadContext(context: CoroutineContext, oldState: BizContextMap) {
        TODO("Not yet implemented")
    }

    override fun updateThreadContext(context: CoroutineContext): BizContextMap {
        TODO("Not yet implemented")
    }

}
