package com.epam;

import java.time.LocalDate;
import java.util.List;

public class Round {
		private int year;
		private int week;
		private int round;
		private LocalDate date;
		private List<Hit> hits;
		private List<Outcome> outcomes;
		
		
		public int getYear() {
			return year;
		}
		public void setYear(int year) {
			this.year = year;
		}
		public int getWeek() {
			return week;
		}
		public void setWeek(int week) {
			this.week = week;
		}
		public int getRound() {
			return round;
		}
		public void setRound(int round) {
			this.round = round;
		}
		public LocalDate getDate() {
			return date;
		}
		public void setDate(LocalDate date) {
			this.date = date;
		}
		public List<Hit> getHits() {
			return hits;
		}
		public void setHits(List<Hit> hits) {
			this.hits = hits;
		}
		public List<Outcome> getOutcomes() {
			return outcomes;
		}
		public void setOutcomes(List<Outcome> outcomes) {
			this.outcomes = outcomes;
		}
		@Override
		public String toString() {
			return "Round [year=" + year + ", week=" + week + ", round=" + round + ", date=" + date + ", hits=" + hits
					+ ", outcomes=" + outcomes + "]";
		}
		
		

		
		
		
		
		
		
		
		
		
		
		
}
