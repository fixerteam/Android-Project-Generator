package ui

import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.px

class Style : Stylesheet() {
  init {
    s(form) {
      padding = box(25.px)
      prefWidth = 450.px
    }
  }
}