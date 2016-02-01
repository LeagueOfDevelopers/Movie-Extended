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
    class QrCodeFingerprint : ClassMapping<QrCodeFingerprint>
    {
        public QrCodeFingerprint()
        {
            Id(model => model.Id, mapper =>
            {
                mapper.Column("Id");
                mapper.Generator(Generators.EnhancedTable);
            });
            Property(model => model.Name, mapper => mapper.Column("Name"));
            Property(model => model.CinemaId, mapper => mapper.Column("CinemaId"));
        }
    }
}
