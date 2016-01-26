using System;
using System.Collections.Generic;
using Domain.Models;

namespace Extended_Movie.Visitor_Repository
{
   public interface ICompanyRepository
    {
        IEnumerable<Company> GetAllCompanies();
        Company GetCompanyByCompanyId(string companyId);
        void deleteCompanyById(string companyId);
        void SaveCompany(Company company);
       Company GetCompanyByName(string name);

    }
}
