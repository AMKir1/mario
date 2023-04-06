import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.Image
import kotlinx.browser.document
import kotlinx.browser.window


fun main() {
        window.onload = {
            val canvas = document.getElementById("canvas") as HTMLCanvasElement
            val context = canvas.getContext("2d") as CanvasRenderingContext2D
            context.fillStyle = "#7974ff"
            context.fillRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
            val image = Image()
            image.src = "mario.png"
            image.onload = {
                context.drawImage(image, 0.0, 0.0)
            }
            Unit
        }
}
