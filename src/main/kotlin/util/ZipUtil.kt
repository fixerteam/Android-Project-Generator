package util

import ui.GeneratorApp
import java.io.*
import java.nio.file.FileSystems
import java.nio.file.Files.createDirectories
import java.nio.file.Files.createFile
import java.util.zip.ZipFile

fun unzip(filename: String, destFolder: String) {
  val dest = destFolder + "/"
  val rootDir = filename.replace(".zip", "/")
  val zipFile = ZipFile(File(exportResource(filename)))
  val fileSystem = FileSystems.getDefault()
  val entries = zipFile.entries()

  while (entries.hasMoreElements()) {
    val entry = entries.nextElement()
    if (entry.name != rootDir) {
      val entryName = entry.name.replace(rootDir, "")
      if (entry.isDirectory) {
        createDirectories(fileSystem.getPath(dest + entryName))
      } else {
        val bis = BufferedInputStream(zipFile.getInputStream(entry))
        val uncompressedFileName = dest + entryName
        createFile(fileSystem.getPath(uncompressedFileName))
        bis.copyTo(FileOutputStream(uncompressedFileName))
      }
    }
  }
}

fun exportResource(resourceName: String): String {
  val stream = GeneratorApp::class.java.classLoader.getResourceAsStream(resourceName)
  val jarFolder = File(GeneratorApp::class.java.protectionDomain.codeSource.location.toURI().path).parentFile.path.replace('\\', '/')
  val resStreamOut = FileOutputStream(jarFolder + resourceName)
  stream.copyTo(resStreamOut)
  return jarFolder + resourceName
}