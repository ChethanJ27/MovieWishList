package com.uttara.tasks.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MovieTaskModel {

	public String isMovieNameUnique(String movieName, String wishList) {
		File f = new File(wishList + ".txt");
		if (f.exists()) {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(wishList + ".txt"));
				String line;
				while ((line = br.readLine()) != null) {
					String[] ar = line.split(":");
					if (movieName.equals(ar[0])) {
						return "movie name already exits in this wish list";
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return Constants.SUCCESS;
		} else
			return Constants.SUCCESS;
	}

	public boolean checkIfMovieWishListExits(String name) {
		File f = new File(name + ".txt");
		return f.exists();
	}

	public String addMovie(MovieBean bean, String wishList) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(wishList + ".txt", true));
			String line = bean.getName() + ":" + bean.getDirectorName() + ":" + bean.getProducerName() + ":"
					+ bean.getRatings() + ":" + bean.getReviews();
			bw.write(line);
			bw.newLine();
			return Constants.SUCCESS;
		} catch (IOException e) {
			e.printStackTrace();
			return "something bad happened while writing file" + e.getMessage();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public String removeMovie(String movieName, String wishlist) {
		File f = new File(wishlist + ".txt");
		List<String> list = new LinkedList<String>();
		if (f.exists()) {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(f));
				String line;
				while ((line = br.readLine()) != null) {
					list.add(line);
				}
				boolean b = false;
				for (String name : list) {
					if (name.contains(movieName))
						b = true;
				}
				if (b != true)
					return "movie does not exist in the wishlist";
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("something bad happened while reading the file" + e.getMessage());
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
						return "something bad happened" + e.getMessage();
					}
				}
			}
			BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new FileWriter(f));
				list = deleteMovie(list, movieName);
				for (String s : list) {
					bw.write(s);
					bw.newLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return "something bad happened while reading the file" + e.getMessage();
			} finally {
				if (bw != null) {
					try {
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
						return "something bad happened while closing the file" + e.getMessage();
					}
				}
			}
			return Constants.SUCCESS;
		}
		return "please provide a valid wishlist";
	}

	private static List<String> deleteMovie(List<String> list, String movieName) {
		LinkedList<String> res = new LinkedList<String>();
		for (String s : list) {
			String[] str = s.split(":");
			if (str[0].equals(movieName)) {
				continue;
			} else
				res.add(s);
		}
		return res;
	}

	public List<MovieBean> getMoviesBasedOnMovieName(String wishlist) {
		if (checkIfMovieWishListExits(wishlist)) {
			List<MovieBean> movie = getListOfMovieBeans(wishlist);
			Collections.sort(movie);
			return movie;
		} else
			return null;
	}

	private List<MovieBean> getListOfMovieBeans(String wishlist) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(wishlist + ".txt"));
			List<MovieBean> movie = new LinkedList<MovieBean>();
			String line;
			while ((line = br.readLine()) != null) {
				String[] str = line.split(":");
				int i = 0;
				MovieBean b = new MovieBean(str[i++], str[i++], str[i++], Integer.parseInt(str[i++]), str[i++]);
				movie.add(b);
			}
			return movie;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public List<MovieBean> getMoviesBasedOnDirectorName(String wishlist) {
		if (checkIfMovieWishListExits(wishlist)) {
			List<MovieBean> movie = getListOfMovieBeans(wishlist);
			Collections.sort(movie, (o1, o2) -> o1.getDirectorName().compareTo(o2.getDirectorName()));
			return movie;
		} else
			return null;
	}

	public List<MovieBean> getMoviesBasedOnProducerName(String wishlist) {
		if (checkIfMovieWishListExits(wishlist)) {
			List<MovieBean> movie = getListOfMovieBeans(wishlist);
			Collections.sort(movie, (o1, o2) -> o1.getProducerName().compareTo(o2.getProducerName()));
			return movie;
		} else
			return null;
	}

	public List<MovieBean> getMoviesBasedOnRatings(String wishlist) {
		if (checkIfMovieWishListExits(wishlist)) {
			List<MovieBean> movie = getListOfMovieBeans(wishlist);
			Collections.sort(movie, (o1, o2) -> o1.getRatings() - o2.getRatings());
			return movie;
		} else
			return null;
	}

	public List<MovieBean> getMoviesBasedOnReviews(String wishlist) {
		if (checkIfMovieWishListExits(wishlist)) {
			List<MovieBean> movie = getListOfMovieBeans(wishlist);
			Collections.sort(movie, (o1, o2) -> o1.getReviews().compareTo(o2.getReviews()));
			return movie;
		} else
			return null;
	}

	public List<String> searchMovie(String str, String wishlist) {
		List<String> result = new ArrayList<String>();
		/*
		 * int count = totalNumOfOccurence(str, wishlist);
		 * result.add("total number of occurence :"+count);
		 */
		List<String> tempMovie = new LinkedList<String>();
		List<String> tempDir = new LinkedList<String>();
		List<String> tempReview = new LinkedList<String>();
		List<MovieBean> movie = getListOfMovieBeans(wishlist);
		int countDir = 0, countMovie = 0, countReview = 0;
		for (MovieBean m : movie) {
			if (m.getDirectorName().contains(str)) {
				countDir++;
				tempDir.add(m.toString());
			}
			if (m.getName().contains(str)) {
				countMovie++;
				tempMovie.add(m.toString());
			}
			if (m.getReviews().contains(str)) {
				countReview++;
				tempReview.add(m.toString());
			}
		}
		result.add("Total number of occurences : " + (countDir + countMovie + countReview));
		result.add("Number of occurences in directorName : " + countDir);
		result.add("Matches found : ");
		result.addAll(tempDir);
		result.add("Number of occurences in MovieName : " + countMovie);
		result.add("Matches found : ");
		result.addAll(tempMovie);
		result.add("Number of occurences in reviews : " + countReview);
		result.add("Matches found : ");
		result.addAll(tempDir);
		return result;
	}

	public MovieBean getMovieBean(String movieName, String wishlist) {
		if (checkIfMovieWishListExits(wishlist)) {
			List<MovieBean> listOfMovieBeans = getListOfMovieBeans(wishlist);
			for (MovieBean b : listOfMovieBeans) {
				if (b.getName().equals(movieName))
					return b;
			}
		}
		return null;
	}

	public String update(MovieBean old, MovieBean obj, String wishlist) {
		if (checkIfMovieWishListExits(wishlist)) {
			List<MovieBean> listOfMovieBeans = getListOfMovieBeans(wishlist);
			listOfMovieBeans.remove(old);
			listOfMovieBeans.add(obj);
			BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new FileWriter(wishlist + ".txt"));
				for (MovieBean mb : listOfMovieBeans) {
					String line = mb.getName() + ":" + mb.getDirectorName() + ":" + mb.getProducerName() + ":"
							+ mb.getRatings() + ":" + mb.getReviews();
					bw.write(line);
					bw.newLine();
				}
				return Constants.SUCCESS;
			} catch (IOException e) {
				e.printStackTrace();
				return "something bad happened while reading the file" + e.getMessage();
			} finally {
				if (bw != null) {
					try {
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
						return "something bad happened while closing the file" + e.getMessage();
					}
				}
			}

		}
		return "wishlist doesnot exists";
	}

	public Map<Integer, List<MovieBean>> totalNumOfOcc(String str, String path) {
		int count = 0;
		Map<Integer, List<MovieBean>> hs = new HashMap<Integer, List<MovieBean>>();
		File f = new File(path);
		List<MovieBean> bean = new LinkedList<MovieBean>();
		if (f.exists() && f.isDirectory()) {
			File[] fn = f.listFiles();
			for (File a : fn) {
				if (a.getName().endsWith(".txt")) {
					BufferedReader br = null;
					try {
						br = new BufferedReader(new FileReader(a));
						String line;
						int pos = 0;
						while ((line = br.readLine()) != null) {
							pos = 0;
							while ((pos = line.indexOf(str, pos)) != -1) {
								pos++;
								count++;
							}
							if (line.contains(str)) {
								int i = 0;
								String[] st = line.split(":");
								MovieBean mov = new MovieBean(st[i++], st[i++], st[i++], Integer.parseInt(st[i++]),
										st[i++]);
								bean.add(mov);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (br != null) {
							try {
								br.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			hs.put(count, bean);
		}
		return hs;
	}

	public Map<String, List<MovieBean>> getAllMovies(String path) {
		Map<String, List<MovieBean>> mp = new HashMap<String, List<MovieBean>>();
		List<MovieBean> bean = new LinkedList<MovieBean>();
		File f = new File(path);
		String wishlist;
		if (f.exists()) {
			if (f.isDirectory()) {
				File[] fa = f.listFiles();
				for (File a : fa) {
					bean.clear();
					if (a.getName().endsWith(".txt")) {
						String[] split = a.getName().split(".txt");
						wishlist = split[0];
						BufferedReader br = null;
						try {
							br = new BufferedReader(new FileReader(a));
							String line;
							MovieBean mov;
							while ((line = br.readLine()) != null) {
								int i = 0;
								String[] str = line.split(":");
								mov = new MovieBean(str[i++], str[i++], str[i++], Integer.parseInt(str[i++]), str[i++]);
								bean.add(mov);
							}

						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							if (br != null) {
								try {
									br.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
						mp.put(wishlist, bean);
					}

				}
			}
			return mp;
		}
		return null;
	}

	/*
	 * private int totalNumOfOccurence(String str, String wishlist) { int count = 0;
	 * BufferedReader br = null; try { br = new BufferedReader(new
	 * FileReader(wishlist + ".txt")); String line; int pos = 0; while ((line =
	 * br.readLine()) != null) { pos = 0; if ((pos = line.indexOf(str, pos)) != -1)
	 * { pos++; count++; } } } catch (IOException e) { e.printStackTrace(); }
	 * finally { if (br != null) { try { br.close(); } catch (IOException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } } } return count; }
	 */
}
