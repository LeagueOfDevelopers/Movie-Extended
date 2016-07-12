using Domain.Models.Entities;
using NHibernate.Mapping.ByCode;
using NHibernate.Mapping.ByCode.Conformist;

namespace Domain.Mappings
{
    public class CompanyMapping : ClassMapping<Company>
    {
        public CompanyMapping()
        {
            Table("Company");
            Id(model => model.Id, mapper =>
            {
                mapper.Column("Id");
                mapper.Generator(Generators.Identity);
            });
            Property(model => model.Name, mapper => mapper.Column("Name"));
            Property(model => model.Website, mapper => mapper.Column("Website"));
            Set(company => company.Cinema , mapper =>
            {
                mapper.Cascade(Cascade.All);
                mapper.Key(cinema => cinema.Column("Cinema") );
            },relation =>relation.OneToMany() );
            Property(company => company.IdAdmin , mapper => mapper.Column("IdAdmin"));
        }
    }
}