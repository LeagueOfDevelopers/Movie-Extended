using System.Collections.Generic;
using Domain.Models;
using Domain.Models.Entities;

namespace Domain.VisitorRepository
{
   public interface ICompanyRepository
    {
       IEnumerable<Company> GetAllCompanies();
       Company GetCompanyByCompanyId(int companyId);
       void DeleteCompanyById(int companyId);
       void SaveCompany(Company company);
       void UpdateCompany(string jsonForUpdate);
       bool Exists(int companyId);
    }
}
