namespace Domain.Models
{
    public interface IQrCodeGenerator
    {
        string Generate(int movieId, int companyId);
    }
}