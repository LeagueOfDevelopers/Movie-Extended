﻿using System.Linq;
using Domain.Models.Entities;
using Domain.Models.FrontendEntities;
using Domain.Repository;
using Journalist;
using NHibernate.Linq;

namespace Infrastructure.Repository
{
    class CompanyUserRepository : ICompanyUserRepository
    {
        private readonly SessionProvider _provider;
        public CompanyUserRepository(SessionProvider provider)
        {
            Require.NotNull(provider,nameof(SessionProvider));
            _provider = provider;
        }
        public void CreateUser(CompanyUser user)
        {
            var sesssion = _provider.GetCurrentSession();
            sesssion.BeginTransaction();
            sesssion.Save(user);
            sesssion.Transaction.Commit();

        }

        public int CheckCredentials(Credentials credentials)
        {
            var session = _provider.GetCurrentSession();
            var user = session.Query<CompanyUser>().SingleOrDefault(companyUser =>
            
                companyUser.Email == credentials.Email&&
                companyUser.MD5Hash == credentials.Pass
            );
            return user.Id;
        }
    }
}
