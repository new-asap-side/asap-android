package asap.aljyo.core.state

sealed class UiState<out D> {
    data object Loading : UiState<Nothing>()

    data class Success<out D>(val data: D) : UiState<D>()

    data object Error : UiState<Nothing>()
}
