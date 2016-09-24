package ui

import tornadofx.App
import tornadofx.View
import ui.project_info.ProjectInfoView
import kotlin.reflect.KClass

class GeneratorApp : App(ProjectInfoView::class, Style::class) {

  companion object {
    val APP_NAME = "Android Project Generator"
  }
}