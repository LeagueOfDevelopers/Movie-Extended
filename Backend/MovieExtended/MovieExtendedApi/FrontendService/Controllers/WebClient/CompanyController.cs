using System.Collections.Generic;
using System.Web.Http;
using Domain.Models;
using Domain.VisitorRepository;
using Newtonsoft.Json;

namespace FrontendService.Controllers.WebClient
{
    public class CompanyController:ApiController
    {
        private readonly ICompanyRepository companyRepository;

        public CompanyController(ICompanyRepository companyRepository)
        {
            this.companyRepository = companyRepository;
        }

        [Route("api/Company/All")]
        [HttpGet]
        public IEnumerable<Company> GetAlLCompanies()
        {
            return companyRepository.GetAllCompanies();
        }

        [Route("api/Company/New/{json}")]
        [HttpGet]
        public void SaveNewCompany(string json)
        {
            var newCompany = JsonConvert.DeserializeObject<Company>(json);
            companyRepository.SaveCompany(newCompany);
        }

        [Route("api/Company/Get/{companyId}")]
        [HttpGet]
        public Company GetCompanyById(int companyId)
        {
            return companyRepository.GetCompanyByCompanyId(companyId);
        }

        [Route("api/Company/Delete/{companyId}")]
        [HttpGet]
        public void DeleteCompanyById(int companyId)
        {
            companyRepository.deleteCompanyById(companyId);
        }
    }
}