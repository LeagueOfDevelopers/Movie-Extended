using System.Collections.Generic;
using Domain.Models;

namespace Domain.VisitorRepository
{
   public interface ICompanyRepository
    {
        IEnumerable<Company> GetAllCompanies();
        Company GetCompanyByCompanyId(int companyId);
        void deleteCompanyById(int companyId);
        void SaveCompany(Company company);

    }
}
