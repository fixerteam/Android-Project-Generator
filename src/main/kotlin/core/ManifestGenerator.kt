package core

import core.entity.ProjectInfo
import java.io.File

class ManifestGenerator {

  fun createAndroidManifest(info: ProjectInfo) {
    File("${info.mainRoot}/AndroidManifest.xml").writeText(generateManifest(info.packageName))
  }

  private fun generateManifest(packageName: String) =
      "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
          "<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
          "\tpackage=\"$packageName\">\n\n" +

          "\t<application\n" +
          "\t\tandroid:allowBackup=\"false\"\n" +
          "\t\tandroid:icon=\"@mipmap/ic_launcher\"\n" +
          "\t\tandroid:label=\"@string/app_name\"\n" +
          "\t\tandroid:supportsRtl=\"true\"\n\t>\n" +
          "\t</application>\n\n" +

          "</manifest>"
}