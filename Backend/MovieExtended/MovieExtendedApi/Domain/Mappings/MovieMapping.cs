using Domain.Models.Entities;
using NHibernate.Mapping.ByCode;
using NHibernate.Mapping.ByCode.Conformist;

namespace Domain.Mappings
{
    public class MovieMapping : ClassMapping<Movie>
    {
        public MovieMapping()
        {
            Id(model => model.Id, mapper =>
            {
                mapper.Column("Id");
                mapper.Generator(Generators.EnhancedTable);
            });
            Property(model => model.Name, mapper => mapper.Column("Name"));
            ManyToOne(model => model.Cinema, mapper =>
            {
                mapper.Column("CinemaId");
                mapper.Cascade(Cascade.All);
                
            });
            Property(movie => movie.AndroidToken, mapper => mapper.Column("Token"));
        }
    }
}