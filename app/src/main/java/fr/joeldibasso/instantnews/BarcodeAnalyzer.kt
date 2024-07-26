package fr.joeldibasso.instantnews

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning.getClient
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

/**
 * An [ImageAnalysis.Analyzer] that scans for barcodes in the image.
 * When a barcode is found, the [onQrCodeScanned] lambda is called with the barcode value.
 * The image is closed after processing.
 * @param onQrCodeScanned The lambda to call when a barcode is found.
 */
class BarcodeAnalyzer(private val onQrCodeScanned: (String) -> Unit = {}) : ImageAnalysis.Analyzer {

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
        .build()

    private val scanner = getClient(options)

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image?.let { image ->
            scanner.process(
                InputImage.fromMediaImage(
                    image, imageProxy.imageInfo.rotationDegrees
                )
            ).addOnSuccessListener { barcode ->
                barcode?.takeIf { it.isNotEmpty() }
                    ?.mapNotNull { it.rawValue }
                    ?.joinToString(",")
                    ?.let { onQrCodeScanned(it) }
            }.addOnCompleteListener {
                imageProxy.close()
            }
        }
    }
}