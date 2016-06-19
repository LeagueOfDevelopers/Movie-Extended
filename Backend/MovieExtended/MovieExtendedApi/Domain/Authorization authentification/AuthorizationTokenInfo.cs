using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Journalist;

namespace Domain.Authorization_authentification
{
    class AuthorizationTokenInfo
    {
        public AuthorizationTokenInfo(int userId, string token, DateTime creationTime)
        {
            Require.Positive(userId, nameof(userId));
            Require.NotEmpty(token, nameof(token));

            UserId = userId;
            Token = token;
            CreationTime = creationTime;
        }

        public int UserId { get; private set; }

        public string Token { get; private set; }

        public DateTime CreationTime { get; set; }
    }
}
