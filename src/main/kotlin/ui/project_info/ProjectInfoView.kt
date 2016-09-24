package ui.project_info

import core.PackageGenerator
import core.entity.ProjectInfo
import javafx.scene.Parent
import javafx.scene.layout.BorderPane
import tornadofx.*
import ui.GeneratorApp.Companion.APP_NAME

class ProjectInfoView : View() {
  override val root = Form()

  val projectInfo = ProjectInfo()

  init {
    title = APP_NAME

    with(root) {
      fieldset("Project info") {
        field("App Name:") {
          textfield().bind(projectInfo.appNameProperty())
        }
        field("Company domain:") {
          textfield {
            promptText = "example.com"
            bind(projectInfo.companyDomainProperty())
          }
        }
        field("Package name:") {
          textfield {
            promptText = "com.example"
            bind(projectInfo.packageNameProperty())
          }
        }
        field("Project location:") {
          textfield {
            promptText = "/home/user/projects"
            bind(projectInfo.projectLocationProperty())
          }
        }
      }

      button("Next") {
        setOnAction {
          PackageGenerator.createProject(projectInfo)
        }
      }
    }
  }
}