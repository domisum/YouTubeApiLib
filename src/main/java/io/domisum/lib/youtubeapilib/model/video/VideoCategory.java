package io.domisum.lib.youtubeapilib.model.video;

import io.domisum.lib.auxiliumlib.annotations.API;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum VideoCategory
{
	
	// @formatter:off
	@API FILM_AND_ANIMATION(1),
	@API AUTOS_AND_VEHICLES(2),
	@API MUSIC(10),
	@API PETS_AND_ANIMALS(15),
	@API SPORTS(17),
	@API SHORT_MOVIES(18),
	@API TRAVEL_AND_EVENTS(19),
	@API GAMING(20),
	@API VIDEOBLOGGING(21),
	@API PEOPLE_AND_BLOGS(22),
	@API COMEDY(23),
	@API ENTERTAINMENT(24),
	@API NEWS_AND_POLITICS(25),
	@API HOWTO_AND_STYLE(26),
	@API EDUCATION(27),
	@API SCIENCE_AND_TECHNOLOGY(28),
	@API NONPROFITS_AND_ACTIVISM(29),
	@API MOVIES(30),
	@API ANIME_ANIMATION(31),
	@API ACTION_ADVENTURE(32),
	@API CLASSICS(33),
	@API COMEDY_NEW(34),
	@API DOCUMENTARY(35),
	@API DRAMA(36),
	@API FAMILY(37),
	@API FOREIGN(38),
	@API HORROR(39),
	@API SCIFI_FANTASY(40),
	@API THRILLER(41),
	@API SHORTS(42),
	@API SHOWS(43),
	@API TRAILERS(44);
	// @formatter:on
	
	
	// ATTRIBUTES
	public final int categoryId;
	
	
	// INIT
	public static VideoCategory fromCategoryId(int categoryId)
	{
		for(var category : values())
			if(category.categoryId == categoryId)
				return category;
		
		throw new IllegalArgumentException("no category with id "+categoryId);
	}
	
}
