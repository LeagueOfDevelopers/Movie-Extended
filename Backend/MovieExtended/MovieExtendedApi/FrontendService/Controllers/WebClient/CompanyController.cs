using System.Collections.Generic;
using System.Web.Http;
using Domain.Models.Entities;
using Domain.Models.FrontendEntities;
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

        [Route("company/all")]
        [HttpGet]
        public IEnumerable<Company> GetAlLCompanies()
        {
            return _companyRepository.GetAllCompanies();
        }

        [Route("company/new")]
        [HttpPost]
        public void SaveNewCompany([FromBody] Company company)
        {
            _companyRepository.SaveCompany(company);
        }

        [Route("company/get/{companyId}")]
        [HttpGet]
        public Company GetCompanyById(int companyId)
        {
            return _companyRepository.GetCompanyByCompanyId(companyId);
        }

        [Route("create/cinema/{companyId}")]
        [HttpPost]
        public void CreateCinema([FromBody] Cinema cinema , int companyId )
        {
            _companyRepository.CreateCinema(cinema , companyId);
        }

        [Route("company/delete/{companyId}")]
        [HttpPost]
        public void DeleteCompanyById(int companyId)
        {
            _companyRepository.DeleteCompanyById(companyId);
        }
    }
}