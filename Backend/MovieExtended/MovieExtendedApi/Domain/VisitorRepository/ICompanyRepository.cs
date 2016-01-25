using System;
using System.Collections.Generic;
using Domain.Models;

namespace Extended_Movie.Visitor_Repository
{
   public interface ICompanyRepository
    {
        IEnumerable<Company> GetAllCompanies();
        Company GetCompanyByCompanyId(Guid? companyId);
        void deleteCompanyById(Guid? companyId);
        void SaveCompany(Company company);
       Company GetCompanyByName(string name);

    }
}
