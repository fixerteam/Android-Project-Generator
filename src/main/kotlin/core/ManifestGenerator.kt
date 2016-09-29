package core

import core.entity.ProjectInfo
import java.io.File

class ManifestGenerator {

  fun createAndroidManifest(info: ProjectInfo) {
    val manifest = File("${info.mainRoot}/AndroidManifest.xml")
    manifest.writeText(manifest.readText().replace("<packageNameStub>", info.packageName))
  }
}