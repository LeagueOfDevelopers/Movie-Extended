using System.Collections.Generic;
using System.Linq;
using Domain.Models;
using Domain.VisitorRepository;
using NHibernate;
using NHibernate.Linq;
using Journalist;

namespace Infrastructure.VisitorRepository
{
    public class CompanyRepository :ICompanyRepository
    {
        private readonly SessionProvider _provider;

        public CompanyRepository(SessionProvider provider)
        {
            Require.NotNull(provider, nameof(SessionProvider));
            _provider = provider;
        }
        
        public IEnumerable<Company> GetAllCompanies()
        {

            var session = _provider.GetCurrentSession();
            return session.Query<Company>();
        }

        public Company GetCompanyByCompanyId(int companyId)
        {
            var session = _provider.GetCurrentSession();
            return session.Query<Company>().SingleOrDefault(company => company.Id == companyId);
        }

        public void deleteCompanyById(int companyId)
        {
            var session = _provider.GetCurrentSession();
            var checkIfExists = session.Query<Company>().SingleOrDefault(company => company.Id == companyId);
            if (checkIfExists != null)
            {
                session.BeginTransaction();
                session.Delete(checkIfExists);
                session.Transaction.Commit();
            }
        }

        public void SaveCompany(Company company)
        {
            var session = _provider.GetCurrentSession();
            session.BeginTransaction();
            session.Save(company);
            session.Transaction.Commit();
        }

        public void UpdateCompany(string jsonForUpdate)
        {
            throw new System.NotImplementedException();
        }
    }
}