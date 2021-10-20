package io.gatling.interview.service

import cats.effect.Sync
import cats.implicits._
import io.finch.{Ok, Output}
import io.gatling.interview.dto.ComputerCreate
import io.gatling.interview.model.Computer
import io.gatling.interview.repository.ComputerRepository

class ComputerService[F[_]](computerRepository: ComputerRepository[F])(implicit F: Sync[F]) {

  def queryComputers(): F[Output[Seq[Computer]]] =
    computerRepository.findAll().map(Ok)

  def createComputer(computerCreate: ComputerCreate): F[Output[Unit]] = {
    val uuid = java.util.UUID.randomUUID.toString
    val computer = Computer(uuid, computerCreate.name, computerCreate.introduced, computerCreate.discontinued)
    computerRepository.save(computer).map(Ok)
  }

}