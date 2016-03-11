namespace Domain.Repository
{
    public interface IQrCodeRepository
    {
        void SaveQrCodeFingeprint(string qrCodeFingerprint);
    }
}