using Domain.Repository;
using Infrastructure.VisitorRepository;
using Journalist;

namespace Infrastructure.Repository
{
    public class QrCodeRepository : IQrCodeRepository
    {
        private readonly SessionProvider _provider;

        public QrCodeRepository(SessionProvider provider)
        {
            Require.NotNull(provider, nameof(SessionProvider));
            _provider = provider;
        }

        public void SaveQrCodeFingeprint(string qrCodeFingerprint)
        {
            var session = _provider.GetCurrentSession();
            session.BeginTransaction();
            session.Save(qrCodeFingerprint);
            session.Transaction.Commit();
        }
    }
}