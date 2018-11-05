package root

import javafx.application.Platform
import javafx.concurrent.ScheduledService
import javafx.concurrent.Task
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ContextMenu
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.Stage
import java.net.URL
import java.util.*

class IA : Initializable {
    lateinit var motherClass: BaseApplication
    var stage: Stage = Stage()
    @FXML
    lateinit var IAView: ImageView
    lateinit var IATalk: Label
    
    companion object {
        val fxml = "fxml/IA.fxml"
        val icon = "images/icon.png"
        val default = Image("images/default.png")
        val smile = Image("images/smile.png")
        val wink = Image("images/wink.png")
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        object : ScheduledService<Unit>() {
            override fun createTask(): Task<Unit> {
                return object : Task<Unit>() {
                    override fun call() {
                        Thread.sleep(4000)
                        if (IAView.image != smile) {
                            IAView.image = wink
                            Thread.sleep(150)
                            IAView.image = default
                            Thread.sleep(150)
                            IAView.image = wink
                            Thread.sleep(150)
                            IAView.image = default
                        }
                    }
                }
            }
        }.apply {
            restartOnFailure = true
            start()
        }

    }

    @FXML
    private fun makeIASmile(): Unit {
        IAView.image = smile
    }

    @FXML
    private fun makeIADefault(): Unit {
        IAView.image = default
    }
}
