using System.Collections.Generic;
using System.Web.Http;
using Domain.Models.Entities;
using Domain.Repository;

namespace FrontendService.Controllers.WebClient
{
    public class CompanyController : ApiController
    {
        private readonly ICompanyRepository _companyRepository;

        public CompanyController(ICompanyRepository companyRepository)
        {
            _companyRepository = companyRepository;
        }

        [Route("api/Company/All")]
        [HttpGet]
        public IEnumerable<Company> GetAlLCompanies()
        {
            return _companyRepository.GetAllCompanies();
        }

        [Route("api/Company/New")]
        [HttpPost]
        public void SaveNewCompany([FromBody] Company company)
        {
            _companyRepository.SaveCompany(company);
        }

        [Route("api/Company/Get/{companyId}")]
        [HttpGet]
        public Company GetCompanyById(int companyId)
        {
            return _companyRepository.GetCompanyByCompanyId(companyId);
        }

        [Route("api/Company/Delete/{companyId}")]
        [HttpPost]
        public void DeleteCompanyById(int companyId)
        {
            _companyRepository.DeleteCompanyById(companyId);
        }
    }
}