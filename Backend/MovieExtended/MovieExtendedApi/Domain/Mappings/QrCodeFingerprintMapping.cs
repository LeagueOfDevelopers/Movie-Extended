using Domain.Models.Entities;
using NHibernate.Mapping.ByCode;
using NHibernate.Mapping.ByCode.Conformist;

namespace Domain.Mappings
{
    public class QrCodeFingerprintMapping : ClassMapping<QrCodeFingerprint>
    {
        public QrCodeFingerprintMapping()
        {
            Id(model => model.Id, mapper =>
            {
                mapper.Column("Id");
                mapper.Generator(Generators.Identity);
            });
            Property(model => model.Value, mapper => mapper.Column("Value"));
        }
    }
}