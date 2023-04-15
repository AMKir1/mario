import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.Image
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.events.KeyboardEvent

infix fun <A,B>A.x(b: B) = Pair(this,b)

val sourceImage = Image()
val level = Level(
    floor = listOf(0..68, 71..85, 89..152, 155..212),

    bushes = listOf(11 x 3, 23 x 1, 59 x 3, 89 x 2, 137 x 2, 71 x 1, 106 x 3, 118 x 1, 167 x 1),

    clouds = listOf(
        7 x 8 x 1, 19 x 9 x 1, 27 x 8 x 3, 36 x 9 x 2, 56 x 8 x 1, 67 x 9 x 1, 75 x 8 x 3,
        87 x 9 x 1, 103 x 9 x 1, 123 x 8 x 3, 132 x 9 x 2, 152 x 8 x 1, 163 x 9 x 1,
        171 x 8 x 3, 180 x 9 x 2, 200 x 8 x 1
    ),

    bricks = listOf( 20 x 3 x 5, 77 x 3 x 3, 80 x 7 x 8, 91 x 7 x 4, 94 x 3 x 1,
        117 x 3 x 1, 100 x 3 x 2,  120 x 7 x 4,  128 x 7 x 4,  129 x 3 x 2,  168 x 3 x 4
    ),

    hills = listOf(0 x 2, 16 x 1, 48 x 2, 64 x 1, 96 x 2, 111 x 1, 144 x 2, 160 x 1, 192 x 2),

    pipes = listOf(28 x 2, 38 x 3, 46 x 4, 163 x 2, 179 x 2),

    pandoras = listOf(
        16 x 3, 21 x 3, 22 x 7, 23 x 3, 78 x 3, 94 x 7, 105 x 3,
        108 x 3, 108 x 7, 111 x 3, 129 x 7, 130 x 7, 170 x 3
    ),
    forwardSteps = listOf(134 x 4 x 0, 148 x 4 x 1, 181 x 8 x 1),

    backwardSteps = listOf(140 x 4 x 0, 155 x 4 x 0),
)

lateinit var context: CanvasRenderingContext2D


fun main() {
    window.onload = {
        val canvas = document.getElementById("canvas") as HTMLCanvasElement
        context = canvas.getContext("2d") as CanvasRenderingContext2D
        context.scale(3.0, 3.0)
//        context.fillStyle = BACKGROUND_COLOR
//        context.fillRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())//768x720
        sourceImage.src = TILES_IMAGE
        sourceImage.onload = {
            document.addEventListener("keydown", { event ->
                    val keyboardEvent = event as KeyboardEvent
                    when (keyboardEvent.code) {
                        "ArrowLeft" -> level.windowX -= 0.3
                        "ArrowRight" -> level.windowX += 0.3

                    }
                    render()
                })
        }
        Unit
    }
}

    const val CANVAS_WIDTH = 762.0
    const val CANVAS_HEIGHT = 720.0
    const val BACKGROUND_COLOR = "#7974FF"

    fun render() {
        context.clearRect(0.0, 0.0, CANVAS_WIDTH, CANVAS_HEIGHT)
        context.fillStyle = BACKGROUND_COLOR
        context.fillRect(0.0, 0.0, CANVAS_WIDTH, CANVAS_HEIGHT)
        level.render()
    }