using System;
using System.Security.Principal;
using Journalist;

namespace Domain.Authorization
{
    public class MovExtPrincipal : IPrincipal
    {
        public IIdentity Identity { get; }

        public bool IsInRole(string role)
        {
            throw new NotImplementedException();
        }

        public MovExtPrincipal(IIdentity identity)
        {
            Require.NotNull(identity, nameof(identity));
            Identity = identity;
        }

        public bool IsEmpty { get; private set; }

        public static IPrincipal EmptyPrincipal
=> new MovExtPrincipal(MovExtIdentity.EmptyIdentity) { IsEmpty = true };
    }
}
