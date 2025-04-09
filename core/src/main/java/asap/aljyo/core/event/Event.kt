package asap.aljyo.core.event


// user interaction으로 발생하는 이벤트
sealed interface Event<in T> {
    suspend fun process(input: T)
}