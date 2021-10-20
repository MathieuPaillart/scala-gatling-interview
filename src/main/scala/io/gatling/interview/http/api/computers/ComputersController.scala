package io.gatling.interview.http.api.computers

import io.gatling.interview.model.{Computer, ComputerCreate}
import io.finch.circe._
import cats.effect.Effect
import io.finch._
import io.gatling.interview.service.ComputerService

class ComputersController[F[_]: Effect](computerService: ComputerService[F]) extends Endpoint.Module[F] {

  private val computers: Endpoint[F, Seq[Computer]] = get("computers") {
    computerService.queryComputers()
  }

  private val computerCreate: Endpoint[F, Unit] = post("computers" :: jsonBody[ComputerCreate]) { computer : ComputerCreate =>
    computerService.createComputer(computer)
  }

  //weird syntax, this operator is called "space invader"
  private[api] val endpoints = computers :+: computerCreate
}
