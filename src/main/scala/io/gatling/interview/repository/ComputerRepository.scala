package io.gatling.interview.repository

import cats.effect.Sync
import io.circe
import io.circe.syntax.EncoderOps
import io.gatling.interview.model.{Company, Computer}

import java.nio.file.{Files, Paths}
import scala.io.Source
import scala.util.Using
class ComputerRepository[F[_]](implicit F: Sync[F]) {
  private val computersList: Seq[Computer] = {
    val bufferedSource = Source.fromResource("data/computers.json")
    Using(bufferedSource) { reader =>
      val rawJson = reader.mkString
      val parseResult = circe.parser.parse(rawJson)
      parseResult match {
        case Left(parsingError) =>
          throw new IllegalArgumentException(s"Invalid JSON object: ${parsingError.message}")
        case Right(json) =>
          circe.parser.decode[Seq[Computer]](json.toString()).getOrElse(throw new IllegalArgumentException(s"The json found isn't a list of computers: $json"))
      }
    }.get
  }

  def findAll(companies: Seq[Company]): F[Seq[Computer]] = F.pure {
    var computers: Seq[Computer] = Seq.empty
    computersList.foreach(computer => {
      if (computer.companyId.isDefined) {
        val company = companies.filter(company => company.id.equals(computer.companyId.get)).head
        computer.setCompanyName(company.name)
      }
      computers = computers :+ computer
    })
    computers
  }

  def save(computer: Computer): F[Unit] = F.pure {
    val newList = computersList :+ computer
    Files.writeString(Paths.get("src/main/resources/data/computers.json"), newList.asJson.toString())
  }
}
