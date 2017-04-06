package com.github.johnynek.paiges
import scala.meta._

import java.io.File

import org.scalameta.logger
class Scalafmt extends org.scalatest.FunSuite {
  import Doc._
  def toDoc(tree: Tree): Doc = tree match {
    case Lit(_) => str(tree.syntax)
    case Term.Name(name) => text(name)
    case Term.Select(qual, name) =>
      toDoc(qual) +: "." +: toDoc(name)
    case Term.Apply(fun, args) =>
      toDoc(fun) +:
        Doc.fill(comma, args.map(toDoc)).bracketBy(text("("), text(")"))
  }
  test("basic") {
    val build =
      new File(
        "./core/src/test/scala/com/github/johnynek/paiges/Scalafmt.scala")
        .parse[Source]
        .get
        .structure
        .parse[Stat]
        .get
    logger.elem(build, toDoc(build).render(80))
    logger.elem(toDoc(q"""




         a.b.c(1, 2


         , 3, bar(1, 2, 43), 5, 5)""").render(14))
  }
}
