using System.Collections.Generic;
using Domain.Models.Entities;
using Domain.Models.FrontendEntities;

namespace Domain.Repository
{
    public interface ICompanyRepository
    {
        IEnumerable<Company> GetAllCompanies();
        Company GetCompanyByCompanyId(int companyId);
        void DeleteCompanyById(int companyId);
        void SaveCompany(Company company);
        void UpdateCompany(string jsonForUpdate);
        bool Exists(int companyId);
        int CreateCinema(Cinema cinema, int companyId);
    }
}