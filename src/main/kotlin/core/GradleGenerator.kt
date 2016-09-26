package core

import core.PackageGenerator.projectRoot
import util.generateBuildGradle
import java.io.File

object GradleGenerator {

  fun createRootBuildFile() {
    File("$projectRoot/build.gradle").writeText(generateRootBuildGradle())
  }

  fun createAppBuildGradle() {
    File("$projectRoot/app/build.gradle").writeText(generateAppBuildGradle())
  }

  private fun generateRootBuildGradle() = generateBuildGradle {
    buildscript {
      repositories {
        repository("jcenter")
      }

      dependencies {
        classpath("com.android.tools.build:gradle:2.1.3")
      }
    }
    allprojects {
      repository("jcenter")
    }
  }.toString()

  private fun generateAppBuildGradle() = generateBuildGradle {
    plugin("com.android.application")
    android {
      defaultConfig {

      }
      buildTypes {

      }
      sourceSets {

      }
    }
    dependencies {

    }
  }.toString()
}