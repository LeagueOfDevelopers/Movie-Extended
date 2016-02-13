using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using NHibernate.Mapping.ByCode;
using NHibernate.Mapping.ByCode.Conformist;
using Domain.Models.Entities;

namespace Domain.Mappings
{
    public class AndroidTokenMapping:ClassMapping<AndroidToken>
    {
        public AndroidTokenMapping()
        {
            Table("Android Token");
            Id(tokenMapping => tokenMapping.Id, mapper =>
            {
                mapper.Generator(Generators.EnhancedTable);
            });
            //Property(token => token.);
        }
    }
}
