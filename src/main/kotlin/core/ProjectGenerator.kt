package core

import core.entity.ProjectInfo

object ProjectGenerator {

  val packageGenerator = PackageGenerator()
  val gradleGenerator = GradleGenerator()
  val manifestGenerator = ManifestGenerator()

  fun createProject(info: ProjectInfo) {
    with(info) {
      projectRoot = "$projectLocation/$appName"
      srcDir = "$projectRoot/app/src"
      androidTestRoot = "$srcDir/androidTest"
      mainRoot = "$srcDir/main"
      testRoot = "$srcDir/test"
      packageGenerator.createPackageStructure(this)
      gradleGenerator.createRootBuildFile(this)
      gradleGenerator.createAppBuildGradle(this)
      manifestGenerator.createAndroidManifest(this)
    }
  }
}