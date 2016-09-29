package core

import core.entity.ProjectInfo
import java.io.File

class ResourceGenerator {

  companion object {
    @JvmStatic val RES_FILES_DIR = "src/main/androidFiles/"
  }

  fun createResourceFolders(info: ProjectInfo) {
    File(RES_FILES_DIR).copyRecursively(File(info.mainRoot), true)
    val stringsXml = File("${info.mainRoot}/res/values/strings.xml")
    stringsXml.writeText(stringsXml.readText().replace("<app_name>", info.appName))
  }
}