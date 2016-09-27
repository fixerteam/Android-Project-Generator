package util

fun generateBuildGradle(init: BuildGradle.() -> Unit): BuildGradle {
  val buildGradle = BuildGradle()
  buildGradle.init()
  return buildGradle
}

class BuildGradle : Tag() {

  override fun render(builder: StringBuilder, indent: String) {
    if (children.isNotEmpty()) {
      for (child in children) {
        child.render(builder, indent)
      }
    }
  }

  fun buildscript(init: BuildScript.() -> Unit) = initTag(BuildScript(), init)
  fun allprojects(init: AllProjects.() -> Unit) = initTag(AllProjects(), init)
  fun plugin(name: String) = initTag(Parameter("apply plugin:" to "'$name'"))
  fun android(init: Android.() -> Unit) = initTag(Android(), init)
  fun dependencies(init: Dependencies.() -> Unit) = initTag(Dependencies(), init)
}

class EmptyLine() : Element {
  override fun render(builder: StringBuilder, indent: String) {
    builder.append("\n")
  }
}

class Parameter(val parameter: Pair<String, String>) : Element {
  override fun render(builder: StringBuilder, indent: String) {
    builder.append("$indent${parameter.first} ${parameter.second}\n")
  }
}

class Method(val methodName: String) : Element {
  override fun render(builder: StringBuilder, indent: String) {
    builder.append("$indent$methodName()\n")
  }
}

class BuildScript : Tag("buildscript") {
  fun repositories(init: Repositories.() -> Unit) = initTag(Repositories(), init)
  fun dependencies(init: Dependencies.() -> Unit) = initTag(Dependencies(), init)
}

class Repositories : Tag("repositories") {
  fun repository(name: String, init: Method.() -> Unit = {}) = initTag(Method(name), init)
}

class Dependencies : Tag("dependencies") {
  fun classpath(value: String) = initTag(Parameter("classpath" to "\"$value\""))
  fun compile(value: String) = initTag(Parameter("compile" to "\"$value\""))
  fun apt(value: String) = initTag(Parameter("apt" to "\"$value\""))
  fun kapt(value: String) = initTag(Parameter("kapt" to "\"$value\""))
  fun debugCompile(value: String) = initTag(Parameter("debugCompile" to "\"$value\""))
  fun releaseCompile(value: String) = initTag(Parameter("releaseCompile" to "\"$value\""))
  fun testCompile(value: String) = initTag(Parameter("testCompile" to "\"$value\""))
  fun provided(value: String) = initTag(Parameter("provided" to "\"$value\""))
  fun androidTestCompile(value: String) = initTag(Parameter("androidTestCompile" to "\"$value\""))
}

class AllProjects : Tag("allprojects") {
  fun repository(name: String, init: Method.() -> Unit = {}) = initTag(Method(name), init)
}

class Android : Tag("android") {
  fun compileSdkVersion(compileSdkVersion: String) = initTag(Parameter("compileSdkVersion" to compileSdkVersion))
  fun buildToolsVersion(buildToolsVersion: String) = initTag(Parameter("buildToolsVersion" to buildToolsVersion))
  fun defaultConfig(init: DefaultConfig.() -> Unit) = initTag(DefaultConfig(), init)
  fun buildTypes(init: BuildTypes.() -> Unit) = initTag(BuildTypes(), init)
  fun sourceSets(init: SourceSets.() -> Unit) = initTag(SourceSets(), init)
}

class DefaultConfig : Tag("defaultConfig") {
  fun applicationId(appId: String) = initTag(Parameter("applicationId" to "\"$appId\""))
  fun minSdkVersion(minSdkVersion: String) = initTag(Parameter("minSdkVersion" to minSdkVersion))
  fun targetSdkVersion(targetSdkVersion: String) = initTag(Parameter("targetSdkVersion" to targetSdkVersion))
  fun versionCode(versionCode: Int) = initTag(Parameter("versionCode" to versionCode.toString()))
  fun versionName(versionName: String) = initTag(Parameter("versionName" to "\"$versionName\""))
}

class BuildTypes : Tag("buildTypes") {
  fun release(init: ReleaseConfig.() -> Unit) = initTag(ReleaseConfig(), init)
  fun debug(init: DebugConfig.() -> Unit) = initTag(DebugConfig(), init)
}

class SourceSets : Tag("sourceSets") {

}

class ReleaseConfig : Tag("release") {
  fun minifyEnabled(minifyEnabled: Boolean) = initTag(Parameter("minifyEnabled" to minifyEnabled.toString()))
  fun proguardFiles(fileName: String) = initTag(Parameter("proguardFiles" to "getDefaultProguardFile('proguard-android.txt'), '$fileName'"))
}

class DebugConfig : Tag("debug") {
  fun minifyEnabled(minifyEnabled: Boolean) = initTag(Parameter("minifyEnabled" to minifyEnabled.toString()))
  fun proguardFiles(fileName: String) = initTag(Parameter("proguardFiles" to "getDefaultProguardFile('proguard-android.txt'), '$fileName'"))
}

interface Element {
  fun render(builder: StringBuilder, indent: String)
}

abstract class Tag(val name: String = "") : Element {
  val children = arrayListOf<Element>()

  protected fun <T : Element> initTag(tag: T, init: T.() -> Unit = {}): T {
    tag.init()
    children.add(tag)
    return tag
  }

  fun Element.emptyLine() = initTag(EmptyLine())

  override fun render(builder: StringBuilder, indent: String) {
    if (children.isNotEmpty()) {
      builder.append("$indent$name {\n")
      for (child in children) {
        child.render(builder, "$indent\t")
      }
      builder.append("$indent}\n")
    }
  }

  override fun toString(): String {
    val builder = StringBuilder()
    render(builder, "")
    return builder.toString()
  }
}