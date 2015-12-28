﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Routing;
using Extended_Movie.Models;
using Extended_Movie.Visitor_Repository;
using Newtonsoft.Json;

namespace Extended_Movie.Controllers.WebClient
{
    public class CompanyController:ApiController
    {
        private readonly CompanyRepository companyRepository;

        public CompanyController()
        {
            companyRepository = new CompanyRepository();
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
        public Company GetCompanyById(Guid? companyId)
        {
            return companyRepository.GetCompanyByCompanyId(companyId);
        }

        [Route("api/Company/Delete/{companyId")]
        [HttpGet]
        public void DeleteCompanyById(Guid? companyId)
        {
            companyRepository.deleteCompanyById(companyId);
        }
    }
}