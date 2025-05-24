import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.example.project.data.remote.exception.RemoteException
import org.example.project.data.repository.GetNewsRepository
import org.example.project.di.AppModule
import org.example.project.model.ApiResult
import org.example.project.model.News
import org.example.project.ui.news.NewsViewModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@ExperimentalCoroutinesApi
class NewsViewModelTest {

    private lateinit var newsRepository: GetNewsRepository

    private lateinit var viewModel: NewsViewModel

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @BeforeTest
    fun setUp() {
        // Sets the main dispatcher to the test dispatcher
        Dispatchers.setMain(testDispatcher)
        newsRepository = mock<GetNewsRepository>()
        viewModel = AppModule.provideNewsViewModel(newsRepository, testScope)
    }

    @AfterTest
    fun tearDown() {
        // Resets the main dispatcher to the original one
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() = runTest(testDispatcher) {
        val initialState = viewModel.uiState.value
        assertFalse(initialState.isLoading, "Initial isLoading should be false")
        assertEquals(emptyList(), initialState.news, "Initial news should be empty")
        assertNull(initialState.error, "Initial error should be null")
    }

    @Test
    fun `processIntent LoadNews success updates state with news`() = runTest(testDispatcher) {
        val mockNews = listOf(
            News(
                id = 1, title = " title 1", description = "description 1", author = "author 1",
                isLocal = false, publishedAtUnix =
                    1748107452000L, media = null
            ),
            News(
                id = 1, title = " title 2", description = "description 2", author = "author 2",
                isLocal = false, publishedAtUnix =
                    1748107452001L, media = null
            )
        )

        everySuspend { newsRepository.getNews(1, 5) } returns ApiResult.Success(mockNews)

        viewModel.loadNews()
        advanceUntilIdle()


        // verify
        val currentState = viewModel.uiState.value
        assertFalse(currentState.isLoading, "isLoading should be false after success")
        assertEquals(mockNews, currentState.news, "News should be updated")
        assertNull(currentState.error, "Error should be null on success")
    }

    @Test
    fun `processIntent LoadNews failure updates state with error`() = runTest(testDispatcher) {
        val errorMessage = "Server Message"
        everySuspend {
            newsRepository.getNews(1, 5)
        } returns ApiResult.Error(RemoteException.ResultError(errorMessage))


        viewModel.loadNews()
        advanceUntilIdle()

        // verify
        val currentState = viewModel.uiState.value
        assertFalse(currentState.isLoading, "isLoading should be false after error")
        assertEquals(emptyList(), currentState.news, "News should remain empty on error")
        assertNotNull(currentState.error, "Error should be present")
        assertEquals(errorMessage, currentState.error, "Error message should match")
    }
}