using System;
using System.Security.Policy;
using Domain.Models;
using Extended_Movie.Visitor_Repository;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace CompanyRepositoryTests
{
    [TestClass]
    public class CompanyRepositoryTests
    {
        [TestMethod]
        public void SaveCompany()
        {
            var companyId = new Guid();
            var companyName = "company";
            var uri1 = new Uri("http://handynotes.ru/2009/09/uri-url-urn.html");
            
            var company = new Company(companyId,companyName,uri1);
            var provider = new SessionProvider();
            provider.OpenSession();
            using (var session = provider.GetCurrentSession())
            {
                var companyRepository = new CompanyRepository(session);
                companyRepository.SaveCompany(company);
            }
        }
        [TestMethod]
         public void GetAllCompanies()
          {
            var provider = new SessionProvider();
            provider.OpenSession();
            using (var session = provider.GetCurrentSession())
            {
                var companyRepository = new CompanyRepository(session);
                var test = companyRepository.GetAllCompanies();
            }

        }

        [TestMethod]
        public void GetCompanyByName()
        {
            var provider = new SessionProvider();
            provider.OpenSession();
            using (var session = provider.GetCurrentSession())
            {
                var companyRepository = new CompanyRepository(session);
                var test =  companyRepository.GetCompanyByName("company");
            }
        }
    }

   
    }

