package com.github.johnynek.paiges
import scala.meta._

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

import org.scalameta.logger
class Scalafmt extends org.scalatest.FunSuite {
  import Doc._
  def toDoc(tree: Tree): Doc = tree match {
    case Lit(_) => str(tree.syntax)
    case Term.Name(name) => text(name)
    case Term.Select(qual, name) =>
      toDoc(qual) + text(".") + toDoc(name)
    case Term.Apply(fun, args) =>
      toDoc(fun) +
        Doc.fill(comma, args.map(toDoc)).bracketBy(text("("), text(")"))
  }
  test("basic") {
    val build =
      new File("./core/src/main/scala/com/github/johnynek/paiges/Doc.scala")
        .parse[Source]
        .get
        .structure
        .parse[Stat]
        .get
    val doc = toDoc(build).render(100)
    logger.elem(doc)
    Files.write(Paths.get("target", "paiges.scala"), doc.getBytes())
  }
}
