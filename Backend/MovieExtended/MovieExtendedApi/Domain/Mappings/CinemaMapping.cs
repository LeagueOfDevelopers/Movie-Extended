using Domain.Models.Entities;
using NHibernate.Mapping.ByCode;
using NHibernate.Mapping.ByCode.Conformist;

namespace Domain.Mappings
{
    public class CinemaMapping : ClassMapping<Cinema>
    {
        public CinemaMapping()
        {
            Id(model => model.Id, mapper =>
            {
                mapper.Column("Id");
                mapper.Generator(Generators.EnhancedTable);
                
            });
            Property(model => model.Name, mapper => mapper.Column("Name"));
            Property(model => model.Address, mapper => mapper.Column("Address"));
            ManyToOne(model => model.Company, mapper =>
            {
                mapper.Column("Company");
                
            });
        }
    }
}