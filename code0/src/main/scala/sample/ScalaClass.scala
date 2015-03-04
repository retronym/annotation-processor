package sample

import java.util.Locale

@Annot
object ScalaClass {
  private val _locale = new ThreadLocal[Locale] {
    override def initialValue(): Locale = defaultLocale
  }
  def defaultLocale: Locale = null
}