package io.gatling.interview.dto

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

import java.time.LocalDate

object ComputerCreate {
  implicit val decoder: Decoder[ComputerCreate] = deriveDecoder
}

final case class ComputerCreate(name: String,
                                introduced: Option[LocalDate],
                                discontinued: Option[LocalDate],
                                companyId: Option[String]
                               )
