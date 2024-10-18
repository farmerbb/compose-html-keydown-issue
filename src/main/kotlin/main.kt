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
class Counter(var count: Int) {
    fun increment(): Counter = Counter(count + 1)
}

@Composable
fun Body() {
    var counter: MutableState<Counter> = remember { mutableStateOf(Counter(0)) }
    CountingScreen(
        counter = counter,
        onCounterChanged = { newCounter -> counter.value = newCounter }
    )
}

@Composable
fun CountingScreen(
    counter: MutableState<Counter>,
    onCounterChanged: (Counter) -> Unit,
) {
    Div { Text("Clicked: ${counter.value}") }

    fun incCounter() {
        onCounterChanged(counter.value.increment())
    }

    CountingComponent(
        counter = counter,
        incrementCount = { incCounter() },
    )
}

@Composable
fun CountingComponent(
    counter: MutableState<Counter>,
    incrementCount: () -> Unit,
) {
    Div {
        Text("Component Clicked: ${counter.value.count}")
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