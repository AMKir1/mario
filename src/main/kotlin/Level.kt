typealias IntPair = Pair<Int, Int>
typealias IntTriple = Pair<Pair<Int, Int>, Int>

class Level(
    val floor: List<IntRange> = emptyList(),
    val bushes: List<Pair<Int, Int>> = emptyList(),
    val hills: List<Pair<Int, Int>> = emptyList(),
    val clouds: List<Pair<Pair<Int, Int>, Int>> = emptyList(),
    val pipes: List<Pair<Int, Int>> = emptyList(),
    val bricks: List<Pair<Pair<Int, Int>, Int>> = emptyList(),
    val pandoras: List<Pair<Int, Int>> = emptyList(),
    val forwardSteps: List<Pair<Pair<Int, Int>, Int>> = emptyList(),
    val backwardSteps: List<Pair<Pair<Int, Int>, Int>> = emptyList(),
) {

    var entities = setOf<Entity>()
    val CELL_SIZE = 16.0
    val FREE_AREA = 13
    var windowX = 0.0

    init {
        floor.forEach { range ->
            for (j in -1 downTo -2) {
                for (i in range) {
                    addFloor(i, j)
                }
            }
        }

        clouds.forEach { (indices, size) ->
            val (i, j) = indices
            addCloud(i, j, size)
        }

        bushes.forEach { (i, size) ->
            addBush(i, size)
        }

        hills.forEach { (i, size) ->
            addHill(i, size)
        }
    }




    fun render() {
        for (entity in entities) {
            if (entity.x + entity.sprite.w > windowX && entity.x < windowX + 16) {
                drawSprite(entity.sprite, entity.x - windowX, entity.y)
            }
        }
    }

    fun drawSprite(sprite: Sprite, x: Double, y: Double) {
        context.drawImage(
            sourceImage,
            sx = sprite.si * CELL_SIZE + 1 / 3.0,
            sy = sprite.sj * CELL_SIZE + 1 / 3.0,
            sw = sprite.w * CELL_SIZE - 2 / 3.0,
            sh = sprite.h * CELL_SIZE - 2 / 3.0,
            dw = sprite.w * CELL_SIZE,
            dh = sprite.h * CELL_SIZE,
            dx = x * CELL_SIZE,
            dy = (FREE_AREA - y - sprite.h) * CELL_SIZE,
        )
    }

    fun addFloor(i: Int, j: Int) {
        entities += Entity(i = i, j = j, floorSprite)
    }

    fun addBush(i: Int, size: Int) {
        entities += Entity(i = i, j = -2, bushSprites[0])
        for (n in i + 1..size + i) {
            entities += Entity(i = n, j = -2, bushSprites[1])
        }
        entities += Entity(i = size + i + 1, j = -2, bushSprites[2])
    }

    fun addCloud(i: Int, j: Int, size: Int) {
        entities += Entity(i = i, j = j, cloudSprite[0])
        for (n in i + 1..size + i) {
            entities += Entity(i = n, j = j, cloudSprite[1])
        }
        entities += Entity(i = size + i + 1, j = j, cloudSprite[2])
    }

    fun addHill(i: Int, height: Int) {
        var col = i + height - 3
        for (j in 1 until height) {
            val size = (2 * j) - 1
            drawHillSection(col, height - j, size)
            col--
        }
        entities += Entity(i = height - 1, j = height - 1, hillSprites[1]) // top
    }

    fun drawHillSection(i: Int, j: Int, size: Int) {
        entities += Entity(i = i, j = j, hillSprites[0]) // left side
        for (n in i + 1..size + i) {
            entities += Entity(i = n, j = j, hillSprites[4]) // middle
        }
        entities += Entity(i = size + i + 1, j = j, hillSprites[2]) // right side
    }

}