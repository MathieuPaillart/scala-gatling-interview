package io.gatling.interview.http.api.computers

import cats.effect.Effect
import io.finch._
import io.finch.circe._
import io.gatling.interview.dto.ComputerCreate
import io.gatling.interview.model.Computer
import io.gatling.interview.service.ComputerService

class ComputersController[F[_] : Effect](computerService: ComputerService[F]) extends Endpoint.Module[F] {

  private val computers: Endpoint[F, Seq[Computer]] = get("computers") {
    computerService.queryComputers()
  }

  private val computerCreate: Endpoint[F, Unit] = post("computers" :: jsonBody[ComputerCreate]) { computer: ComputerCreate =>
    //try
    computerService.createComputer(computer)
    /*    catch {
          case e: IllegalArgumentException =>
             NotFound(e)
        }*/
  }

  //weird syntax, this operator is called "space invader"
  private[api] val endpoints = computers :+: computerCreate
}
