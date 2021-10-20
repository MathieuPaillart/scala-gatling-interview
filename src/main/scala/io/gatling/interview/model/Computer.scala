package io.gatling.interview.model

import io.circe._
import io.circe.generic.semiauto._
import io.circe.syntax.EncoderOps

import java.time.LocalDate
import java.time.temporal._

object Computer {
  implicit val decoder: Decoder[Computer] = deriveDecoder[Computer]
  implicit val encoder: Encoder[Computer] = (computer: Computer) => {
    deriveEncoder[Computer]
      .apply(computer)
      .mapObject(json => json.add("lifetime", computer.lifetime.asJson))
      .deepDropNullValues
  }
}

final case class Computer(
                           id: String,
                           name: String,
                           introduced: Option[LocalDate],
                           discontinued: Option[LocalDate]
                         ) {
  val lifetime: Option[Long] = {
    if (introduced.isDefined && discontinued.isDefined) {
      Option(ChronoUnit.MONTHS.between(introduced.get, discontinued.get))
    } else {
      None
    }
  }

}