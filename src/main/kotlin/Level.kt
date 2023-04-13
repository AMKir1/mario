class Level {
    var entities = setOf<Entity>()
    val CELL_SIZE = 16.0
    val FREE_AREA = 13

    fun render() {
        addCloud(7,5,1)
        addBush(11,3)
        addHill(1, 3)
        addFloor()

        for (entity in entities) {
            drawSprite(entity.sprite, entity.x, entity.y)
        }
    }

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

    fun addFloor() {
        val FREE_AREA = 13
        for (j in 16 downTo 15) {
            for (i in 16 downTo 0) {
               entities += Entity(i, FREE_AREA - j + floorSprite.h, floorSprite)
            }
        }
    }
    fun addBush(i: Int, size: Int) {
        entities += Entity(i = i, j = -2 + cloudSprite[0].h, bushSprites[0])
        for (n in i + 1 .. size + i) {
            entities += Entity(i = n, j = -2 + cloudSprite[0].h, bushSprites[1])
        }
        entities += Entity(i = size + i + 1, j = -2 + cloudSprite[0].h, bushSprites[2])
    }
    fun addCloud(i: Int, j: Int, size: Int) {
        entities += Entity(i = i, j = j + cloudSprite[0].h, cloudSprite[0])
        for (n in i + 1 .. size + i) {
            entities += Entity(i = n, j = j + cloudSprite[0].h, cloudSprite[1])
        }
        entities += Entity(i = size + i + 1, j = j + cloudSprite[0].h, cloudSprite[2])
    }
    fun addHill(i: Int, height: Int) {
        var col = i + height - 3
        for(j in 1 until height) {
            val size = (2 * j) - 1
            drawHillSection(col, height - j, size)
            col--
        }
        entities += Entity(i = height - 1, j = height - 1, hillSprites[1]) // top
    }
    fun drawHillSection(i: Int, j: Int, size: Int) {
        entities += Entity(i = i, j = j - hillSprites[0].h, hillSprites[0]) // left side
        for (n in i + 1 .. size + i) {
            entities += Entity(i = n, j = j - hillSprites[0].h, hillSprites[4]) // middle
        }
        entities += Entity(i = size + i + 1, j = j - hillSprites[0].h, hillSprites[2]) // right side
    }

}