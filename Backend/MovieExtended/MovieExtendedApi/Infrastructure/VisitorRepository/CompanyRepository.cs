using System;
using System.Collections.Generic;
using System.Linq;
using Domain.Models.Entities;
using Domain.VisitorRepository;
using Journalist;
using NHibernate.Linq;

namespace Infrastructure.VisitorRepository
{
    public class CompanyRepository : ICompanyRepository
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

        public void DeleteCompanyById(int companyId)
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
            throw new NotImplementedException();
        }

        public bool Exists(int companyId)
        {
            var session = _provider.GetCurrentSession();
            var checkIfExists = session.Query<Company>().SingleOrDefault(company => company.Id == companyId);
            return checkIfExists != null;
        }
    }
}