package io.gatling.interview.model

import java.time.LocalDate

import io.circe._
import io.circe.generic.semiauto._

object Computer {
  implicit val decoder: Decoder[Computer] = deriveDecoder[Computer]
  implicit val encoder: Encoder[Computer] = deriveEncoder[Computer]
}

final case class Computer(
                           id: String,
                           name: String,
                           introduced: Option[LocalDate],
                           discontinued: Option[LocalDate]
                         )

object ComputerCreate {
  implicit val decoder: Decoder[ComputerCreate] = deriveDecoder
}

final case class ComputerCreate(name: String,
                                introduced: Option[LocalDate],
                                discontinued: Option[LocalDate]
                               )
