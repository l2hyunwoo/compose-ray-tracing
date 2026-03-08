package io.github.l2hyunwoo.raytracer.model

data class Argb(
    val a: Int = 255,
    val r: Int,
    val g: Int,
    val b: Int
) {
    fun toInt() = (a shl 24) or (r shl 16) or (g shl 8) or b
    operator fun times(multiplier: Float): Argb {
        return copy(
            a = a,
            r = (this.r * multiplier).coerceIn(0.0f, 255.0f).toInt(),
            g = (this.g * multiplier).coerceIn(0.0f, 255.0f).toInt(),
            b = (this.b * multiplier).coerceIn(0.0f, 255.0f).toInt()
        )
    }

    companion object {
        @JvmStatic
        fun from(argb: Int) = Argb(
            a = (argb shr 24) and 0xFF,
            r = (argb shr 16) and 0xFF,
            g = (argb shr 8) and 0xFF,
            b = argb and 0xFF
        )
    }
}

data class Yuv(
    val y: Double,
    val u: Double,
    val v: Double
) {
    fun toRgb() = Argb(
        r = (1.164 * (y - 16) + 1.596 * (v - 128)).toInt().coerceIn(0, 255),
        g = (1.164 * (y - 16) - 0.813 * (v - 128) - 0.391 * (u - 128)).toInt().coerceIn(0, 255),
        b = (1.164 * (y - 16) + 2.018 * (v - 128)).toInt().coerceIn(0, 255),
    )

    companion object {
        @JvmStatic
        fun from(rgb: Argb) = Yuv(
            y = (0.257 * rgb.r) + (0.504 * rgb.g) + (0.0098 * rgb.b) + 16,
            u = -(0.148 * rgb.r) - (0.291 * rgb.g) + (0.439 * rgb.b) + 128,
            v = (0.439 * rgb.r) - (0.368 * rgb.g) - (0.0071 * rgb.b) + 128
        )
    }
}

data class Hsv(
    val h: Double,
    val s: Double,
    val v: Double
) {
    val vInt: Int get() = (v * 255).toInt().coerceIn(0, 255)

    fun withVInt(newV: Int): Hsv = copy(v = newV / 255.0)

    fun toRgb(): Argb {
        val c = v * s
        val x = c * (1 - kotlin.math.abs((h / 60) % 2 - 1))
        val m = v - c
        val (r1, g1, b1) = when {
            h < 60 -> Triple(c, x, 0.0)
            h < 120 -> Triple(x, c, 0.0)
            h < 180 -> Triple(0.0, c, x)
            h < 240 -> Triple(0.0, x, c)
            h < 300 -> Triple(x, 0.0, c)
            else -> Triple(c, 0.0, x)
        }
        return Argb(
            r = ((r1 + m) * 255).toInt().coerceIn(0, 255),
            g = ((g1 + m) * 255).toInt().coerceIn(0, 255),
            b = ((b1 + m) * 255).toInt().coerceIn(0, 255),
        )
    }

    companion object {
        @JvmStatic
        fun from(rgb: Argb): Hsv {
            val r = rgb.r / 255.0
            val g = rgb.g / 255.0
            val b = rgb.b / 255.0
            val max = maxOf(r, g, b)
            val min = minOf(r, g, b)
            val delta = max - min
            val h = when {
                delta == 0.0 -> 0.0
                max == r -> 60 * (((g - b) / delta) % 6)
                max == g -> 60 * (((b - r) / delta) + 2)
                else -> 60 * (((r - g) / delta) + 4)
            }.let { if (it < 0) it + 360 else it }
            val s = if (max == 0.0) 0.0 else delta / max
            return Hsv(h = h, s = s, v = max)
        }
    }
}