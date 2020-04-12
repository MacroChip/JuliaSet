import java.awt.*
import java.awt.image.BufferedImage
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities

class JuliaSetTranslated : JPanel() {
    private val maxIter = 300
    private val zoom = 1.0
    private var cY: Double = 0.toDouble()
    private var cX: Double = 0.toDouble()

    init {
        preferredSize = Dimension(800, 600)
        background = Color.white
    }

    internal fun drawJuliaSet(g: Graphics2D) {
        val w = width
        val h = height
        val image = BufferedImage(
            w, h,
            BufferedImage.TYPE_INT_RGB
        )

        cX = -0.7
        cY = 0.27015
        val moveX = 0.0
        val moveY = 0.0
        var zx: Double
        var zy: Double

        for (x in 0 until w) {
            for (y in 0 until h) {
                zx = 1.5 * (x - w / 2) / (0.5 * zoom * w.toDouble()) + moveX
                zy = (y - h / 2) / (0.5 * zoom * h.toDouble()) + moveY
                var i = maxIter.toFloat()
                while (zx * zx + zy * zy < 4 && i > 0) {
                    val tmp = zx * zx - zy * zy + cX
                    zy = 2.0 * zx * zy + cY
                    zx = tmp
                    i--
                }
                val c = Color.HSBtoRGB(maxIter / i % 1, 1f, (if (i > 0) 1 else 0).toFloat())
                image.setRGB(x, y, c)
            }
        }
        g.drawImage(image, 0, 0, null)
    }

    public override fun paintComponent(gg: Graphics) {
        super.paintComponent(gg)
        val g = gg as Graphics2D
        g.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        )
        drawJuliaSet(g)
    }

}

fun main(args: Array<String>) {
    SwingUtilities.invokeLater {
        val f = JFrame()
        f.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        f.title = "Julia Set"
        f.isResizable = false
        f.add(JuliaSetTranslated(), BorderLayout.CENTER)
        f.pack()
        f.setLocationRelativeTo(null)
        f.isVisible = true
    }
}
