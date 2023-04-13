import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.Image
import kotlinx.browser.document
import kotlinx.browser.window

val sourceImage = Image()
lateinit var context: CanvasRenderingContext2D

fun main() {
    window.onload = {
        val canvas = document.getElementById("canvas") as HTMLCanvasElement
        context = canvas.getContext("2d") as CanvasRenderingContext2D
        context.scale(3.0, 3.0)
        context.fillStyle = "#7974ff"
        context.fillRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())//768x720
        sourceImage.src = TILES_IMAGE
        val level = Level()
        sourceImage.onload = {
            level.render()
        }
        Unit
    }
}