using System;
using Domain.Models.Entities;
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
            
                var companyRepository = new CompanyRepository(provider);
                companyRepository.SaveCompany(company);
                
            
        }
        [TestMethod]
        public void SaveFileData()
        {
            var saveFile = new File(@"~/AudioTrack/77.mp3", FileType.Track);

            var provider = new SessionProvider();
            provider.OpenSession();

            var fileRepository = new FileRepository(provider);
            fileRepository.SaveFileData(saveFile);
        }
    }
    }

