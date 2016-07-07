using System;
using System.Net;
using System.Web.Http;
using Domain.Authorization_authentification;
using Domain.Models;
using Domain.Models.FrontendEntities;
using Journalist;

namespace FrontendService.Controllers.WebClient
{
    [AllowAnonymous]
    public class AuthorizationController : ApiController
    {
        private readonly IAuthorizer _authorizer;

        public AuthorizationController(IAuthorizer authorizer)
        {
            Require.NotNull(authorizer, nameof(authorizer));
            _authorizer = authorizer;
        }

        [HttpPost]
        [Route("login")]
        public AuthorizationTokenInfo Authorize([FromBody] Credentials credentials)
        {
            try
            {
                var token = _authorizer.Authorize(credentials.Email, new Password(credentials.Password));
                return token;
            }
                           
            catch (UnauthorizedAccessException)
            {
                throw new HttpResponseException(HttpStatusCode.Unauthorized);
            }
        }
    }
}
