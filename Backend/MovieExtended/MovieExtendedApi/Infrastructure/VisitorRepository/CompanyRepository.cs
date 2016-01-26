using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Domain.Models;
using NHibernate;
using NHibernate.Linq;

namespace Extended_Movie.Visitor_Repository
{
    public class CompanyRepository :ICompanyRepository
    {
        public CompanyRepository(ISession session)
        {
            _session = session;
        }
        private readonly ISession _session;
        public IEnumerable<Company> GetAllCompanies()
        {
            return _session.Query<Company>();
        }

        public Company GetCompanyByCompanyId(string companyId)
        {
            return _session.Query<Company>().SingleOrDefault(company => company.Id == companyId);
        }

        public void deleteCompanyById(string companyId)
        {
            var checkIfExists = _session.Query<Company>().SingleOrDefault(company => company.Id == companyId);
            if (checkIfExists != null)
            {
                _session.BeginTransaction();
                _session.Delete(checkIfExists);
                _session.Transaction.Commit();
            }
        }

        public void SaveCompany(Company company)
        {
            _session.BeginTransaction();
            _session.Save(company);
            _session.Transaction.Commit();
        }

        public Company GetCompanyByName(string companyName)
        {
            return _session.Query<Company>().SingleOrDefault(company => company.Name == companyName);
        }
    }
}