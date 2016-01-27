using System;
using System.Security.Policy;
using Domain.Models;
using Extended_Movie.Visitor_Repository;
using Infrastructure.VisitorRepository;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace CompanyRepositoryTests
{
    [TestClass]
    public class CompanyRepositoryTests
    {
        [TestMethod]
        public void SaveCompany()
        {
            var companyName = "company";
            var uri1 = new Uri("http://handynotes.ru/2009/09/uri-url-urn.html");
            
            var company = new Company(7,companyName,uri1);
            var provider = new SessionProvider();
            provider.OpenSession();
            using (var session = provider.GetCurrentSession())
            {
                var companyRepository = new CompanyRepository(session);
                companyRepository.SaveCompany(company);
                
            }
        }
    }
    }

