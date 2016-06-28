using System;
using System.Collections.Generic;
using Domain.Authorization;
using Domain.Models.Entities;
using Domain.Models.FrontendEntities;

namespace Domain.Repository
{
   public interface ICompanyUserRepository
    {
        void CreateUser(CompanyUser user);
        int CheckCredentials(Credentials credentials);
        List<Account> GetAllAccounts(Func<Account , bool> predicate);
    }
}
