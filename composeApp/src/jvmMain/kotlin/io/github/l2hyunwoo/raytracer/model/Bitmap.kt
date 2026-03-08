package io.github.l2hyunwoo.raytracer.model

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PixelMap
import androidx.compose.ui.graphics.asComposeImageBitmap
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.ColorAlphaType
import org.jetbrains.skia.ImageInfo
import java.nio.ByteBuffer
import java.nio.ByteOrder

fun PixelMap.toImageBitmap(): ImageBitmap {
    val bitmap = Bitmap()
    /**
     * Bitmap에 픽셀 데이터를 저장할 메모리를 할당
     * @param imageInfo 이미지 스펙(크기, 색상 포맷 등)을 받는다
     * @param zeroFixels 두 번째 boolean은 메모리를 0으로 초기화할지 여부
     */
    bitmap.allocPixelsFlags(
        // 이미지의 메타 정보(너비, 높이, 색상 타입, 알파 타입)를 정의
        // N32는 "Native 32-bit", 네이티브 픽셀 포맷을 사용 (대부분 BGRA 또는 RGBA)
        // makeS32는 sRGB 색공간이 명시
        // ColorAlphaType.UNPREMUL: 알파가 RGB 채널에 미리 곱해지지 않은(pre-multiplied 아닌) 상태
        // UNPREMUL: (R, G, B, A) 그대로 저장. 예: 반투명 빨강 = (255, 0, 0, 128)
        // PREMUL: (R*A/255, G*A/255, B*A/255, A). 예: 반투명 빨강 = (128, 0, 0, 128)
        ImageInfo.makeN32(width, height, ColorAlphaType.UNPREMUL),
        false
    )
    // IntArray -> ByteArray 변환 (각 Int를 4바이트 native order로)
    val byteBuffer = ByteBuffer.allocate(buffer.size * 4)
        .order(ByteOrder.nativeOrder())
    byteBuffer.asIntBuffer().put(buffer)
    // 외부에서 만든 픽셀 데이터(ByteArray)를 Bitmap에 직접 주입
    // rowBytes는 한 행의 바이트 수(= width × 4 for 32bit)
    bitmap.installPixels(bitmap.imageInfo, byteBuffer.array(), bitmap.rowBytes)
    return bitmap.asComposeImageBitmap()
}
