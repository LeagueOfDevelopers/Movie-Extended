namespace Domain.Models
{
    public class TokenForQrCode
    {
        public TokenForQrCode(string value)
        {
            Value = value;
        }

        public string Value { get; private set; }
    }
}