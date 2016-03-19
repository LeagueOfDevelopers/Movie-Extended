using System;
using System.Collections.Generic;
using Domain.Models.Entities;

namespace Domain.Models.FrontendEntities
{
    public class FrontendMovie
    {
        public FrontendMovie(int Id , string Name , ISet<Language> Language  , File Poster )
        {
            this.Id = Id;
            this.Name = Name;
            this.Language = new HashSet<FrontendLanguage>();
            foreach (var language in Language)
            {
                var frontendLangugeEntity = new FrontendLanguage();
                frontendLangugeEntity.Id = language.Id;
                frontendLangugeEntity.Name = language.Name;
                frontendLangugeEntity.Subtitles.Id = language.Subtitles.Id;
                frontendLangugeEntity.TrackFile.Id = language.TrackFile.Id;
                this.Language.Add(frontendLangugeEntity);
            }
            this.Poster.Id = Poster.Id;
        }

        public FrontendMovie()
        {
            
        }
        public virtual int Id { get;  set; }

        public virtual string Name { get;  set; }

        public virtual ISet<FrontendLanguage> Language { get; set; }

        public virtual Guid AndroidToken { get; set; }

        public virtual FrontendFile Poster { get; set; }

    }
}
