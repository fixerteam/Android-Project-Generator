package core

import core.entity.ProjectInfo
import util.unzip
import java.io.File

class ResourceGenerator {

  companion object {
    @JvmStatic val RES_FILES_ZIP = "androidFiles.zip"
  }

  fun createResourceFolders(info: ProjectInfo) {
    unzip(RES_FILES_ZIP, info.mainRoot)
    val stringsXml = File("${info.mainRoot}/res/values/strings.xml")
    stringsXml.writeText(stringsXml.readText().replace("<app_name>", info.appName))
  }
}