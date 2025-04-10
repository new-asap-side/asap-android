package asap.aljyo.core.event

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async


// 데이터 갱신, 서버 데이터 동기화 등 network 관련 처리 필요 이벤트
sealed class Network<in I, out R>(
    open val scope: CoroutineScope,
    open val block: suspend (I) -> Boolean
): Event<I, Deferred<R>>

data class Patch(
    override val scope: CoroutineScope,
    override val block: suspend (Unit) -> Boolean
): Network<Unit, Boolean>(scope, block) {
    override suspend fun process(input: Unit) = scope.async {
        block(input)
    }
}