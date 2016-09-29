package core

import core.entity.ProjectInfo
import util.generateBuildGradle
import java.io.File

class GradleGenerator {

  fun createRootBuildFile(info: ProjectInfo) {
    File("${info.projectRoot}/build.gradle").writeText(generateRootBuildGradle(info))
  }

  fun createAppBuildGradle(info: ProjectInfo) {
    File("${info.projectRoot}/app/build.gradle").writeText(generateAppBuildGradle(info))
  }

  private fun generateRootBuildGradle(info: ProjectInfo) = generateBuildGradle {
    buildscript {
      repositories {
        repository("jcenter")
      }

      dependencies {
        classpath("com.android.tools.build:gradle:2.2.0")
      }
    }
    emptyLine()
    allprojects {
      repositories {
        repository("jcenter")
      }
    }
    emptyLine()
    variable("ext.versions", generateVersionsMap(info))
  }.toString()

  private fun generateVersionsMap(info: ProjectInfo) = mapOf(
      "COMPILE_SDK" to 24,
      "MIN_SDK" to info.minSdk.substring(4..5), // simple way?
      "TARGET_SDK" to 24,
      "BUILD_TOOLS" to "\"23.0.3\"",
      "ANDROID_SUPPORT" to "\"24.2.0\"")

  private fun generateAppBuildGradle(info: ProjectInfo) = generateBuildGradle {
    plugin("com.android.application")
    emptyLine()
    android {
      compileSdkVersion("versions.COMPILE_SDK")
      buildToolsVersion("versions.BUILD_TOOLS")
      emptyLine()
      defaultConfig {
        applicationId(info.packageName)
        minSdkVersion("versions.MIN_SDK")
        targetSdkVersion("versions.TARGET_SDK")
        versionCode(1)
        versionName("1.0")
      }
      emptyLine()
      buildTypes {
        release {
          minifyEnabled(false)
          proguardFiles("proguard-rules.pro")
        }
      }
    }
    emptyLine()
    dependencies {
      compile("com.android.support:support-v4:\$versions.ANDROID_SUPPORT")
      compile("com.android.support:appcompat-v7:\$versions.ANDROID_SUPPORT")
      compile("com.android.support:recyclerview-v7:\$versions.ANDROID_SUPPORT")
      compile("com.android.support:design:\$versions.ANDROID_SUPPORT")
    }
  }.toString()
}