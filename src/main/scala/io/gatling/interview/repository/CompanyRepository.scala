package io.gatling.interview.repository

import cats.effect.Sync
import io.gatling.interview.model.Company

class CompanyRepository[F[_]](implicit F: Sync[F]) {
  val companiesList: Seq[Company] = {
    Seq(Company("ce94b983-e8d0-4161-b06f-b3d43f785d01", "Thinking Machines"), Company("913e0699-0792-4fc0-882b-535750fd9254", "Apple"))
  }

  def findAll(): F[Seq[Company]] = F.pure(
    companiesList
  )

  def findCompanyById(id: String): F[Company] = F.pure(
    companiesList.find(company => id.equals(company.id)).getOrElse(throw new IllegalArgumentException("No company found with id: " + id))
  )
}
