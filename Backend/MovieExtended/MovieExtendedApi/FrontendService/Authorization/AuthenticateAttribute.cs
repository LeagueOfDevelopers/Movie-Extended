﻿using System.Net.Http.Headers;
using System.Threading;
using System.Threading.Tasks;
using System.Web.Http.Filters;
using System.Web.Http.Results;
using Domain.Authorization;
using Domain.Authorization_authentification;
using Journalist;

namespace FrontendService.Authorization
{
    public class AuthenticateAttribute : IAuthenticationFilter
    {
        private readonly IAuthorizer _authorizer;

        public AuthenticateAttribute(IAuthorizer authorizer)
        {
            Require.NotNull(authorizer, nameof(authorizer));

            _authorizer = authorizer;
        }

        public bool AllowMultiple => true;

        public async Task AuthenticateAsync(HttpAuthenticationContext context, CancellationToken cancellationToken)
        {
            var tokenString = context?.Request?.Headers?.Authorization?.Parameter;
            if (string.IsNullOrEmpty(tokenString))
            {
                SetupUnauthenticated();
                return;
            }

            var tokenInfo = _authorizer.GetTokenInfo(tokenString);
            if (tokenInfo == null)
            {
                context.ErrorResult = new UnauthorizedResult(
                    new AuthenticationHeaderValue[] { },
                    context.Request);
                await context.ErrorResult.ExecuteAsync(cancellationToken);
                SetupUnauthenticated();
                return;
            }

            var identity = new MovExtIdentity(tokenInfo.UserId, true);
            var principal = new MovExtPrincipal( identity);

            Thread.CurrentPrincipal = principal;
            context.Principal = principal;
        }

        public Task ChallengeAsync(HttpAuthenticationChallengeContext context, CancellationToken cancellationToken)
        {
            return Task.CompletedTask;
        }

        private void SetupUnauthenticated()
        {
            Thread.CurrentPrincipal = MovExtPrincipal.EmptyPrincipal;
        }
    }
}