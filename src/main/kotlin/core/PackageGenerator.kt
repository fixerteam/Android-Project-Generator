package core

import core.entity.ProjectInfo
import util.unzip
import java.io.File

class PackageGenerator {

  companion object {
    @JvmStatic val GRADLE_FILES_ZIP = "gradleFiles.zip"
  }

  fun createPackageStructure(info: ProjectInfo) {
    with(info) {
      createBaseProjectFolder(projectRoot)
      addGradleWrappers(projectRoot)
      addAppModule(this)
      addPackages(this)
    }
  }

  private fun createBaseProjectFolder(projectRoot: String) {
    createFolders(projectRoot)
  }

  private fun addGradleWrappers(projectRoot: String) {
    unzip(GRADLE_FILES_ZIP, projectRoot)
  }

  private fun addAppModule(info: ProjectInfo) {
    with(info) {
      createFolders("$srcDir/androidTest")
      createFolders("$srcDir/main")
      createFolders("$srcDir/test")
      createFile("$projectRoot/settings.gradle").writeText("include ':app'")
    }
  }

  private fun addPackages(info: ProjectInfo) {
    val escapedPackage = replaceDots(info.packageName).trim()
    with(info) {
      createFolders("$androidTestRoot/java/$escapedPackage")
      createFolders("$mainRoot/java/$escapedPackage")
      createFolders("$testRoot/java/$escapedPackage")
    }
  }

  private fun createFolders(path: String) = File(path).apply { mkdirs() }

  private fun createFile(pathName: String) = File(pathName).apply { createNewFile() }

  private fun replaceDots(input: String) = input.replace('.', '/')
}