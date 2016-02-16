namespace Domain.Models.Entities
{
    public class QrCodeFingerprint
    {
        protected QrCodeFingerprint()
        {
        }

        public QrCodeFingerprint(string value)
        {
            Value = value;
        }

        public virtual string Value { get; protected set; }
        public virtual string Id { get; protected set; }
    }
}