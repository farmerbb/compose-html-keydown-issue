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
    var counter: Counter by remember { mutableStateOf(Counter(0)) }
    CountingScreen(
        counter = counter,
        onCounterChanged = { newCounter -> counter = newCounter }
    )
}

@Composable
fun CountingScreen(
    counter: Counter,
    onCounterChanged: (Counter) -> Unit,
) {
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
        Text("ComponentB Clicked: ${counter.count}")
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