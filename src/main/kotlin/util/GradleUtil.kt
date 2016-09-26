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
}

class AllProjects : Tag("allprojects") {
  fun repository(name: String, init: Method.() -> Unit = {}) = initTag(Method(name), init)
}

class Android : Tag("android") {
  fun defaultConfig(init: DefaultConfig.() -> Unit) = initTag(DefaultConfig(), init)
  fun buildTypes(init: BuildTypes.() -> Unit) = initTag(BuildTypes(), init)
  fun sourceSets(init: SourceSets.() -> Unit) = initTag(SourceSets(), init)
}

class DefaultConfig : Tag("defaultConfig") {

}

class BuildTypes : Tag("buildTypes") {

}

class SourceSets : Tag("sourceSets") {

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