using System.Collections.Generic;
using Domain.Models;

namespace Domain.VisitorRepository
{
   public interface ICompanyRepository
    {
        IEnumerable<Company> GetAllCompanies();
        Company GetCompanyByCompanyId(int companyId);
        void DeleteCompanyById(int companyId);
        void SaveCompany(Company company);

    }
}
