namespace Domain.VisitorRepository
{
    public interface IQrCodeRepository
    {
        void SaveQrCodeFingeprint(string qrCodeFingerprint);
    }
}
