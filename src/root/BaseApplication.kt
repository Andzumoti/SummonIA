package root

import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.stage.StageStyle
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.imageio.ImageIO


class BaseApplication : Application() {
    lateinit var trayIcon: TrayIcon
    override fun init() {
        println("Now summoning IA-chan...")
    }

    override fun start(dummyStage: Stage) {
        println("Hello IA-chan...")
        Platform.setImplicitExit(false)
        initDummyStage(dummyStage)
        val IAStage = initIAStage(dummyStage)
        initSystemTray(IAStage)
        dummyStage.show()
        IAStage.show()
    }

    override fun stop() {
        println("Bye-bye IA-chan...")
        if (SystemTray.isSupported()) SystemTray.getSystemTray().remove(trayIcon)
    }

    private fun initDummyStage(dummyStage: Stage): Unit {
        with(dummyStage) {
            initStyle(StageStyle.UTILITY)
            opacity = 0.0
            x = -1000.0
            y = -1000.0
        }
    }

    private fun initIAStage(dummyStage: Stage): Stage {
        val fxmlLoader = FXMLLoader(javaClass.classLoader.getResource(IA.fxml))
        val root = fxmlLoader.load<Parent>()
        val scene = Scene(root)
        scene.fill = Color.TRANSPARENT

        val mainStage = Stage().apply {
            initOwner(dummyStage)
            initStyle(StageStyle.TRANSPARENT)
            this.scene = scene
            x = 1400.0
            y = 850.0
            isAlwaysOnTop = true
        }
        fxmlLoader.getController<IA>().apply {
            motherClass = this@BaseApplication
            stage = mainStage
        }
        return mainStage
    }

    private fun initSystemTray(IAStage: Stage): Unit {
        if (!SystemTray.isSupported()) return
        val image = ImageIO.read(javaClass.classLoader.getResource(IA.icon))
        var popupMenu = PopupMenu().apply {
            add(MenuItem("Bye-bye")).apply { addActionListener {
                Platform.runLater {
                    Platform.exit()
                }
            } }
        }
        trayIcon = TrayIcon(image!!, "IA-chan", popupMenu)
        with(trayIcon) {
            isImageAutoSize = true
            addActionListener {
                Platform.runLater {
                    with(IAStage) {
                        try {
                            if (isShowing) hide() else show()
                        } catch (e: Exception) {
                        }
                    }
                }
            }
//            addMouseListener(object :MouseAdapter() {
//                override fun mousePressed(e: MouseEvent?) {
//                    if (e?.button == MouseEvent.BUTTON3) {
//                        trayIcon.
//                    }
//                }
//            })
            SystemTray.getSystemTray().add(trayIcon)
        }
    }
}
