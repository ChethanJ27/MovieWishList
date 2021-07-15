package com.uttara.tasks.util;

public class MovieBean implements Comparable<MovieBean> {
	private String name;
	private String directorName;
	private String producerName;
	private int ratings; // range(1-100);
	private String reviews;

	@Override
	public int compareTo(MovieBean o) {
		return this.name.compareTo(o.name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.trim().equals(""))
			throw new IllegalArgumentException("please provide a valid movie name");
		else
			this.name = name;
	}

	public String getDirectorName() {
		return directorName;
	}

	public void setDirectorName(String directorName) {
		if (directorName == null || directorName.trim().equals(""))
			throw new IllegalArgumentException("please provide a valid director name");
		else
			this.directorName = directorName;
	}

	public String getProducerName() {
		return producerName;
	}

	public void setProducerName(String producerName) {
		if (producerName == null || producerName.trim().equals(""))
			throw new IllegalArgumentException("please provide a valid producer name");
		else
			this.producerName = producerName;
	}

	public int getRatings() {
		return ratings;
	}

	public void setRatings(int ratings) {
		if (ratings >= 1 && ratings <= 100)
			this.ratings = ratings;
		else
			throw new IllegalArgumentException("please provide ratings in between 1-100");
	}

	public String getReviews() {
		return reviews;
	}

	public void setReviews(String reviews) {
		if (reviews == null || reviews.trim().equals(""))
			throw new IllegalArgumentException("please provide a valid review");
		else
			this.reviews = reviews;
	}

	public MovieBean() {
		// TODO Auto-generated constructor stub
	}

	public MovieBean(String name, String directorName, String producerName, int ratings, String reviews) {
		super();
		if (name == null || name.trim().equals(""))
			throw new IllegalArgumentException("please provide a valid movie name");
		else
			this.name = name;

		if (directorName == null || directorName.trim().equals(""))
			throw new IllegalArgumentException("please provide a valid director name");
		else
			this.directorName = directorName;

		if (producerName == null || producerName.trim().equals(""))
			throw new IllegalArgumentException("please provide a valid producer name");
		else
			this.producerName = producerName;

		if (ratings >= 1 && ratings <= 100)
			this.ratings = ratings;
		else
			throw new IllegalArgumentException("please provide ratings in between 1-100");

		if (reviews == null || reviews.trim().equals(""))
			throw new IllegalArgumentException("please provide a valid review");
		else
			this.reviews = reviews;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MovieBean) {
			MovieBean s = (MovieBean) obj;
			if (this.name.equals(s.name) && this.directorName.equals(s.directorName)
					&& this.producerName.equals(s.producerName) && this.ratings == s.ratings
					&& this.reviews.equals(s.reviews))
				return true;
			else
				return false;
		} else
			return false;
	}

	@Override
	public int hashCode() {
		return (name + directorName + producerName + ratings + reviews + "").hashCode();
	}

	@Override
	public String toString() {
		return "MovieBean : " + name + ":" + directorName + ":" + producerName + ":" + ratings + ":" + reviews;
	}
}
