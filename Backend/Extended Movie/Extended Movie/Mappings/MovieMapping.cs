using Extended_Movie.Models;
using NHibernate.Mapping.ByCode;
using NHibernate.Mapping.ByCode.Conformist;

namespace Extended_Movie.Mappings
{
    public class MovieMapping : ClassMapping<Movie>
    {
        public MovieMapping()
        {
            Id(model => model.Id, mapper =>
            {
                mapper.Column("Id");
                mapper.Generator(Generators.Guid);
            });
            Property(model => model._name, mapper => mapper.Column("Name"));
            Property(model => model._cinemaId, mapper => mapper.Column("CinemaId"));
        }
    }
}