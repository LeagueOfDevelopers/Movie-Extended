using Domain.Models.Entities;
using Domain.Models.FrontendEntities;

namespace Domain.Repository
{
   public interface ICompanyUserRepository
    {
        void CreateUser(CompanyUser user);
        int CheckCredentials(Credentials credentials);
    }
}
