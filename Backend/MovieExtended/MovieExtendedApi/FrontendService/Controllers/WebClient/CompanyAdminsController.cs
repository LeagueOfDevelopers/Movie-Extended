using System;
using System.Web.Http;
using Domain.Authorization;
using Domain.Repository;
using Journalist;

namespace FrontendService.Controllers.WebClient
{
    public class CompanyAdminsController:ApiController
    {
        private readonly ICompanyUserRepository _companyUserRepository;

        public CompanyAdminsController(ICompanyUserRepository companyUserRepository)
        {
            Require.NotNull(companyUserRepository , nameof(companyUserRepository));
            _companyUserRepository = companyUserRepository;
        }
        [AllowAnonymous]
        [HttpPut]
        [Route("register")]
        public IHttpActionResult RegisterNewAdminCinema([FromBody] Account account)
        {
            try
            {
                _companyUserRepository.CreateUser(account);
            }
            catch (Exception)
            {

                return Conflict();
            }
            return Ok();
        }


    }
}