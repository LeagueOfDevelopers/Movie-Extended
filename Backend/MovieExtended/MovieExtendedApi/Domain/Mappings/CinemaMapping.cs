using Domain.Models.Entities;
using NHibernate.Mapping;
using NHibernate.Mapping.ByCode;
using NHibernate.Mapping.ByCode.Conformist;

namespace Domain.Mappings
{
    public class CinemaMapping : ClassMapping<Cinema>
    {
        public CinemaMapping()
        {
            Table("Cinema");
            Id(model => model.Id, mapper =>
            {
                mapper.Column("Id");
                mapper.Generator(Generators.Identity);
            });
            Property(model => model.Name, mapper => mapper.Column("Name"));
            Property(model => model.Address, mapper => mapper.Column("Address"));
            Bag(model =>model.Movie , mapper =>
            {
               mapper.Table("Movie"); 
               mapper.Cascade(Cascade.All);
            } );
        }
    }
}