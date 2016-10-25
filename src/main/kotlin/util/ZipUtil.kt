package util

import ui.GeneratorApp
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.file.FileSystems
import java.nio.file.Files.createDirectories
import java.nio.file.Files.createFile
import java.util.zip.ZipFile

fun unzip(filename: String, destFolder: String) {
  val dest = destFolder + "/"
  val rootDir = filename.replace(".zip", "/")
  val tmpFile = File(exportResource(filename))
  val zipFile = ZipFile(tmpFile)
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
  zipFile.close()
  tmpFile.delete()
}

fun exportResource(resourceName: String): String {
  val stream = GeneratorApp::class.java.classLoader.getResourceAsStream(resourceName)
  val filePath = "${getResourcePath()}/$resourceName"
  val resStreamOut = FileOutputStream(filePath)
  stream.copyTo(resStreamOut)
  return filePath
}

private fun getResourcePath() = File(
    GeneratorApp::class.java.protectionDomain.codeSource.location.toURI().path)
    .parentFile.path.replace('\\', '/')