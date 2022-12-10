package org.vliux.nycschools.util

fun String.isNumber(): Boolean {
  return this.all { char -> char.isDigit() }
}
