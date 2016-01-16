using Domain.Models;
using NHibernate.Mapping.ByCode;
using NHibernate.Mapping.ByCode.Conformist;

namespace Domain.Mappings
{
    public class CompanyMapping : ClassMapping<Company>
    {
        public CompanyMapping()
        {
            Id(model => model.Id, mapper =>
            {
                mapper.Column("Id");
                mapper.Generator(Generators.Guid);
            });
            Property(model => model.Name, mapper => mapper.Column("Name"));
            Property(model => model.Website, mapper => mapper.Column("Website"));
        }
    }
}
