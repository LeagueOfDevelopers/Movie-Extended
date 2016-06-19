using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Principal;
using System.Text;
using System.Threading.Tasks;
using Journalist;

namespace Domain.Authorization
{
    public class MovExtPrinciple : IPrincipal
    {
        private readonly AccountRole _accountRole;

        public MovExtPrinciple(AccountRole accountRole, IIdentity identity)
        {
            Require.NotNull(identity, nameof(identity));

            _accountRole = accountRole;
            Identity = identity;
        }

        public bool IsEmpty { get; private set; }

        public static IPrincipal EmptyPrincipal
            => new MovExtPrinciple(AccountRole.User, MovExtIdentity.EmptyIdentity); { IsEmpty = true };

        public bool IsInRole(string role)
        {
            return !IsEmpty && _accountRole.ToString("G").Equals(role);
        }

        public IIdentity Identity { get; }

        public bool IsInRole(AccountRole role)
        {
            return (_accountRole == AccountRole.Administrator || _accountRole == role) && !IsEmpty;
        }
    }
}
