import androidx.compose.runtime.*
import kotlinx.browser.document
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.events.Event

fun main() {
    renderComposable(rootElementId = "root") {
        Body()
    }
}

@Immutable
data class Counter(var count: Int) {
    fun increment(): Counter = copy(count + 1)
}

@Composable
fun Body() {
    CountingScreen()
}

@Composable
fun CountingScreen() {
    var counter: Counter by remember { mutableStateOf(Counter(0)) }
    val onCounterChanged = { newCounter: Counter -> counter = newCounter }

    Div { Text("Clicked: $counter") }

    fun incCounter() {
        onCounterChanged(counter.increment())
    }

    CountingComponent(
        counter = counter,
        incrementCount = { incCounter() },
    )
}

@Composable
fun CountingComponent(
    counter: Counter,
    incrementCount: () -> Unit,
) {
    Div {
        Text("Component Clicked: ${counter.count}")
    }

    Button(
        attrs = {
            ref {
                val keydownListener = { event: Event -> incrementCount() }
                document.addEventListener("keydown", keydownListener)
                onDispose { document.removeEventListener("keydown", keydownListener) }
            }
            onClick { _ ->
                incrementCount()
            }
        }
    ) {
        Text("Click")
    }
}