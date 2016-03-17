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
                mapper.Generator(Generators.Identity);
            });
            Property(model => model.Name, mapper => mapper.Column("Name"));
            Set(movie => movie.Language, mapper =>
            {
               mapper.Cascade(Cascade.All);
                mapper.Key(language => language.Column("Language") );
            }
             ,relation => relation.OneToMany()
            ); 
            Property(movie => movie.AndroidToken, mapper => mapper.Column("Token"));
            ManyToOne(movie => movie.Poster,mapper => mapper.Cascade(Cascade.All));
           
        }
    }
}