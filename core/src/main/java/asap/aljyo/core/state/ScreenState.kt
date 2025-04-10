package asap.aljyo.core.state

interface ScreenState<out S> {
    val state: S

    suspend fun refresh()
}