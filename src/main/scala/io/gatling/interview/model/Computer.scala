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
      .mapObject(json => json.add("companyName", computer.companyName.asJson))
      .deepDropNullValues
  }
}

final case class Computer(
                           id: String,
                           name: String,
                           introduced: Option[LocalDate],
                           discontinued: Option[LocalDate],
                           companyId: Option[String],
                         ) {
  val lifetime: Option[Long] = {
    if (introduced.isDefined && discontinued.isDefined) {
      Option(ChronoUnit.MONTHS.between(introduced.fold(LocalDate.MIN)(localDate => localDate), discontinued.fold(LocalDate.MIN) { localDate => localDate }))
    } else {
      None
    }
  }
  //val is immutable so can't do that, var is disabled so not right either. Design flow here
  var companyName: Option[String] = None

  def setCompanyName(name: String): Unit = {
    companyName = Option(name)
  }
}