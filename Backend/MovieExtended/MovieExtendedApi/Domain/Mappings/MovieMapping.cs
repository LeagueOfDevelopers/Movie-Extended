using Domain.Models.Entities;
using NHibernate.Mapping.ByCode;
using NHibernate.Mapping.ByCode.Conformist;

namespace Domain.Mappings
{
    public class MovieMapping : ClassMapping<Movie>
    {
        public MovieMapping()
        {
            Table("Movie");
            Id(model => model.Id, mapper =>
            {
                mapper.Column("Id");
                mapper.Generator(Generators.EnhancedTable);
            });
            Property(model => model.Name, mapper => mapper.Column("Name"));
            Set(movie => movie.Language, mapper =>
            {
               mapper.Table("Language");
               mapper.Cascade(Cascade.All);
            }); 
            Property(movie => movie.AndroidToken, mapper => mapper.Column("Token"));
            OneToOne(model => model.Poster , mapper =>
            {
                mapper.Cascade(Cascade.All);
            });
        }
    }
}