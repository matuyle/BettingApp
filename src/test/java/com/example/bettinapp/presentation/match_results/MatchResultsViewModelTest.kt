package com.example.bettinapp.presentation.match_results

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.bettinapp.MainCoroutineRule
import com.example.bettinapp.core.util.Resource
import com.example.bettinapp.domain.model.Result
import com.example.bettinapp.domain.repository.FakeBettingRepository
import com.example.bettinapp.domain.repository.FakeDataStoreRepository
import com.example.bettinapp.domain.use_case.*
import com.example.bettinapp.presentation.common.Constants.MATCH_LIST_SCREEN
import com.example.bettinapp.presentation.match_results.MatchResultsViewModel.UiEvent
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify


@ExperimentalCoroutinesApi
class MatchResultsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    lateinit var application: Application
    private lateinit var viewModel: MatchResultsViewModel

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    @Before
    fun setup() {
        application = Mockito.mock(Application::class.java)
        viewModel = spy(
            MatchResultsViewModel(
                getMatchesUseCases = MatchesUseCases(
                    getMatchesUseCase = GetMatchesUseCase(FakeBettingRepository()),
                    getMatchUseCase = GetMatchUseCase(FakeBettingRepository()),
                    addMatchUseCase = AddMatchUseCase(FakeBettingRepository()),
                    getMatchWithPrediction = GetMatchWithPrediction(FakeBettingRepository()),
                    getResultsUseCase = GetResultsUseCase(FakeBettingRepository()),
                    getMatchesAndResults = GetMatchesAndResults(FakeBettingRepository()),
                    deleteTablesUseCase = DeleteTablesUseCase(FakeBettingRepository())
                ),
                FakeDataStoreRepository(),
                application
            )
        )
    }

    @Test
    fun `validate state on Success result with empty list`() {
        val list = emptyList<Result>()
        Resource.Success(data = list)
        assertThat(viewModel.state.value.matches).isEmpty()
        assertThat(viewModel.state.value.isLoading).isFalse()
        assertThat(viewModel.state.value.error).isNotNull()
    }

    @Test
    fun `validate state on Error result`() {
        val list = emptyList<Result>()
        val resource = Resource.Error(data = list, message = "error")
        viewModel.handleResult(resource)
        assertThat(viewModel.state.value.matches).isEmpty()
        assertThat(viewModel.state.value.error).isEqualTo("error")
    }

    @Test
    fun `validate state on Loading result`() {
        val list = emptyList<Result>()
        val resource = Resource.Loading(data = list)
        viewModel.handleResult(resource)
        assertThat(viewModel.state.value.isLoading).isTrue()
    }

    @Test
    fun `when view model initialized then should emit initial state first`() {
        assertThat(MatchResultsState()).isEqualTo(viewModel.state.value)
    }

    @Test
    fun `OnTopButtonPressed event, UiEvent is sent`() = runTest {
        viewModel.onEvent(MatchResultsEvent.OnTopButtonPressed)
        verify(viewModel, Mockito.times(1)).sendUiEvent(UiEvent.OnReset)
    }

    @Test
    fun `OnTopButtonPressed event, is loading is true and state is reset`() = runTest {
        viewModel.onEvent(MatchResultsEvent.OnTopButtonPressed)
        assertThat(viewModel.state.value.state).isEqualTo("reset")
        assertThat(viewModel.state.value.isLoading).isEqualTo(true)
    }

    @Test
    fun `OnNav event, is storeLatestScreen function called`() = runTest {
        viewModel.onEvent(MatchResultsEvent.OnNav)
        verify(viewModel, Mockito.times(1)).storeLatestScreen(MATCH_LIST_SCREEN)
    }

    @Test
    fun `emit UiEvent shen sendUiEvent function is called`() = runTest(UnconfinedTestDispatcher()) {
        val event = UiEvent.OnReset
        viewModel.sendUiEvent(event)
        val deferred = async {
            eventFlow.first()
        }

        _eventFlow.emit(event)
        assertThat(UiEvent.OnReset).isEqualTo(deferred.await())
    }
}