package com.uttara.tasks;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import com.uttara.tasks.util.Constants;
import com.uttara.tasks.util.Logger;
import com.uttara.tasks.util.MovieBean;
import com.uttara.tasks.util.MovieTaskModel;

public class StartApp {

	public static void main(String[] args) {
		try {
			Scanner sc1 = new Scanner(System.in);
			Scanner sc2 = new Scanner(System.in);
			int ch1 = 0;
			String wishlist;
			MovieTaskModel m = new MovieTaskModel();
			while (ch1 != 5) {
				System.out.println("press 1 to create a Movie WishList");
				System.out.println("press 2 to Load Movie WishList");
				System.out.println("press 3 to search");
				System.out.println("press 4 to list");
				System.out.println("press 5 to exit");
				System.out.println("enter the choice");
				ch1 = sc1.nextInt();
				Logger.getInstance().log("entered choice : " + ch1);
				switch (ch1) {
				case 1:
					System.out.println("please enter the name of movie wishlist");
					wishlist = sc2.nextLine();
					while (m.checkIfMovieWishListExits(wishlist) == true) {
						System.out.println("wishlist already exists");
						System.out.println("please enter a unique name of movie wishlist");
						wishlist = sc2.nextLine();
					}
					Logger.getInstance().log("creating movie wishlist : " + wishlist);
					innerLoop(wishlist);
					break;

				case 2:
					System.out.println("please enter the name of movie wishlist to load");
					wishlist = sc2.nextLine();
					MovieTaskModel mb = new MovieTaskModel();
					while (mb.checkIfMovieWishListExits(wishlist) == false) {
						System.out.println("wishlist doesnot exists");
						System.out.println("please enter name of movie wishlist which exists to load");
						wishlist = sc2.nextLine();
					}
					Logger.getInstance().log("opening movie wishlist : " + wishlist);
					innerLoop(wishlist);
					break;

				case 3:
					System.out.println("please enter the string to search");
					String str = sc2.nextLine();
					Logger.getInstance().log("searching for the string in all movie wishlists : " + str);
					Map<Integer, List<MovieBean>> occurenece = m.totalNumOfOcc(str, Constants.PATH);
					Set<Entry<Integer, List<MovieBean>>> entrySet2 = occurenece.entrySet();
					for (Entry<Integer, List<MovieBean>> i : entrySet2) {
						System.out.println(
								"Total number of given string occurence acroos all wishlists is : " + i.getKey());
						System.out.println("The Movie Beans which consits the given string are :");
						List<MovieBean> value = i.getValue();
						for (MovieBean e : value)
							System.out.println(e);
					}
					break;

				case 4:
					System.out.println("listing all movies");
					Logger.getInstance().log("listing all movie wishlists ");
					Map<String, List<MovieBean>> allMovies = m.getAllMovies(Constants.PATH);
					Set<Entry<String, List<MovieBean>>> entrySet = allMovies.entrySet();
					for (Entry<String, List<MovieBean>> e : entrySet) {
						System.out.println("wislist name is : " + e.getKey());
						List<MovieBean> value = e.getValue();
						for (MovieBean a : value)
							System.out.println(a);
						System.out.println("");
					}
					System.out.println("completed");
					break;
				}
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private static void innerLoop(String wishlist) {

		MovieTaskModel m = new MovieTaskModel();
		Scanner sc1 = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);
		int ch2 = 0, ch3 = 0, ch4 = 0;
		String movieName, directorName, producerName, reviews;
		int ratings;
		String result;

		while (ch2 != 6) {
			System.out.println("press 1 to add a Movie");
			System.out.println("press 2 to edit a Movie");
			System.out.println("press 3 to remove a Movie");
			System.out.println("press 4 to list the Movies");
			System.out.println("press 5 to search a Movie");
			System.out.println("press 6 to go back");
			System.out.println("Enter the Choice");
			ch2 = sc1.nextInt();
			Logger.getInstance().log("entered choice : " + ch2);
			switch (ch2) {
			case 1:
				System.out.println("adding a movie");
				System.out.println("enter a unique movie name");
				movieName = sc2.nextLine();
				result = m.isMovieNameUnique(movieName, wishlist);
				while (result != Constants.SUCCESS) {
					System.out.println(result);
					System.out.println("please enter a unique movie name");
					movieName = sc2.nextLine();
					result = m.isMovieNameUnique(movieName, wishlist);
				}
				Logger.getInstance().log("creating movie");
				System.out.println("please enter director name");
				directorName = sc2.nextLine();
				System.out.println("please enter producer name");
				producerName = sc2.nextLine();
				System.out.println("please provide ratings of movie on a scale of 1-100");
				ratings = sc1.nextInt();
				// isRatingValid
				System.out.println("please provide review for the movie");
				reviews = sc2.nextLine();
				MovieBean bean = new MovieBean(movieName, directorName, producerName, ratings, reviews);
				result = m.addMovie(bean, wishlist);
				while (result != Constants.SUCCESS) {
					result = m.addMovie(bean, wishlist);
				}
				Logger.getInstance().log("movie has been created : " + bean);
				System.out.println("movie has been added");
				break;

			case 2:
				ch3 = 0;
				System.out.println("editing the movie info");
				System.out.println("enter the movie name to edit");
				movieName = sc2.nextLine();
				MovieBean obj = m.getMovieBean(movieName, wishlist);
				MovieBean obj1;
				Logger.getInstance().log("editing the movie of wishlist : " + wishlist);
				Logger.getInstance().log("editing movie : " + obj);
				System.out.println(obj);
				while (ch3 != 6) {
					System.out.println("press 1 to edit movie name");
					System.out.println("press 2 to edit director name");
					System.out.println("press 3 to edit producer name");
					System.out.println("press 4 to edit ratings");
					System.out.println("press 5 to edit reviews");
					System.out.println("press 6 to go back");
					System.out.println("enter your choice");
					ch3 = sc1.nextInt();
					switch (ch3) {
					case 1:
						Logger.getInstance().log("editing the movie name");
						System.out.println("enter the movie name to replace");
						movieName = sc2.nextLine();
						obj1 = new MovieBean(movieName, obj.getDirectorName(), obj.getProducerName(), obj.getRatings(),
								obj.getReviews());
						result = m.update(obj, obj1, wishlist);
						if (result == Constants.SUCCESS)
							obj = obj1;
						System.out.println(result);
						break;

					case 2:
						Logger.getInstance().log("editing the director name");
						System.out.println("enter the director name to replace");
						directorName = sc2.nextLine();
						obj1 = new MovieBean(obj.getName(), directorName, obj.getProducerName(), obj.getRatings(),
								obj.getReviews());
						result = m.update(obj, obj1, wishlist);
						if (result == Constants.SUCCESS)
							obj = obj1;
						System.out.println(result);
						break;

					case 3:
						Logger.getInstance().log("editing the producer name");
						System.out.println("enter the producer name to replace");
						producerName = sc2.nextLine();
						obj1 = new MovieBean(obj.getName(), obj.getDirectorName(), producerName, obj.getRatings(),
								obj.getReviews());
						result = m.update(obj, obj1, wishlist);
						if (result == Constants.SUCCESS)
							obj = obj1;
						System.out.println(result);
						break;

					case 4:
						Logger.getInstance().log("editing the ratings");
						System.out.println("enter the ratings to replace");
						ratings = sc1.nextInt();
						obj1 = new MovieBean(obj.getName(), obj.getDirectorName(), obj.getProducerName(), ratings,
								obj.getReviews());
						result = m.update(obj, obj1, wishlist);
						if (result == Constants.SUCCESS)
							obj = obj1;
						System.out.println(result);
						break;

					case 5:
						Logger.getInstance().log("editing the reviews");
						System.out.println("enter the reviews to replace");
						reviews = sc2.nextLine();
						obj1 = new MovieBean(obj.getName(), obj.getDirectorName(), obj.getProducerName(),
								obj.getRatings(), reviews);
						result = m.update(obj, obj1, wishlist);
						if (result == Constants.SUCCESS)
							obj = obj1;
						System.out.println(result);
						break;
					}
				}

				break;

			case 3:
				System.out.println("removing movie");
				System.out.println("please enter the movie name to remove");
				movieName = sc2.nextLine();
				result = m.removeMovie(movieName, wishlist);
				while (result != Constants.SUCCESS) {
					System.out.println(result);
					System.out.println("please enter the movie name to remove");
					movieName = sc2.nextLine();
					result = m.removeMovie(movieName, wishlist);
				}
				Logger.getInstance().log("removing the movie : " + movieName + " of wishlist : " + wishlist);
				System.out.println(result);
				break;

			case 4:
				ch4 = 0;
				while (!m.checkIfMovieWishListExits(wishlist)) {
					System.out.println("please enter a valid wish list");
					wishlist = sc2.nextLine();
				}
				Logger.getInstance().log("sorting the movie of wishlist : " + wishlist);
				List<MovieBean> movie = new LinkedList<MovieBean>();
				while (ch4 != 6) {
					System.out.println("press 1 to list movies by alphabetical listing by movieName");
					System.out.println("Press 2 to list movies by directorName");
					System.out.println("press 3 to list movies by producer name");
					System.out.println("press 4 to list movies by ratings");
					System.out.println("press 5 to list movies by reviews");
					System.out.println("press 6 to go back");
					System.out.println("enter your choice");
					ch4 = sc1.nextInt();
					switch (ch4) {
					case 1:
						System.out.println("listing the movies of the wishlist based on movie name");
						movie = m.getMoviesBasedOnMovieName(wishlist);
						for (MovieBean m1 : movie)
							System.out.println(m1);
						Logger.getInstance().log("sorting the wishlist : " + wishlist + " based on movie name");
						System.out.println("completed");
						System.out.println("");
						break;

					case 2:
						System.out.println("listing the movies based on directors name");
						movie = m.getMoviesBasedOnDirectorName(wishlist);
						for (MovieBean m1 : movie)
							System.out.println(m1);
						Logger.getInstance().log("sorting the wishlist : " + wishlist + " based on Director  name");
						System.out.println("completed");
						System.out.println("");
						break;

					case 3:
						System.out.println("listing the movies based on producers name");
						movie = m.getMoviesBasedOnProducerName(wishlist);
						for (MovieBean m1 : movie)
							System.out.println(m1);
						Logger.getInstance().log("sorting the wishlist : " + wishlist + " based on producer name");
						System.out.println("completed");
						System.out.println("");
						break;

					case 4:
						System.out.println("listing the movies based on movie ratings");
						movie = m.getMoviesBasedOnRatings(wishlist);
						for (MovieBean m1 : movie)
							System.out.println(m1);
						Logger.getInstance().log("sorting the wishlist : " + wishlist + " based on ratings");
						System.out.println("completed");
						System.out.println("");
						break;

					case 5:
						System.out.println("listing the movies based on movie reviews");
						movie = m.getMoviesBasedOnReviews(wishlist);
						for (MovieBean m1 : movie)
							System.out.println(m1);
						Logger.getInstance().log("sorting the wishlist : " + wishlist + " based on reviews");
						System.out.println("completed");
						System.out.println("");
						break;
					}
				}
				break;

			case 5:
				System.out.println("please enter the string to search");
				String str = sc2.nextLine();
				List<String> searchMovie = m.searchMovie(str, wishlist);
				for (String s : searchMovie)
					System.out.println(s);
				Logger.getInstance().log("searching for the string : " + str + " in the wishlist : " + wishlist);
				break;
			}
		}
	}
}
