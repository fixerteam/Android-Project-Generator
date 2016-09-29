package ui.project_info

import core.ProjectGenerator.createProject
import core.entity.ProjectInfo
import javafx.collections.FXCollections
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
        field("MinSDK") {
          val androidVersions = FXCollections.observableArrayList(
              "API 14: Android 4.0 (IceCreamSandwich)",
              "API 15: Android 4.0.3 (IceCreamSandwich)",
              "API 16: Android 4.1 (Jelly Bean)",
              "API 17: Android 4.2 (Jelly Bean)",
              "API 18: Android 4.3 (Jelly Bean)",
              "API 19: Android 4.4 (KitKat)",
              "API 21: Android 5.0 (Lollipop)",
              "API 22: Android 5.1 (Lollipop)",
              "API 23: Android 6.0 (Marshmallow)",
              "API 24: Android 7.0 (Nougat)")
          combobox(values = androidVersions) {
            bind(projectInfo.minSdkProperty())
          }
        }
      }

      button("Next") {
        setOnAction {
          createProject(projectInfo)
        }
      }
    }
  }
}