using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Extended_Movie.Models;
using NHibernate;
using NHibernate.Linq;

namespace Extended_Movie.Visitor_Repository
{
    public class CompanyRepository :ICompanyRepository
    {
        private readonly ISession session;
        public IEnumerable<Company> GetAllCompanies()
        {
            return session.Query<Company>();
        }

        public Company GetCompanyByCompanyId(Guid? companyId)
        {
            return session.Query<Company>().SingleOrDefault(company => company.Id == companyId);
        }

        public void deleteCompanyById(Guid? companyId)
        {
            var checkIfExists = session.Query<Company>().SingleOrDefault(company => company.Id == companyId);
            if (checkIfExists != null) session.Delete(checkIfExists);
        }

        public void SaveCompany(Company company)
        {
            session.BeginTransaction();
            session.SaveOrUpdate(company);
            session.Transaction.Commit();
        }
    }
}