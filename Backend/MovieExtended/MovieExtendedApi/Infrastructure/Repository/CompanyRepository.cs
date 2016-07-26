﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Authentication;
using Domain.Models.Entities;
using Domain.Models.FrontendEntities;
using Domain.Repository;
using Journalist;
using NHibernate.Linq;

namespace Infrastructure.Repository
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

        public int CreateCinema(Cinema cinema, int companyId ,int AdminId)
        {
            var session = _provider.GetCurrentSession();
            var company = session.Query<Company>().SingleOrDefault(model => model.Id == companyId);
            if (company.IdAdmin== AdminId)
            {
            cinema.IdAdmin = AdminId;
            company.Cinema.Add(cinema);
            session.Update(company);
            return cinema.Id;
            }
            throw new AuthenticationException();
        }
    }
}