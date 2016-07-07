using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using Domain.Authorization_authentification;
using Domain.Models;
using Domain.Repository;
using Journalist;

namespace Domain.Authorization
{
   public class Authorizer : IAuthorizer
    {
        public TimeSpan TokenLifeTime { get; }

        public AuthorizationTokenInfo Authorize(string email, Password password)
        {
            Require.NotEmpty(email, nameof(email));
            Require.NotNull(password, nameof(password));
            var userAccount = _userRepository
                .GetAllAccounts(
                    account => account.Email.Address == email
                              )
                .SingleOrDefault();
            if (userAccount == null)
            {
                throw new Exception("There is no account with such email");
            }

            if (userAccount.Password.Value != password.Value)
            {
                throw new UnauthorizedAccessException("Wrong password");
            }

            var existantToken = TakeTokenByUserId(userAccount.UserId);
            if (existantToken != null)
            {
                return existantToken;
            }

            var token = GenerateNewToken(userAccount);
            _tokensWithGenerationTime.AddOrUpdate(token.Token, token, (oldToken, info) => token);
            return token;
        }


        public AuthorizationTokenInfo GetTokenInfo(string authorizationToken)
        {
            Require.NotEmpty(authorizationToken, nameof(authorizationToken));

            if (!_tokensWithGenerationTime.ContainsKey(authorizationToken))
            {
                return null;
            }
            var token = _tokensWithGenerationTime[authorizationToken];

            if (token.CreationTime + TokenLifeTime < DateTime.Now)
            {
                _tokensWithGenerationTime.TryRemove(token.Token, out token);
                return null;
            }

            token.CreationTime = DateTime.Now;
            return token;
        }

        public Authorizer(TimeSpan tokenLifeTime, ICompanyUserRepository userRepository)
        {
            Require.NotNull(userRepository, nameof(userRepository));

            TokenLifeTime = tokenLifeTime;
            _userRepository = userRepository;
        }

        private static AuthorizationTokenInfo GenerateNewToken(Account account)
        {
            var guid = Guid.NewGuid();
            var token = BitConverter.ToString(guid.ToByteArray());
            token = token.Replace("-", "");
            return new AuthorizationTokenInfo(account.UserId, token, DateTime.Now);
        }

        private AuthorizationTokenInfo TakeTokenByUserId(int userId)
        {
            var pair = _tokensWithGenerationTime.SingleOrDefault(token => token.Value.UserId == userId);
            if (!pair.Equals(default(KeyValuePair<string, AuthorizationTokenInfo>)))
            {
                return pair.Value;
            }

            return null;
        }

        private readonly ConcurrentDictionary<string, AuthorizationTokenInfo> _tokensWithGenerationTime
= new ConcurrentDictionary<string, AuthorizationTokenInfo>();

        private readonly ICompanyUserRepository _userRepository;
    }
}
