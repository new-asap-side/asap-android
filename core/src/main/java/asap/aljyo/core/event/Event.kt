package asap.aljyo.core.event


// user interaction으로 발생하는 이벤트
sealed interface Event<in I, out R> {
    suspend fun process(input: I): R
}