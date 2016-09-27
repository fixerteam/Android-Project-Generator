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
    emptyLine()
    android {
      compileSdkVersion("versions.COMPILE_SDK")
      buildToolsVersion("versions.BUILD_TOOLS")
      emptyLine()
      defaultConfig {
        applicationId("com.example")
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
      compile("com.android.support:design-v7:\$versions.ANDROID_SUPPORT")
    }
  }.toString()
}