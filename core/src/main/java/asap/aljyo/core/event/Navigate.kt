package asap.aljyo.core.event

data class Navigate<I>(val navigate: (I) -> Unit): Event<I, Unit> {
    override suspend fun process(input: I) = navigate(input)
}