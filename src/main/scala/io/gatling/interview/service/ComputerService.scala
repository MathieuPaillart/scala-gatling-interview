package io.gatling.interview.service

import cats.effect.Sync
import cats.implicits._
import io.finch.{Ok, Output}
import io.gatling.interview.dto.ComputerCreate
import io.gatling.interview.model.Computer
import io.gatling.interview.repository.{CompanyRepository, ComputerRepository}

class ComputerService[F[_]](computerRepository: ComputerRepository[F], companyRepository: CompanyRepository[F])(implicit F: Sync[F]) {

  def queryComputers(): F[Output[Seq[Computer]]] = {
    val companies = companyRepository.companiesList
    computerRepository.findAll(companies).map(Ok)
  }

  def createComputer(computerCreate: ComputerCreate): F[Output[Unit]] = {
    val companyId = computerCreate.companyId
    val uuid = java.util.UUID.randomUUID.toString
    if (companyId.isDefined) {
      //Here it will throw an exception if it doesn't exist, should have been caught by the calling method but doesn't work.
      companyRepository.findCompanyById(companyId.fold("") { companyId => companyId })
    }
    val computer = Computer(uuid, computerCreate.name, computerCreate.introduced, computerCreate.discontinued, companyId)
    computerRepository.save(computer).map(Ok)
  }

}