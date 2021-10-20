package io.gatling.interview.http.api

import io.gatling.interview.http.api.computers.ComputersController
import cats.effect.{ContextShift, Effect}
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import io.finch._
import io.finch.circe._
import io.gatling.interview.service.ComputerService

class ComputerDatabaseApi[F[_]: Effect: ContextShift](computerService: ComputerService[F]) extends Endpoint.Module[F] {

  private val computersApi: ComputersController[F] = new ComputersController[F](computerService)

  val service: Service[Request, Response] =
    Endpoint.toService(
      Bootstrap
        .serve[Application.Json](computersApi.endpoints)
        .compile

    )
}
