package core.entity

import tornadofx.getProperty
import tornadofx.property

class ProjectInfo {
  var appName by property<String>()
  fun appNameProperty() = getProperty(ProjectInfo::appName)

  var companyDomain by property<String>()
  fun companyDomainProperty() = getProperty(ProjectInfo::companyDomain)

  var packageName by property<String>()
  fun packageNameProperty() = getProperty(ProjectInfo::packageName)

  var projectLocation by property<String>()
  fun projectLocationProperty() = getProperty(ProjectInfo::projectLocation)

  var minSdk by property<String>()
  fun minSdkProperty() = getProperty(ProjectInfo::minSdk)

  var projectRoot = ""
  var androidTestRoot = ""
  var mainRoot = ""
  var testRoot = ""
  var srcDir = ""
}