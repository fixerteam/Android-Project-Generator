package core

import core.entity.ProjectInfo
import java.io.File

object PackageGenerator {

  fun createProject(info: ProjectInfo) {
    with(info) {
      createFolders("${info.projectLocation}/${replaceDots(info.packageName)}/${info.appName}")
    }
  }

  private fun createFolders(path: String) {
    File(path).mkdirs()
  }

  private fun replaceDots(input: String) = input.replace('.', '/')
}