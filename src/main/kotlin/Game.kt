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
        sourceImage.onload = {
           render()
        }
        Unit
    }
}

fun render() {
    drawClouds(7,1)
    drawBush(11, 3)
    drawHill(1 , 5)
    drawFloor()
}

const val CELL_SIZE = 16.0
const val FREE_AREA = 13

fun drawSprite(sprite: Sprite, x: Double, y: Double) {
    context.drawImage(
        sourceImage,
        sx = sprite.si * CELL_SIZE + 1/3.0,
        sy = sprite.sj * CELL_SIZE + 1/3.0,
        sw = sprite.w * CELL_SIZE - 2/3.0,
        sh = sprite.h * CELL_SIZE - 2/3.0,
        dw = sprite.w * CELL_SIZE,
        dh = sprite.h * CELL_SIZE,
        dx = x * CELL_SIZE,
        dy = (FREE_AREA - y - sprite.h) * CELL_SIZE,
    )
}

fun drawSprite(sprite: Sprite, i: Int, j: Int) {
    drawSprite(sprite, i.toDouble(), j.toDouble())
}

fun drawClouds(i: Int, lenght: Int) {
    val cloudHeight = cloudSprite[0].h
    drawSprite(cloudSprite[0], i = i, j = 5 + cloudHeight)
    for (n in i + 1 .. lenght + i) {
        drawSprite(cloudSprite[1], i = n, j = 5 + cloudHeight)
    }
    drawSprite(cloudSprite[2],  i = lenght + i + 1, j = 5 + cloudHeight)
}

fun drawBush(i: Int, lenght: Int) {
    val bushHeight = cloudSprite[0].h
    drawSprite(bushSprites[0], i = i, j = -2 + bushHeight)
    for (n in i + 1 .. lenght + i) {
        drawSprite(bushSprites[1], i = n, j = -2 + bushHeight)
    }
    drawSprite(bushSprites[2], i = lenght + i + 1, j = -2 + bushHeight)
}

fun drawFloor() {
    for (j in 16 downTo 15) {
        for (i in 16 downTo 0) {
            drawSprite(floorSprite, i, FREE_AREA - j + floorSprite.h)
        }
    }
}

fun drawHill(i: Int, height: Int) {
    var col = i + height
    for(j in 0 until height) {
        val size = (2 * j) - 1
        drawHillSection(col, height - j, size)
        col--
    }
    drawSprite(hillSprites[1], i = col - hillSprites[0].h, j = height) // top
}

fun drawHillSection(i: Int, j: Int, size: Int) {
    val hillHeight = hillSprites[0].h
    drawSprite(hillSprites[0], i = i, j = j - hillHeight) // left side
        for (n in i + 1 .. size + i - 1) {
        drawSprite(hillSprites[4], i = n, j = j - hillHeight) // middle
    }
    drawSprite(hillSprites[2], i = size + i + 1, j = j - hillHeight) // right side
}