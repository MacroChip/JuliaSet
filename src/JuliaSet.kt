import java.awt.*
import java.awt.image.BufferedImage
import javax.swing.JFrame
import javax.swing.JPanel

class JuliaPanel : JPanel() {
    init {
        preferredSize = Dimension(800, 600)
        background = Color.white
    }

    private val maxIterations = 300
    private val zoom = 1
    private val moveX = 0.0
    private val moveY = 0.0
    private val cX = -0.7
    private val cY = 0.27015

    public override fun paintComponent(gg: Graphics) {
        super.paintComponent(gg)
        with(gg as Graphics2D) {
            setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            val w = width
            val h = height
            val image = BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)
            for (x in 0 until w) {
                for (y in 0 until h) {
                    var zx = 1.5 * (x - w / 2) / (0.5 * zoom * w) + moveX
                    var zy = (y - h / 2) / (0.5 * zoom * h) + moveY
                    var i = maxIterations.toFloat()
                    while (zx * zx + zy * zy < 4 && i > 0) {
                        val tmp = zx * zx - zy * zy + cX
                        zy = 2.0 * zx * zy + cY
                        zx = tmp
                        i--
                    }
                    image.setRGB(x, y, Color.HSBtoRGB(maxIterations / i % 1, 1f, (if (i > 0) 1 else 0).toFloat()))
                }
            }
            drawImage(image, 0, 0, null)
        }
    }
}

fun main() {
    with(JFrame()) {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        title = "Julia Set"
        isResizable = false
        add(JuliaPanel(), BorderLayout.CENTER)
        pack()
        setLocationRelativeTo(null)
        isVisible = true
    }
}