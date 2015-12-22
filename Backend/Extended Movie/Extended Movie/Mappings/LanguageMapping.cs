using Extended_Movie.Models;
using NHibernate.Mapping.ByCode;
using NHibernate.Mapping.ByCode.Conformist;

namespace Extended_Movie.Mappings
{
    public class LanguageMapping :ClassMapping<Language>
    {
        public LanguageMapping()
        {
            Id(model => model.Id, mapper =>
            {
                mapper.Column("Id");
                mapper.Generator(Generators.Guid);
            });
            Property(model => model.Name, mapper => mapper.Column("Name"));
            Property(model => model.MovieId, mapper =>
            {
                mapper.Column("MovieId");
            });
            Property(model => model.TrackFileId, mapper =>
            {
                mapper.Column("TrackFileId");
            });
        }
    }
}