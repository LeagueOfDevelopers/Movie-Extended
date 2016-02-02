using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
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
                mapper.Generator(Generators.EnhancedTable);
            });
            Property(model => model.Value, mapper => mapper.Column("Value"));
        }
    }
}
