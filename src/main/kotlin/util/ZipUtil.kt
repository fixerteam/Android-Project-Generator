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
  val zipFile = ZipFile(File(GeneratorApp.javaClass.classLoader.getResource(filename).toURI()))
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
        val fileOutput = FileOutputStream(uncompressedFileName)
        while (bis.available() > 0) {
          fileOutput.write(bis.read())
        }
      }
    }
  }
}