package core

import core.entity.ProjectInfo
import java.io.File
import java.io.FileNotFoundException

object PackageGenerator {

  const val GRADLE_FILES_DIR = "src/main/gradleFiles/"

  var projectRoot = ""
  var androidTestRoot = ""
  var mainRoot = ""
  var testRoot = ""

  fun createProject(info: ProjectInfo) {
    createBaseProjectFolder(info)
    addGradleWrappers()
    addAppModule()
    addPackages(info)
  }

  private fun createBaseProjectFolder(info: ProjectInfo) {
    with(info) {
      projectRoot = createFolders("$projectLocation/$appName").path
    }
  }

  private fun addGradleWrappers() {
    try {
      File(GRADLE_FILES_DIR).copyRecursively(File(projectRoot), true)
    } catch (e: FileNotFoundException) {
      e.printStackTrace()
    }
  }

  private fun addAppModule() {
    createFolders("$projectRoot/app/src/").path.apply {
      androidTestRoot = createFolders("$this/androidTest").path
      mainRoot = createFolders("$this/main").path
      testRoot = createFolders("$this/test").path
    }
    createFile("$projectRoot/settings.gradle").writeText("include ':app'")
  }

  private fun addPackages(info: ProjectInfo) {
    val escapedPackage = replaceDots(info.packageName).trim()
    createFolders("$androidTestRoot/java/$escapedPackage")
    createFolders("$mainRoot/java/$escapedPackage")
    createFolders("$testRoot/java/$escapedPackage")
  }

  private fun createFolders(path: String) = File(path).apply { mkdirs() }

  private fun createFile(pathName: String) = File(pathName).apply { createNewFile() }

  private fun replaceDots(input: String) = input.replace('.', '/')
}