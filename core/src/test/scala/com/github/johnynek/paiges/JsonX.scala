package com.github.johnynek.paiges

import com.github.johnynek.paiges.Json._
import org.scalameta.logger
import org.scalatest.FunSuite

class JsonX extends FunSuite {
  test("x") {
    val arr = JArray(
      1.to(6)
        .map { i =>
          logger.elem(i)
          val obj = JObject(
            1.to(i)
              .map { j =>
                logger.elem(j)
                "a" * j -> JNumber(j.toDouble)
              }
              .toMap)
          logger.elem(obj)
          obj
        }
        .toVector)
    println(arr.toDoc.render(30))
  }
}
