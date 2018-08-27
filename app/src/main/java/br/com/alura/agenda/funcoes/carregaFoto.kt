package br.com.alura.agenda.funcoes

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface

fun carregaFoto(caminho: String): Bitmap {
    val exif = ExifInterface(caminho)
    val orientacao = exif.getAttribute(ExifInterface.TAG_ORIENTATION)
    val codigoOrientacao = Integer.parseInt(orientacao)

    val rotacao = when(codigoOrientacao) {
        ExifInterface.ORIENTATION_NORMAL -> 0
        ExifInterface.ORIENTATION_ROTATE_90 -> 90
        ExifInterface.ORIENTATION_ROTATE_180 -> 180
        ExifInterface.ORIENTATION_ROTATE_270 -> 270
        else -> 0
    }

    return rotacionaFoto(caminho, rotacao)
}

private fun rotacionaFoto(caminho: String, angulo: Int): Bitmap {
    val bitmap = BitmapFactory.decodeFile(caminho)
    val matrix = Matrix()
    matrix.postRotate(angulo.toFloat())

    return Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}
